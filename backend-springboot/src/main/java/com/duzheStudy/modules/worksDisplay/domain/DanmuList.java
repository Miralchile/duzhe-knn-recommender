package com.duzheStudy.modules.worksDisplay.domain;

import java.util.List;

public class DanmuList {
    private String _id;
    private List<Danmu> danmuList;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Danmu> getDanmuList() {
        return danmuList;
    }

    public void setDanmuList(List<Danmu> danmuList) {
        this.danmuList = danmuList;
    }
}
