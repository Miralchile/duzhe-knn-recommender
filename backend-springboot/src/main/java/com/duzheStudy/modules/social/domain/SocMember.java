package com.duzheStudy.modules.social.domain;

import com.duzheStudy.modules.common.annotation.Excel;
import com.duzheStudy.modules.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 圈内成员对象 soc_member
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public class SocMember extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 圈成员id */
    @Excel(name = "圈成员id")
    private Long memberId;

    /** 圈id */
    @Excel(name = "圈id")
    private Long groupId;

    /** 未读消息 */
    @Excel(name = "未读消息")
    private Long unread;

    /** 最后一条消息 */
    @Excel(name = "最后一条消息")
    private String lastMessage;

    /** 是否隐藏 */
    @Excel(name = "是否隐藏")
    private String isHidden;

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
    public void setMemberId(Long memberId) 
    {
        this.memberId = memberId;
    }

    public Long getMemberId() 
    {
        return memberId;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setUnread(Long unread) 
    {
        this.unread = unread;
    }

    public Long getUnread() 
    {
        return unread;
    }
    public void setLastMessage(String lastMessage) 
    {
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() 
    {
        return lastMessage;
    }
    public void setIsHidden(String isHidden) 
    {
        this.isHidden = isHidden;
    }

    public String getIsHidden() 
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
            .append("memberId", getMemberId())
            .append("groupId", getGroupId())
            .append("unread", getUnread())
            .append("lastMessage", getLastMessage())
            .append("isHidden", getIsHidden())
            .append("isDeleted", getIsDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
