package com.dreamer.demoproject.controller;

import com.dreamer.demoproject.pojo.Result;
import com.dreamer.demoproject.pojo.User;
import com.dreamer.demoproject.service.UserService;
import com.dreamer.demoproject.util.JwtUtil;
import com.dreamer.demoproject.util.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //查询用户名是否重复
        User u = userService.findByUserName(username);
        if (u == null) {
            //注册
            userService.register(username, password);
            return Result.success();
        } else {
            return Result.error("用户名已被使用");
        }
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        //根据用户名查询密码
        User u = userService.findByUserName(username);
        if (u == null) {
            return Result.error("用户不存在");
        }
        if (Md5Util.getMD5String(password).equals(u.getPassword())) {
            //登录成功
            Map<String,Object> claims=new HashMap<>();
            claims.put("id",u.getId());
            claims.put("username",u.getUsername());
            String token= JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }
}

