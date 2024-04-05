package com.dreamer.demoproject.controller;

import com.dreamer.demoproject.pojo.Result;
import com.dreamer.demoproject.pojo.User;
import com.dreamer.demoproject.service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username ,@Pattern(regexp = "^\\S{5,16}$") String password){
        //查询用户名是否重复
        User u=userService.findByUserName(username);
        if(u==null){
            //注册
            userService.register(username,password);
            return Result.success();
        }
        else{
            return Result.error("用户名已被使用");
        }
    }
}
