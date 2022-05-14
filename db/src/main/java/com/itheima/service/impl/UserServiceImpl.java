package com.itheima.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author tang
 * @date 2022/5/13 14:45
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User login(UserLoginDto dto){
        QueryWrapper<User> qw=new QueryWrapper<>();
        qw.eq("phone", dto.getPhone());
        return userMapper.selectOne(qw);
    }



    @Override
    public boolean saveUser(User user) {
        userMapper.insert(user);
        return true;
    }

}
