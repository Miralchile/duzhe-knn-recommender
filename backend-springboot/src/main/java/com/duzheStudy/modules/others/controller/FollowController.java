package com.duzheStudy.modules.others.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.others.service.FollowService;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.others.domain.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/getFollowList")
    public List<Map<String,Object>> getFollowList(){
        User currentUser = UserUtils.getLoginUser();
        return this.followService.selectByUserId(currentUser.getId());
    }

    @GetMapping("/followThisUser")
    public void followThisUser(Long userId){
        User currentUser = UserUtils.getLoginUser();
        Follow follow = new Follow();
        follow.setFollowUserId(currentUser.getId());
        follow.setUserId(userId);
        this.followService.insert(follow);
    }

    @GetMapping("/cancelFollowThisUser")
    public void cancelFollowThisUser(Long userId){
        User currentUser = UserUtils.getLoginUser();
        this.followService.deleteByUserIdAndFollowUserId(userId,currentUser.getId());
    }

}
