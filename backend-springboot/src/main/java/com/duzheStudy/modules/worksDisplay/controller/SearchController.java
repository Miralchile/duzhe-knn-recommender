package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.modules.worksDisplay.dao.SearchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private SearchMapper searchMapper;

    @GetMapping("/suggestSearch")
    public List<String> suggestSearch(String prefix) throws IOException {
        return this.searchMapper.suggestSearch(prefix);
    }

    @GetMapping("/search")
    public List<Map<String, Object>> search(String keywords,
                                            @RequestParam(required = true,defaultValue = "1")Integer pageNo,
                                            @RequestParam(required = true,defaultValue = "8")Integer pageSize,
                                            @RequestParam(required = true,defaultValue = "0")Integer type,
                                            String sortField) throws IOException {
        Integer from = (pageNo-1)*pageSize;
        Integer size = pageSize;
        return this.searchMapper.conditionSearch(keywords, from, size, type, sortField);
    }

    @GetMapping("/searchTotal")
    public Integer searchTotal(String keywords,
                                            @RequestParam(required = true,defaultValue = "0")Integer type) throws IOException {

        return this.searchMapper.conditionSearchTotal(keywords, type);
    }

}
