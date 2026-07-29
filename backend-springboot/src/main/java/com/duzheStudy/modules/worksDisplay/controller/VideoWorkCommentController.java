package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.VideoWorkCommentMapper;
import com.duzheStudy.modules.worksDisplay.domain.Comment;
import com.duzheStudy.modules.worksDisplay.domain.CommentList;
import com.duzheStudy.modules.worksDisplay.domain.form.AddCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VideoWorkCommentController {
    @Autowired
    private VideoWorkCommentMapper videoWorkCommentMapper;

    @GetMapping("/getVideoWorkCommentById")
    public CommentList getVideoWorkCommentById(String id){
        return this.videoWorkCommentMapper.getCommentById(id);
    }

    @PostMapping("/addVideoWorkCommentById")
    public void addVideoWorkCommentById(@RequestBody AddCommentForm addCommentForm){
        User currentUser = UserUtils.getLoginUser();
        Comment comment = new Comment();
        comment.setContent(addCommentForm.getContent());
        comment.setCommentator(currentUser.getUserName());
        comment.setCreatedBy(System.currentTimeMillis());
        this.videoWorkCommentMapper.addCommentById(addCommentForm.getId(),comment);
    }

    @GetMapping("/getVideoWorkCommentNumById")
    public Integer getVideoWorkCommentNumById(String id){
        return this.videoWorkCommentMapper.getCommentNumById(id);
    }
}
