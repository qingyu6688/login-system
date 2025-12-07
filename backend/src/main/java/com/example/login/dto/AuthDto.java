package com.example.login.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @io.swagger.v3.oas.annotations.media.Schema(description = "登录请求参数")
    public static class LoginRequest {
        @jakarta.validation.constraints.NotBlank(message = "用户名不能为空")
        @io.swagger.v3.oas.annotations.media.Schema(description = "用户名", example = "admin", requiredMode = io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED)
        private String username;

        @jakarta.validation.constraints.NotBlank(message = "密码不能为空")
        @io.swagger.v3.oas.annotations.media.Schema(description = "密码", example = "123456", requiredMode = io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED)
        private String password;

        @io.swagger.v3.oas.annotations.media.Schema(description = "验证码", example = "1234")
        private String captchaCode;

        @io.swagger.v3.oas.annotations.media.Schema(description = "验证码UUID", example = "uuid-string")
        private String captchaUuid;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @io.swagger.v3.oas.annotations.media.Schema(description = "注册请求参数")
    public static class RegisterRequest {
        @jakarta.validation.constraints.NotBlank(message = "用户名不能为空")
        @jakarta.validation.constraints.Size(min = 3, max = 20, message = "用户名长度需在3-20之间")
        @io.swagger.v3.oas.annotations.media.Schema(description = "用户名", example = "newuser")
        private String username;

        @jakarta.validation.constraints.NotBlank(message = "密码不能为空")
        @jakarta.validation.constraints.Size(min = 8, max = 20, message = "密码长度需在8-20之间")
        @io.swagger.v3.oas.annotations.media.Schema(description = "密码", example = "Password@123")
        private String password;

        @jakarta.validation.constraints.Email(message = "邮箱格式不正确")
        @io.swagger.v3.oas.annotations.media.Schema(description = "邮箱", example = "user@example.com")
        private String email;

        @jakarta.validation.constraints.Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
        @io.swagger.v3.oas.annotations.media.Schema(description = "手机号", example = "13800138000")
        private String mobile;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @io.swagger.v3.oas.annotations.media.Schema(description = "认证响应结果")
    public static class AuthResponse {
        @io.swagger.v3.oas.annotations.media.Schema(description = "JWT Token")
        private String token;
        @io.swagger.v3.oas.annotations.media.Schema(description = "用户名")
        private String username;
        @io.swagger.v3.oas.annotations.media.Schema(description = "角色")
        private String role;
    }
}
