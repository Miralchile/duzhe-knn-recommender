package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通知对象 soc_notice
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public class SocNotice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 发送者id */
    @Excel(name = "发送者id")
    private Long senderId;

    /** 接收者id */
    @Excel(name = "接收者id")
    private Long receiverId;

    /** 类型（同意、拒绝） */
    @Excel(name = "类型", readConverterExp = "同=意、拒绝")
    private String type;

    /** 信息 */
    @Excel(name = "信息")
    private String information;

    /** 是否已读 */
    @Excel(name = "是否已读")
    private Integer isRead;

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
    public void setSenderId(Long senderId) 
    {
        this.senderId = senderId;
    }

    public Long getSenderId() 
    {
        return senderId;
    }
    public void setReceiverId(Long receiverId) 
    {
        this.receiverId = receiverId;
    }

    public Long getReceiverId() 
    {
        return receiverId;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setInformation(String information) 
    {
        this.information = information;
    }

    public String getInformation() 
    {
        return information;
    }
    public void setIsRead(Integer isRead) 
    {
        this.isRead = isRead;
    }

    public Integer getIsRead() 
    {
        return isRead;
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
            .append("senderId", getSenderId())
            .append("receiverId", getReceiverId())
            .append("type", getType())
            .append("information", getInformation())
            .append("isRead", getIsRead())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
