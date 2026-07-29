package com.duzheStudy.modules.worksDisplay.domain.vo;

import com.duzheStudy.modules.worksDisplay.domain.ImageWork;

public class ImageWorkVo extends ImageWork {
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

    public ImageWorkVo(ImageWork imageWork){
        this._id = imageWork.get_id();
        this.workName = imageWork.getWorkName();
        this.title = imageWork.getTitle();
        this.createdBy = imageWork.getCreatedBy();
        this.author = imageWork.getAuthor();
        this.views = imageWork.getViews();
        this.category = imageWork.getCategory();
        this.channel = imageWork.getChannel();
        this.tagList = imageWork.getTagList();
        this.type = imageWork.getType();
        this.coverImage = imageWork.getCoverImage();
        this.thumbList = imageWork.getThumbList();
        this.commentList = imageWork.getCommentList();
        this.auditStatus = imageWork.getAuditStatus();
        this.content = imageWork.getContent();
    }
}
