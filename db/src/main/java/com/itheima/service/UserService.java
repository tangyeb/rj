package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.dto.UserLoginDto;
import com.itheima.entity.User;

/**
 * @author tang
 * @date 2022/5/13 14:44
 */
public interface UserService extends IService<User> {
    public User login(UserLoginDto dto);

    boolean saveUser(User user);

}
