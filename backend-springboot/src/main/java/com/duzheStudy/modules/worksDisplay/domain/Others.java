package com.duzheStudy.modules.worksDisplay.domain;

import com.duzheStudy.modules.user.domain.User;

import java.util.List;

public class Others extends User {

    private List<Work> workList;

    public Others(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.avatarUrl = user.getAvatarUrl();
        this.password = user.getPassword();
        this.createdby = user.getCreatedby();
        this.updatedby = user.getUpdatedby();
    }

    public List<Work> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Work> workList) {
        this.workList = workList;
    }
}
