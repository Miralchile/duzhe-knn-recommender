package com.duzheStudy.modules.social.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GroupMessageComplete extends GroupMessage{
    private  Boolean isShowGroupMessage;

    public GroupMessageComplete(GroupMessage groupMessage,Boolean isShowGroupMessage){

        super(groupMessage.getId(),groupMessage.getGroupId(),groupMessage.getSenderId(),groupMessage.getSenderName(),
                groupMessage.getAvatar(),groupMessage.getSendTime(),groupMessage.getContent(),
                groupMessage.getMessageType(),groupMessage.getIsWithdrawn());
        this.isShowGroupMessage = isShowGroupMessage;
    }
}
