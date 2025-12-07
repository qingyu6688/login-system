package com.example.login.controller;

import com.example.login.dto.AuthDto.AuthResponse;
import com.example.login.dto.AuthDto.LoginRequest;
import com.example.login.dto.AuthDto.RegisterRequest;
import com.example.login.entity.SysUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "认证模块", description = "处理用户登录、注册与Token颁发")
public class AuthController {

    private final com.example.login.service.SysUserService sysUserService;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
    private final com.example.login.util.JwtUtils jwtUtils;
    private final org.springframework.security.authentication.AuthenticationManager authenticationManager;
    private final com.example.login.service.LoginLogService loginLogService;
    private final org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    /**
     * 获取图形验证码接口
     */
    @org.springframework.web.bind.annotation.GetMapping("/captcha")
    @io.swagger.v3.oas.annotations.Operation(summary = "验证码", description = "获取图形验证码")
    public ResponseEntity<java.util.Map<String, String>> getCaptcha() {
        // Create captcha: width 200, height 100, 4 chars, 20 interference lines
        cn.hutool.captcha.LineCaptcha captcha = cn.hutool.captcha.CaptchaUtil.createLineCaptcha(200, 100, 4, 20);
        String uuid = cn.hutool.core.util.IdUtil.simpleUUID();
        String code = captcha.getCode();

        // Save to Redis: 2 minutes expiration
        redisTemplate.opsForValue().set("captcha:" + uuid, code, 2, java.util.concurrent.TimeUnit.MINUTES);

        String imageBase64 = captcha.getImageBase64();
        // Remove newlines to avoid frontend issues
        imageBase64 = imageBase64.replaceAll("[\\s\\r\\n]+", "");

        // Ensure data URI prefix
        if (!imageBase64.startsWith("data:image")) {
            imageBase64 = "data:image/png;base64," + imageBase64;
        }

        return ResponseEntity.ok(java.util.Map.of("uuid", uuid, "image", imageBase64));
    }

    /**
     * 用户注册接口
     */
    @PostMapping("/register")
    @io.swagger.v3.oas.annotations.Operation(summary = "用户注册", description = "支持用户名、密码、手机号、邮箱注册")
    public ResponseEntity<?> register(@jakarta.validation.Valid @RequestBody RegisterRequest request) {
        if (sysUserService.getByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "用户名已存在"));
        }

        if (sysUserService.getByMobile(request.getMobile()) != null) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "手机号已注册"));
        }

        // Password strength: at least 8 chars, 1 letter, 1 number
        if (!request.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            return ResponseEntity.badRequest().body(java.util.Map.of("message", "密码太弱：需至少8位，包含字母和数字"));
        }

        SysUser user = new SysUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setStatus(0); // Normal
        user.setLoginRetryCount(0);

        sysUserService.save(user);

        String token = jwtUtils.generateToken(user.getUsername());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .username(user.getUsername())
                .role("USER")
                .build());
    }

    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    @io.swagger.v3.oas.annotations.Operation(summary = "用户登录", description = "校验账号密码并返回 JWT Token")
    public ResponseEntity<?> login(@jakarta.validation.Valid @RequestBody LoginRequest request,
            jakarta.servlet.http.HttpServletRequest httpRequest) {

        String ip = com.example.login.util.IpUtils.getIpAddr(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");

        // 1. Verify Captcha
        String captchaKey = "captcha:" + request.getCaptchaUuid();
        String captchaCode = redisTemplate.opsForValue().get(captchaKey);

        if (!"skip".equals(request.getCaptchaCode())) {
            if (captchaCode == null || !captchaCode.equalsIgnoreCase(request.getCaptchaCode())) {
                loginLogService.recordLoginLog(null, request.getUsername(), 1, "验证码错误", ip, userAgent);
                throw new org.springframework.security.authentication.BadCredentialsException("验证码错误或已过期");
            }
        }
        redisTemplate.delete(captchaKey);

        // Try to find by username first
        SysUser user = sysUserService.getByUsername(request.getUsername());
        // If not found, try by mobile
        if (user == null) {
            user = sysUserService.getByMobile(request.getUsername());
        }

        if (user == null) {
            loginLogService.recordLoginLog(null, request.getUsername(), 1, "用户不存在", ip, userAgent);
            throw new org.springframework.security.authentication.BadCredentialsException("用户不存在");
        }

        // Check lock
        if (user.getStatus() == 1) { // Locked
            if (user.getLockTime() != null
                    && user.getLockTime().plusMinutes(15).isAfter(java.time.LocalDateTime.now())) {
                loginLogService.recordLoginLog(user.getId(), user.getUsername(), 1, "账号已锁定", ip, userAgent);
                return ResponseEntity.status(401).body(java.util.Map.of("message", "账号已锁定，请15分钟后再试"));
            } else {
                // Unlock
                user.setStatus(0);
                user.setLoginRetryCount(0);
                user.setLockTime(null);
                sysUserService.updateById(user);
            }
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), // Use real username for auth
                            request.getPassword()));

            // Success: Reset retry count
            if (user.getLoginRetryCount() > 0) {
                user.setLoginRetryCount(0);
                sysUserService.updateById(user);
            }

            String token = jwtUtils.generateToken(user.getUsername());
            loginLogService.recordLoginLog(user.getId(), user.getUsername(), 0, "登录成功", ip, userAgent);

            return ResponseEntity.ok(AuthResponse.builder()
                    .token(token)
                    .username(user.getUsername())
                    .role("USER")
                    .build());

        } catch (org.springframework.security.core.AuthenticationException e) {
            // Failure: Increment retry count
            int retryCount = user.getLoginRetryCount() == null ? 0 : user.getLoginRetryCount();
            user.setLoginRetryCount(retryCount + 1);
            if (user.getLoginRetryCount() >= 5) {
                user.setStatus(1); // Lock
                user.setLockTime(java.time.LocalDateTime.now());
            }
            sysUserService.updateById(user);

            loginLogService.recordLoginLog(user.getId(), user.getUsername(), 1, "密码错误", ip, userAgent);
            throw new org.springframework.security.authentication.BadCredentialsException("用户名或密码错误");
        }
    }
}
