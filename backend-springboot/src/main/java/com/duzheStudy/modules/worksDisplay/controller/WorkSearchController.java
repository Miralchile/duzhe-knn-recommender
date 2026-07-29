package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.modules.worksDisplay.dao.WorkSearchMapper;
import com.duzheStudy.modules.worksDisplay.domain.Page;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class WorkSearchController {

    @Autowired
    private WorkSearchMapper workSearchMapper;


    /**
     * 首页查询，返回分页分页
     * @param keywords
     * @param pageNo
     * @return
     * @throws IOException
     */
    @GetMapping("/work/search")
    public Page search(String keywords,
                       @RequestParam(required = true,defaultValue = "1")Integer pageNo,Integer type) throws IOException {
        return this.workSearchMapper.search(keywords,pageNo,type);
    }

    /**
     * 一级分类二级分类查询， 返回
     * @param pageNo
     * @return
     * @throws IOException
     */
    @GetMapping("/work/searchByCategory")
    public List<Work> searchByCategory(
                       @RequestParam(required = true,defaultValue = "1")Integer pageNo,String categoryName,String channelName) throws IOException {
        return this.workSearchMapper.searchByCategory(pageNo,categoryName,channelName);
    }
}
