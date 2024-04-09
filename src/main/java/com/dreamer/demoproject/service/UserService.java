package com.dreamer.demoproject.service;

import com.dreamer.demoproject.pojo.User;

public interface UserService {
    //查询用户
    User findByUserName(String username);
    //注册
    void register(String username,String password);
    //更新信息
    void update(User user);
}
