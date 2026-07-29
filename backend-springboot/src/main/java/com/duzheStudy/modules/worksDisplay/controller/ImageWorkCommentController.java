package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.dao.ImageWorkCommentMapper;
import com.duzheStudy.modules.worksDisplay.domain.Comment;
import com.duzheStudy.modules.worksDisplay.domain.CommentList;
import com.duzheStudy.modules.worksDisplay.domain.form.AddCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageWorkCommentController {
    @Autowired
    private ImageWorkCommentMapper imageWorkCommentMapper;

    @GetMapping("/getImageWorkCommentById")
    public CommentList getImageWorkCommentById(String id){
        return this.imageWorkCommentMapper.getCommentById(id);
    }

    @PostMapping("/addImageWorkCommentById")
    public void addImageWorkCommentById(@RequestBody AddCommentForm addCommentForm){
        User currentUser = UserUtils.getLoginUser();
        Comment comment = new Comment();
        comment.setContent(addCommentForm.getContent());
        comment.setCommentator(currentUser.getUserName());
        comment.setCreatedBy(System.currentTimeMillis());
        this.imageWorkCommentMapper.addCommentById(addCommentForm.getId(),comment);
    }

    @GetMapping("/getImageWorkCommentNumById")
    public Integer getImageWorkCommentNumById(String id){
        return this.imageWorkCommentMapper.getCommentNumById(id);
    }
}
