package com.duzheStudy.modules.video.service.impl;

import com.duzheStudy.common.utils.JwtUtils;
import com.duzheStudy.modules.video.domain.UserMovieInfo;
import com.duzheStudy.modules.video.mapper.UserMovieInfoMapper;
import com.duzheStudy.modules.video.service.IUserMovieInfoService;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import com.duzheStudy.modules.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service
@Slf4j
public class UserMovieInfoServiceImpl implements IUserMovieInfoService {


    @Resource
    private UserMovieInfoMapper userMovieInfoMapper;
    @Resource
    private HttpServletRequest request;
    @Resource
    private DealUserMovieInfoService dealUserMovieInfoService;

    @Override
    public ResponseDto add(UserMovieInfo userMovieInfo) {
        User user = JwtUtils.getUserByToken(request.getHeader("Authorization"));
        userMovieInfo.setDealFlag("0");
        userMovieInfo.setUserId(user.getId());
        log.info("UserMovieInfoServiceImpl.add.param={}",userMovieInfo);
        UserMovieInfo existingRecord = userMovieInfoMapper.selectByUserIdAndMovieId(user.getId(), userMovieInfo.getMovieId());
        if (existingRecord != null) {
            // 存在记录，删除旧记录
            userMovieInfoMapper.deleteByUserIdAndMovieId(user.getId(), userMovieInfo.getMovieId());
            log.info("Deleted existing score for userId={}, movieId={}", user.getId(), userMovieInfo.getMovieId());
        }
        // 插入新记录
        userMovieInfoMapper.insert(userMovieInfo);
        dealUserMovieInfoService.dealUserMovieInfo(user.getId());
        return ResponseDto.success(null);
    }
    @Override
    public Long getScoreByUserIdAndMovieId(Long userId, String movieId) {
        log.info("UserMovieInfoServiceImpl.getScoreByUserIdAndMovieId.userId={}, movieId={}", userId, movieId);
        UserMovieInfo userMovieInfo = userMovieInfoMapper.selectByUserIdAndMovieId(userId, movieId);
//        return userMovieInfo != null ? userMovieInfo.getScore() : 0L;
        return userMovieInfo != null ? userMovieInfo.getScore() : null;
    }
}
