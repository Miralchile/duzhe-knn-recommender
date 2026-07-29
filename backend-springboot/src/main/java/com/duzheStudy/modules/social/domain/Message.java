package com.duzheStudy.modules.social.domain;

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
@TableName("soc_message")
public class Message {
    private String id;
    private String sessionId;
    private String senderId;   ///发送者id
    private String receiverId; //接收者id
    private String sendTime;  //发送时间
    private String content;  //发送内容
    private String messageType;  //消息类型
    private Integer isWithdrawn;  //是否撤回
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Message(String id, String sessionId, String senderId, String receiverId, String sendTime, String content, String messageType, Integer isWithdrawn) {
        this.id = id;
        this.sessionId = sessionId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.sendTime = sendTime;
        this.content = content;
        this.messageType = messageType;
        this.isWithdrawn = isWithdrawn;
    }
}
