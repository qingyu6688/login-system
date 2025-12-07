package com.example.login.controller;

import com.example.login.entity.SysUser;
import com.example.login.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户模块", description = "用户个人信息管理")
public class UserController {

    private final SysUserService sysUserService;

    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SysUser user = sysUserService.getByUsername(username);

        // Hide sensitive info
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    @Operation(summary = "更新用户信息", description = "更新昵称、邮箱、手机号、头像")
    public ResponseEntity<?> updateProfile(@RequestBody SysUser userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        SysUser user = sysUserService.getByUsername(username);

        if (userRequest.getNickname() != null)
            user.setNickname(userRequest.getNickname());
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getMobile() != null)
            user.setMobile(userRequest.getMobile());
        if (userRequest.getAvatar() != null)
            user.setAvatar(userRequest.getAvatar());

        sysUserService.updateById(user);
        return ResponseEntity.ok(Map.of("message", "更新成功"));
    }
}
