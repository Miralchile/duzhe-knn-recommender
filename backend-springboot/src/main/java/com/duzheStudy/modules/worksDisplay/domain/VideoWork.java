package com.duzheStudy.modules.worksDisplay.domain;

import java.util.List;

public class VideoWork extends Work{

    protected String introduction;
    protected Long duration;
    protected String url;
    protected String url_480;
    protected String url_720;
    protected List<Danmu> danmuList;

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_480() {
        return url_480;
    }

    public void setUrl_480(String url_480) {
        this.url_480 = url_480;
    }

    public String getUrl_720() {
        return url_720;
    }

    public void setUrl_720(String url_720) {
        this.url_720 = url_720;
    }

    public List<Danmu> getDanmuList() {
        return danmuList;
    }

    public void setDanmuList(List<Danmu> danmuList) {
        this.danmuList = danmuList;
    }
}
