package com.eleme.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eleme.common.entity.User;
import com.eleme.user.mapper.UserMapper;
import com.eleme.user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String userName, String password) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名，避免自动转换
        queryWrapper.eq("userName", userName);
        queryWrapper.eq("password", password);
        queryWrapper.eq("delTag", 1);
        return userMapper.selectOne(queryWrapper);
    }

    public boolean register(User user) {
        // 检查用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 明确指定数据库字段名
        queryWrapper.eq("userName", user.getUserName());
        User existUser = userMapper.selectOne(queryWrapper);
        if (existUser != null) {
            return false;
        }

        user.setDelTag(1);
        user.setUserImg("yhtx01.png");
        return userMapper.insert(user) > 0;
    }

    public User getUserById(Integer userId) {
        return userMapper.selectById(userId);
    }

    public Map<String, Object> createLoginResult(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("userId", user.getUserId());
        result.put("userName", user.getUserName());
        result.put("userSex", user.getUserSex());
        result.put("userImg", user.getUserImg());
        result.put("token", JwtUtil.generateToken(user.getUserId().toString()));
        return result;
    }
}
