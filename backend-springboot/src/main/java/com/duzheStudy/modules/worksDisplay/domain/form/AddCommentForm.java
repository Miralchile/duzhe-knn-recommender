package com.duzheStudy.modules.worksDisplay.domain.form;

import com.duzheStudy.modules.worksDisplay.domain.Comment;

public class AddCommentForm{
    private String id;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
