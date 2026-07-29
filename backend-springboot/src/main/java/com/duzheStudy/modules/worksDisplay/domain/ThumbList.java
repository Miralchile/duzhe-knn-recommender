package com.duzheStudy.modules.worksDisplay.domain;

import java.util.List;

public class ThumbList {
    private List<Thumb> thumbList;
    private String _id;

    public List<Thumb> getThumbList() {
        return thumbList;
    }

    public void setThumbList(List<Thumb> thumbList) {
        this.thumbList = thumbList;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
