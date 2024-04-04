package com.dreamer.demoproject.service.impl;

import com.dreamer.demoproject.mapper.UserMapper;
import com.dreamer.demoproject.pojo.User;
import com.dreamer.demoproject.service.UserService;
import com.dreamer.demoproject.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void register(String username, String password) {
        //加密密码
        String md5String= Md5Util.getMD5String(password);
        userMapper.add(username,md5String);
    }
}