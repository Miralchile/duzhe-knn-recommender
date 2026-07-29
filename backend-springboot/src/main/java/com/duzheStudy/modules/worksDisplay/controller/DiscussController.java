package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.DiscussionMapper;
import com.duzheStudy.modules.worksDisplay.domain.Answer;
import com.duzheStudy.modules.worksDisplay.domain.AnswerList;
import com.duzheStudy.modules.worksDisplay.domain.BestAnswer;
import com.duzheStudy.modules.worksDisplay.domain.Discussion;
import com.duzheStudy.modules.worksDisplay.domain.form.AddAnswerForm;
import com.duzheStudy.modules.worksDisplay.domain.form.AddBestAnswerForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiscussController {

    @Autowired
    private DiscussionMapper discussionMapper;

    @PostMapping("/addDiscussion")
    public void addDiscussion(@RequestBody Discussion discussion) throws IOException {
        User currentUser = UserUtils.getLoginUser();
        discussion.setAuthor(currentUser.getId());
        discussion.setCreatedBy(System.currentTimeMillis());
        discussion.setViews((long) 0);
        discussion.setAnswerList(new ArrayList<>());
        discussion.setBestAnswer(null);
        this.discussionMapper.addDiscussion(discussion);
    }

    @GetMapping("/getDiscussionRecommendList")
    public List<Discussion> getDiscussionRecommendList(Integer pageNo){
        return this.discussionMapper.getDiscussionRecommendList(pageNo);
    }

    @GetMapping("/getDiscussionByChannel")
    public List<Discussion> getDiscussionByChannel(Integer pageNo, String channel){
        return this.discussionMapper.getDiscussionByChannel(pageNo,channel);
    }

    @GetMapping("/getDiscussionById")
    public Discussion getDiscussionById(String id){
        return this.discussionMapper.getDiscussionById(id);
    }

    @PostMapping("/addDiscussionBestAnswer")
    public void addDiscussionBestAnswer(@RequestBody AddBestAnswerForm addBestAnswerForm){
        Answer answer = new Answer();
        answer.setAnswerer(addBestAnswerForm.getAnswerer());
        answer.setCreatedBy(addBestAnswerForm.getCreatedBy());
        answer.setContent(addBestAnswerForm.getContent());
        this.discussionMapper.addDiscussionBestAnswer(answer,addBestAnswerForm.getId());
    }

    @PostMapping("/addAnswerById")
    public void addAnswerById(@RequestBody AddAnswerForm addAnswerForm){
        User currentUser = UserUtils.getLoginUser();
        Answer answer = new Answer();
        answer.setContent(addAnswerForm.getContent());
        answer.setCreatedBy(System.currentTimeMillis());
        answer.setAnswerer(currentUser.getUserName());
        this.discussionMapper.addAnswerById(addAnswerForm.getId(),answer);
    }

    @GetMapping("/getAnswerById")
    public AnswerList getAnswerById(String id) {
        return this.discussionMapper.getAnswerById(id);
    }

    @GetMapping("/getBestAnswerById")
    public BestAnswer getBestAnswerById(String id) {
        return this.discussionMapper.getBestAnswerById(id);
    }

    @GetMapping("/cancelBestAnswerById")
    public void cancelBestAnswerById(String id) {
        this.discussionMapper.cancelBestAnswerById(id);
    }

    @GetMapping("/checkIsQuestioner")
    public Boolean checkIsQuestioner(String id) {
        User currentUser = UserUtils.getLoginUser();
        return this.discussionMapper.checkIsQuestioner(id,currentUser.getId());
    }
}
