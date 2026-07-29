package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 圈信息对象 soc_group_message
 *
 * @author duzhe
 * @date 2022-11-21
 */
public class SocGroupMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 圈id */
    @Excel(name = "圈id")
    private Long groupId;

    /** 发送者id */
    @Excel(name = "发送者id")
    private Long senderId;

    /** 发送者 */
    @Excel(name = "发送者")
    private String senderName;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

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
    public void setGroupId(Long groupId)
    {
        this.groupId = groupId;
    }

    public Long getGroupId()
    {
        return groupId;
    }
    public void setSenderId(Long senderId)
    {
        this.senderId = senderId;
    }

    public Long getSenderId()
    {
        return senderId;
    }
    public void setSenderName(String senderName)
    {
        this.senderName = senderName;
    }

    public String getSenderName()
    {
        return senderName;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
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
                .append("groupId", getGroupId())
                .append("senderId", getSenderId())
                .append("senderName", getSenderName())
                .append("avatar", getAvatar())
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
