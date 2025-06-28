package com.eleme.user.controller;

import com.eleme.common.entity.User;
import com.eleme.common.result.Result;
import com.eleme.user.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @CircuitBreaker(name = "user-service", fallbackMethod = "loginFallback")
    public Result login(@RequestBody User user) {
        User loginUser = userService.login(user.getUserName(), user.getPassword());
        if (loginUser != null) {
            return Result.success(userService.createLoginResult(loginUser));
        }
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    @CircuitBreaker(name = "user-service", fallbackMethod = "registerFallback")
    public Result register(@RequestBody User user) {
        boolean success = userService.register(user);
        if (success) {
            return Result.success();
        }
        return Result.error("用户名已存在");
    }

    @GetMapping("/userInfo/{userId}")
    @CircuitBreaker(name = "user-service", fallbackMethod = "getUserInfoFallback")
    public Result getUserInfo(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return Result.success(user);
        }
        return Result.error("用户不存在");
    }

    // Fallback methods
    public Result loginFallback(@RequestBody User user, Exception ex) {
        return Result.error(-1, "登录服务暂时不可用，请稍后重试");
    }

    public Result registerFallback(@RequestBody User user, Exception ex) {
        return Result.error(-1, "注册服务暂时不可用，请稍后重试");
    }

    public Result getUserInfoFallback(@PathVariable Integer userId, Exception ex) {
        return Result.error(-1, "用户信息服务暂时不可用，请稍后重试");
    }
}
