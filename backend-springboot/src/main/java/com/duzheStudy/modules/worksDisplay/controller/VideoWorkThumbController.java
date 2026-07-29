package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.VideoWorkThumbMapper;
import com.duzheStudy.modules.worksDisplay.domain.Thumb;
import com.duzheStudy.modules.worksDisplay.domain.form.ThumbWorkForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoWorkThumbController {
    @Autowired
    private VideoWorkThumbMapper videoWorkThumbMapper;

    @PostMapping("/addOrDeleteVideoWorkThumbById")
    public void addOrDeleteVideoWorkThumbById(@RequestBody ThumbWorkForm thumbWorkForm){
        User currentUser = UserUtils.getLoginUser();
        Thumb thumb = new Thumb();
        thumb.setThumber(currentUser.getUserName());
        thumb.setCreatedBy(System.currentTimeMillis());
        this.videoWorkThumbMapper.thumbWorkById(thumbWorkForm.getId(),thumb);
    }

    @GetMapping("/checkIsVideoWorkThumb")
    public boolean checkIsVideoWorkThumb(String id){
        User currentUser = UserUtils.getLoginUser();
        return this.videoWorkThumbMapper.checkIsThumb(id,currentUser.getUserName());
    }

    @GetMapping("/getVideoWorkThumbNumById")
    public Integer getVideoWorkThumbNumById(String id){
        return this.videoWorkThumbMapper.getThumbNumById(id);
    }
}
