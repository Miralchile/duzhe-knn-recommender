package com.duzheStudy.modules.others.mapper;

import com.duzheStudy.modules.others.domain.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FollowMapper {
    int deleteByUserIdAndFollowUserId(@Param("userId")Long userId, @Param("followUserId")Long followUserId);

    int insert(Follow follow);

    Follow selectById(Long id);

    List<Map<String,Object>> selectByUserId(Long followUserId);

    int updateById(Follow follow);

    Follow selectByUserIdAndFollowUserId(@Param("userId")Long userId, @Param("followUserId")Long followUserId);
}