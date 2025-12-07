package com.example.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.login.mapper")
@lombok.extern.slf4j.Slf4j
@org.springframework.scheduling.annotation.EnableAsync
public class LoginApplication implements org.springframework.boot.CommandLineRunner {

    @org.springframework.beans.factory.annotation.Autowired
    private org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n" +
                "  /\\_/\\  \n" +
                " ( o.o )  Login System Starting... \n" +
                "  > ^ <   ");
        log.info("🚀 应用启动中... (App Starting...) --------------------------");

        try {
            redisTemplate.opsForValue().set("startup_test", "Available");
            String result = redisTemplate.opsForValue().get("startup_test");
            if ("Available".equals(result)) {
                log.info("🍓 Redis 连接成功! (Redis Connected!) (≧∇≦)/ 🟢");
            } else {
                log.warn("🥕 Redis 连接响应异常 (Redis Abnormal) (xx) 🟠");
            }
            redisTemplate.delete("startup_test");
        } catch (Exception e) {
            log.error("💥 Redis 连接失败: {} (Redis Failed) (T_T) 🔴", e.getMessage());
        }

        log.info("----------------------------------------------------------");
        log.info("✨✨✨ 启动完成! (Startup Complete) ✨✨✨");
        log.info("🐱 欢迎使用企业级认证系统 (Welcome) 🐱");
        System.out.println("   /\\___/\\   \n" +
                "  (  o o  )  \n" +
                "  /   *   \\  \n" +
                "  \\__\\_/__/  ");
        log.info("🌐 接口文档: http://localhost:8080/doc.html (Knife4j)");
        log.info("💻 前端地址: http://localhost:5173");
        log.info("----------------------------------------------------------");
    }
}
