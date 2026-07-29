package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.VideoWorkDanmuMapper;
import com.duzheStudy.modules.worksDisplay.domain.Danmu;
import com.duzheStudy.modules.worksDisplay.domain.DanmuList;
import com.duzheStudy.modules.worksDisplay.domain.form.AddDanmuForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoWorkDanmuController {
    @Autowired
    private VideoWorkDanmuMapper videoWorkDanmuMapper;

    @GetMapping("/getVideoWorkDanmuById")
    public DanmuList getVideoWorkDanmuById(String id){
        return this.videoWorkDanmuMapper.getDanmuById(id);
    }

    @PostMapping("/addVideoWorkDanmuById")
    public void addVideoWorkDanmuById(@RequestBody AddDanmuForm addDanmuForm){
        User currentUser = UserUtils.getLoginUser();

        Danmu danmu = new Danmu();
        danmu.setText(addDanmuForm.getText());
        danmu.setColor("#ffffff");
        danmu.setCommentator(currentUser.getUserName());
        danmu.setTime(addDanmuForm.getTime());
        danmu.setCreatedBy(System.currentTimeMillis());

        this.videoWorkDanmuMapper.addDanmuById(addDanmuForm.getId(),danmu);
    }
}
