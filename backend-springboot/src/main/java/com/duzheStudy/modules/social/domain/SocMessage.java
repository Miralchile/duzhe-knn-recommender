package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 好友信息对象 soc_message
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public class SocMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** session_id */
    @Excel(name = "session_id")
    private Long sessionId;

    /** 发送者id */
    @Excel(name = "发送者id")
    private Long senderId;

    /** 接收者id */
    @Excel(name = "接收者id")
    private Long receiverId;

    /** 发送时间 */
    @Excel(name = "发送时间")
    private String sendTime;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 消息类型 */
    @Excel(name = "消息类型")
    private String messageType;

    /** 是否撤回 */
    @Excel(name = "是否撤回")
    private Long isWithdrawn;

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
    public void setSessionId(Long sessionId) 
    {
        this.sessionId = sessionId;
    }

    public Long getSessionId() 
    {
        return sessionId;
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
    public void setSendTime(String sendTime) 
    {
        this.sendTime = sendTime;
    }

    public String getSendTime() 
    {
        return sendTime;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setIsWithdrawn(Long isWithdrawn) 
    {
        this.isWithdrawn = isWithdrawn;
    }

    public Long getIsWithdrawn() 
    {
        return isWithdrawn;
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
            .append("sessionId", getSessionId())
            .append("senderId", getSenderId())
            .append("receiverId", getReceiverId())
            .append("sendTime", getSendTime())
            .append("content", getContent())
            .append("messageType", getMessageType())
            .append("isWithdrawn", getIsWithdrawn())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
