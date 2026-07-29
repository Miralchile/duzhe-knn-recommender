package com.duzheStudy.modules.user.service;

import com.duzheStudy.modules.user.domain.User;

public interface UserService {
    public int deleteById(Long id);

    public int insert(User user);

    public User selectByUserName(String userName);

    public int updateById(User user);

    public User selectById(Long id);
}
