package com.duzheStudy.modules.social.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class MessageComplete extends Message{

    private Boolean isShowMessage;

    public MessageComplete(Message message,Boolean isShowMessage) {
        super(message.getId(), message.getSessionId(), message.getSenderId(), message.getReceiverId(),
                message.getSendTime(), message.getContent(), message.getMessageType(), message.getIsWithdrawn());
        this.isShowMessage = isShowMessage;
    }


}
