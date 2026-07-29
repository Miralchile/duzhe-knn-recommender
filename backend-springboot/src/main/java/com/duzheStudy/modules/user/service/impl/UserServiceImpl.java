package com.duzheStudy.modules.user.service.impl;

import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.mapper.UserMapper;
import com.duzheStudy.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int deleteById(Long id) {
        return this.userMapper.deleteById(id);
    }

    @Override
    public int insert(User user) {
        return this.userMapper.insert(user);
    }

    @Override
    public User selectByUserName(String userName) {
        return this.userMapper.selectByUserName(userName);
    }

    @Override
    public int updateById(User user) {
        return this.userMapper.updateById(user);
    }

    @Override
    public User selectById(Long id) {
        return this.userMapper.selectById(id);
    }
}
