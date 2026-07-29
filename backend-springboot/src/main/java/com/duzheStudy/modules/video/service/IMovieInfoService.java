package com.duzheStudy.modules.video.service;

import com.duzheStudy.modules.social.controller.dto.ResponseDto;

public interface IMovieInfoService {
    void importData();
    ResponseDto getRecommendation(String movieId);
}