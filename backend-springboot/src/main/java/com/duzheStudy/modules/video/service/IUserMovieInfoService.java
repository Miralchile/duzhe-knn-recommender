package com.duzheStudy.modules.video.service;

import com.duzheStudy.modules.video.domain.UserMovieInfo;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;

public interface IUserMovieInfoService {

    ResponseDto add(UserMovieInfo userMovieInfo);
    Long getScoreByUserIdAndMovieId(Long userId, String movieId);
}
