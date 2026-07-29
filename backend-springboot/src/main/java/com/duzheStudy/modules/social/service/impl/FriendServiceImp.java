package com.duzheStudy.modules.social.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duzheStudy.modules.social.domain.Friend;
import com.duzheStudy.modules.social.mapper.FriendMapper;
import com.duzheStudy.modules.social.service.FriendService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FriendServiceImp implements FriendService {

    @Resource
    private FriendMapper friendMapper;
    @Override
    @Transactional
    public Boolean checkIfFriend(String id1, String id2) {
        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted",false).eq("my_id",id1).eq("friend_id", id2);
        return friendMapper.selectCount(queryWrapper) > 0;
    }
}
