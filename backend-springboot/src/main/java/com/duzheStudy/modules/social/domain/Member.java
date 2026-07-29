package com.duzheStudy.modules.social.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
@TableName("soc_member")
public class Member {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String groupId;
    private Integer unread;
    private String lastMessage;
    private Boolean isHidden;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Member(Long id, Long memberId, String groupId, Integer unread, String lastMessage, Boolean isHidden) {
        this.id = id;
        this.memberId = memberId;
        this.groupId = groupId;
        this.unread = unread;
        this.lastMessage = lastMessage;
        this.isHidden = isHidden;
    }
}
