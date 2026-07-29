package com.duzheStudy.modules.worksDisplay.domain.vo;

import com.duzheStudy.modules.worksDisplay.domain.Danmu;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;

import java.util.List;

public class VideoWorkVo extends VideoWork {

    private String authorName;

    private Long authorFollowsNumber;

    private String authorAvatarUrl;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAuthorFollowsNumber() {
        return authorFollowsNumber;
    }

    public void setAuthorFollowsNumber(Long authorFollowsNumber) {
        this.authorFollowsNumber = authorFollowsNumber;
    }

    public String getAuthorAvatarUrl() {
        return authorAvatarUrl;
    }

    public void setAuthorAvatarUrl(String authorAvatarUrl) {
        this.authorAvatarUrl = authorAvatarUrl;
    }

    public VideoWorkVo(VideoWork videoWork){
        this._id = videoWork.get_id();
        this.workName = videoWork.getWorkName();
        this.title = videoWork.getTitle();
        this.createdBy = videoWork.getCreatedBy();
        this.author = videoWork.getAuthor();
        this.views = videoWork.getViews();
        this.category = videoWork.getCategory();
        this.channel = videoWork.getChannel();
        this.tagList = videoWork.getTagList();
        this.type = videoWork.getType();
        this.coverImage = videoWork.getCoverImage();
        this.thumbList = videoWork.getThumbList();
        this.commentList = videoWork.getCommentList();
        this.auditStatus = videoWork.getAuditStatus();
        this.introduction = videoWork.getIntroduction();
        this.duration = videoWork.getDuration();
        this.url = videoWork.getUrl();
        this.url_480 = videoWork.getUrl_480();
        this.url_720 = videoWork.getUrl_720();
        this.danmuList = videoWork.getDanmuList();
    }
}
