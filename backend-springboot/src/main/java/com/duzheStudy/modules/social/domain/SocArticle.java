package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 动态对象 soc_article
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public class SocArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 访问权限 0：全部人可见，1：仅好友可见 */
    @Excel(name = "访问权限 0：全部人可见，1：仅好友可见")
    private Long authority;

    /** 域名历史流 */
    @Excel(name = "域名历史流")
    private Long publishTime;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imgList;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long viewNum;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Integer isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setAuthority(Long authority) 
    {
        this.authority = authority;
    }

    public Long getAuthority() 
    {
        return authority;
    }
    public void setPublishTime(Long publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Long getPublishTime() 
    {
        return publishTime;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setImgList(String imgList) 
    {
        this.imgList = imgList;
    }

    public String getImgList() 
    {
        return imgList;
    }
    public void setViewNum(Long viewNum) 
    {
        this.viewNum = viewNum;
    }

    public Long getViewNum() 
    {
        return viewNum;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("authority", getAuthority())
            .append("publishTime", getPublishTime())
            .append("content", getContent())
            .append("imgList", getImgList())
            .append("viewNum", getViewNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
