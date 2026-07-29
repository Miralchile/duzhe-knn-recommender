package com.duzheStudy.modules.others.service;

import com.duzheStudy.modules.others.domain.Follow;

import java.util.List;
import java.util.Map;

public interface FollowService {

    int deleteByUserIdAndFollowUserId(Long userId, Long followUserId);

    int insert(Follow follow);

    Follow selectById(Long id);

    int updateById(Follow follow);

    boolean isFollowThisUser(Long userId, Long followUserId);

    List<Map<String,Object>> selectByUserId(Long followUserId);

}
