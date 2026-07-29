package com.duzheStudy.modules.others.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.others.dao.OthersMapper;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MyController {

    @Autowired
    private OthersMapper othersMapper;

    @GetMapping("/getMyWorkList")
    public List<Work> getMyWorkList(@RequestParam(required = true,defaultValue = "1")Integer pageNo,
                                    @RequestParam(required = true,defaultValue = "6")Integer pageSize){
        User currentUser = UserUtils.getLoginUser();
        return this.othersMapper.getOthersWorkList(pageNo, pageSize, currentUser.getId());
    }

    @GetMapping("/getMyWorkListTotal")
    public Integer getMyWorkListTotal(){
        User currentUser = UserUtils.getLoginUser();
        return this.othersMapper.getOthersWorkListTotal(currentUser.getId());
    }

}
