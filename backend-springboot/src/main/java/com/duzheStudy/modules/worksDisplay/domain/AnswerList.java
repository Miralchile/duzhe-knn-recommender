package com.duzheStudy.modules.worksDisplay.domain;

import java.util.List;

public class AnswerList {

    private String _id;
    private List<Answer> answerList;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
    }
}
