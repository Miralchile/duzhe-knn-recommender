package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.modules.worksDisplay.Service.CategoryService;
import com.duzheStudy.modules.worksDisplay.Service.ChannelService;
import com.duzheStudy.modules.worksDisplay.domain.Category;
import com.duzheStudy.modules.worksDisplay.domain.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChannelController {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllChannel")
    public List<Map<String, Object>> getAllChannel(){
        return this.channelService.selectAllChannel();
    }

    @GetMapping("/getAllCategory")
    public List<Category> getAllCategory(){
        return this.categoryService.selectAll();
    }

    @GetMapping("/getAllChannelByCategoryId")
    public List<List<Channel>> getAllChannelByCategoryId(){
        return this.channelService.selectByCategoryId();
    }

    @GetMapping("/getChannelsByCategoryName")
    public List<Channel> getChannelsByCategoryName(String name){
        return this.channelService.selectByName(name);
    }

}
