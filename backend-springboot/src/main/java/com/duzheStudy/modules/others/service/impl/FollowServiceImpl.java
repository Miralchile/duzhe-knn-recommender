package com.duzheStudy.modules.others.service.impl;

import com.duzheStudy.modules.others.mapper.FollowMapper;
import com.duzheStudy.modules.others.service.FollowService;
import com.duzheStudy.modules.others.domain.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowMapper followMapper;

    @Override
    public int deleteByUserIdAndFollowUserId(Long id, Long followUserId) {
        return this.followMapper.deleteByUserIdAndFollowUserId(id, followUserId);
    }

    @Override
    public int insert(Follow follow) {
        return this.followMapper.insert(follow);
    }

    @Override
    public Follow selectById(Long id) {
        return this.followMapper.selectById(id);
    }

    @Override
    public int updateById(Follow follow) {
        return this.followMapper.updateById(follow);
    }

    @Override
    public boolean isFollowThisUser(Long userId, Long followUserId) {
        if(this.followMapper.selectByUserIdAndFollowUserId(userId,followUserId)!=null){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Map<String,Object>> selectByUserId(Long followUserId){
        return this.followMapper.selectByUserId(followUserId);
    }
}
