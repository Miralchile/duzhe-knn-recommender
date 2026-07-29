package com.duzheStudy.modules.video.controller;

import com.duzheStudy.modules.video.service.IMovieInfoService;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/movieInfo")
public class MovieInfoController {

    @Resource
    private IMovieInfoService movieInfoService;

    @GetMapping("/getRecommendation")
    public ResponseDto getRecommendation(@RequestParam(required = false) String movieId) {
        return movieInfoService.getRecommendation(movieId); // movieId 若为空，服务层会处理
    }

}