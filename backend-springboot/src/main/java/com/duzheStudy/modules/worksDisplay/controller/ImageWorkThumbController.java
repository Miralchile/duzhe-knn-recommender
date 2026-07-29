package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.ImageWorkThumbMapper;
import com.duzheStudy.modules.worksDisplay.dao.VideoWorkThumbMapper;
import com.duzheStudy.modules.worksDisplay.domain.Thumb;
import com.duzheStudy.modules.worksDisplay.domain.form.ThumbWorkForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageWorkThumbController {
    @Autowired
    private ImageWorkThumbMapper imageWorkThumbMapper;

    @PostMapping("/addOrDeleteImageWorkThumbById")
    public void addOrDeleteImageWorkThumbById(@RequestBody ThumbWorkForm thumbWorkForm){
        User currentUser = UserUtils.getLoginUser();
        Thumb thumb = new Thumb();
        thumb.setThumber(currentUser.getUserName());
        thumb.setCreatedBy(System.currentTimeMillis());
        this.imageWorkThumbMapper.thumbWorkById(thumbWorkForm.getId(),thumb);
    }

    @GetMapping("/checkIsImageWorkThumb")
    public boolean checkIsImageWorkThumb(String id){
        User currentUser = UserUtils.getLoginUser();
        return this.imageWorkThumbMapper.checkIsThumb(id,currentUser.getUserName());
    }

    @GetMapping("/getImageWorkThumbNumById")
    public Integer getImageWorkThumbNumById(String id){
        return this.imageWorkThumbMapper.getThumbNumById(id);
    }
}
