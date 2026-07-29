package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 好友对象 soc_friend
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public class SocFriend extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long myId;

    /** 好友id */
    @Excel(name = "好友id")
    private Long friendId;

    /** 备注 */
    @Excel(name = "备注")
    private String notation;

    /** 缓存id */
    @Excel(name = "缓存id")
    private Long sessionId;

    /** 最后一条信息 */
    @Excel(name = "最后一条信息")
    private String lastMessage;

    /** 未读消息数 */
    @Excel(name = "未读消息数")
    private Long unread;

    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private Integer isHidden;

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
    public void setMyId(Long myId) 
    {
        this.myId = myId;
    }

    public Long getMyId() 
    {
        return myId;
    }
    public void setFriendId(Long friendId) 
    {
        this.friendId = friendId;
    }

    public Long getFriendId() 
    {
        return friendId;
    }
    public void setNotation(String notation) 
    {
        this.notation = notation;
    }

    public String getNotation() 
    {
        return notation;
    }
    public void setSessionId(Long sessionId) 
    {
        this.sessionId = sessionId;
    }

    public Long getSessionId() 
    {
        return sessionId;
    }
    public void setLastMessage(String lastMessage) 
    {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() 
    {
        return lastMessage;
    }
    public void setUnread(Long unread) 
    {
        this.unread = unread;
    }

    public Long getUnread() 
    {
        return unread;
    }
    public void setIsHidden(Integer isHidden) 
    {
        this.isHidden = isHidden;
    }

    public Integer getIsHidden() 
    {
        return isHidden;
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
            .append("myId", getMyId())
            .append("friendId", getFriendId())
            .append("notation", getNotation())
            .append("sessionId", getSessionId())
            .append("lastMessage", getLastMessage())
            .append("unread", getUnread())
            .append("isHidden", getIsHidden())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
