package com.duzheStudy.modules.others.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.others.service.FollowService;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.mapper.UserMapper;
import com.duzheStudy.modules.others.dao.OthersMapper;
import com.duzheStudy.modules.worksDisplay.domain.Others;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OthersController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OthersMapper othersMapper;

    @Autowired
    private FollowService followService;

    @GetMapping("/getOthersById")
    public Others getOthersById(@RequestParam(required = true,defaultValue = "1")Integer pageNo,
                                @RequestParam(required = true,defaultValue = "6")Integer pageSize,
                                Long userId){
        User user = this.userMapper.selectById(userId);
        List<Work> workList = this.othersMapper.getOthersWorkList(pageNo, pageSize, userId);
        Others others = new Others(user);
        others.setWorkList(workList);
        return others;
    }

    @GetMapping("/getOthersWorkListTotal")
    public Integer getOthersWorkListTotal(Long userId){
        return this.othersMapper.getOthersWorkListTotal(userId);
    }

    @GetMapping("/isFollowThisUser")
    public Boolean isFollowThisUser(Long userId){
        User currentUser = UserUtils.getLoginUser();
        return this.followService.isFollowThisUser(userId, currentUser.getId());
    }

}
