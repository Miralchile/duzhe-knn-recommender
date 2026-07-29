package com.duzheStudy.modules.user.mapper;

import com.duzheStudy.modules.user.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    int deleteById(Long id);

    int insert(User user);

    User selectByUserName(String userName);

    int updateById(User user);

    User selectById(Long id);

}