package com.duzheStudy.modules.video.controller;

import com.duzheStudy.modules.video.domain.UserMovieInfo;
import com.duzheStudy.modules.video.service.IMovieInfoService;
import com.duzheStudy.modules.video.service.IUserMovieInfoService;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;


@RestController
@RequestMapping("/system/userMovieInfo")
public class UserMovieInfoController {
    @Resource
    private IMovieInfoService movieInfoService;
    @Resource
    private IUserMovieInfoService userMovieInfoService;
    @PostMapping("add")
    public ResponseDto add(@RequestBody @Valid UserMovieInfo userMovieInfo) {
        return userMovieInfoService.add(userMovieInfo);
    }
    @GetMapping("/readScore")
    public ResponseDto readScore(@RequestParam("userId") Long userId,
                                 @RequestParam("movieId") String movieId) {
        Long score = userMovieInfoService.getScoreByUserIdAndMovieId(userId, movieId); // 修改为 Long
        return ResponseDto.success(score);
    }
}
