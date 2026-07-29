package com.duzheStudy.modules.worksDisplay.domain;

public class BestAnswer {

    private String id;

    private Answer bestAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Answer getBestAnswer() {
        return bestAnswer;
    }

    public void setBestAnswer(Answer bestAnswer) {
        this.bestAnswer = bestAnswer;
    }
}
