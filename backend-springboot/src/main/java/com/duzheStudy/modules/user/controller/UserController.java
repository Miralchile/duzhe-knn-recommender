package com.duzheStudy.modules.user.controller;

import com.duzheStudy.common.utils.JwtUtils;
import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Integer register(@RequestBody User user){
        user.setAvatarUrl("http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg");
        return this.userService.insert(user);
    }

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody User user){
        Map<String,Object> resultMap = new HashMap<>();
        User currentUser = this.userService.selectByUserName(user.getUserName());
        if(currentUser != null){
            String password = currentUser.getPassword();
            user.setId(currentUser.getId());
            if(password.equals(user.getPassword())){
                String token = JwtUtils.sign(user);
                resultMap.put("code",1);
                resultMap.put("token",token);
                UserUtils.setLoginUser(currentUser);
                return resultMap;
            } else {
                resultMap.put("code",0);
                return resultMap;
            }
        } else{
            resultMap.put("code",0);
            return resultMap;
        }
    }

    @GetMapping("/getCurrentUser")
    public User getCurrentUser(){
        User currentUser = UserUtils.getLoginUser();
        currentUser.setAvatarUrl(this.userService.selectById(currentUser.getId()).getAvatarUrl());
        return currentUser;
    }

}
