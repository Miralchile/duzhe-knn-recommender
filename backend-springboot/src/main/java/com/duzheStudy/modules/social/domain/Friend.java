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
@TableName("soc_friend")
public class Friend  {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long myId;
    private Long friendId;
    private String notation;
    private Long sessionId;
    private String lastMessage;
    private Integer unread;
    private Boolean isHidden;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Friend(Long id, Long myId, Long friendId, String notation, Long sessionId, String lastMessage, Integer unread, Boolean isHidden) {
        this.id = id;
        this.myId = myId;
        this.friendId = friendId;
        this.notation = notation;
        this.sessionId = sessionId;
        this.lastMessage = lastMessage;
        this.unread = unread;
        this.isHidden = isHidden;
    }
}
