package com.duzheStudy.modules.worksDisplay.domain;

import java.util.List;

public class CommentList {
    private String _id;
    private List<Comment> commentList;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
