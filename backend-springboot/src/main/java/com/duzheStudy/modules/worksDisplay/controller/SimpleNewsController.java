package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.modules.worksDisplay.dao.ImageWorkMapper;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SimpleNewsController {

    @Autowired
    private ImageWorkMapper imageWorkMapper;

    @GetMapping("/getSimpleNewsImageList")
    public List<ImageWork> getSimpleNewsList(){
        return this.imageWorkMapper.getSimpleNewsImageList();
    }
}
