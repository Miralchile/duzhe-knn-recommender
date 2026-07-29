package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.modules.worksDisplay.dao.HotMapper;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HotController {

    @Autowired
    private HotMapper hotMapper;

    @GetMapping("/getHotImageWork")
    public List<ImageWork> getHotImageWork(){
        return this.hotMapper.getHotImageWork();
    }

    @GetMapping("/getHotVideoWork")
    public List<VideoWork> getHotVideoWork(){
        return this.hotMapper.getHotVideoWork();
    }

}
