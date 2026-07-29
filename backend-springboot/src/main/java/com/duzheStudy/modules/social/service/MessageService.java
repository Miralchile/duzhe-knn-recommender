package com.duzheStudy.modules.social.service;


import com.duzheStudy.modules.social.domain.GroupMessage;
import com.duzheStudy.modules.social.domain.Message;

public interface MessageService {
    void storeMessage(Message message);
    void StoreGroupMessage(GroupMessage groupMessage);
    void withdrawMessage(String id);
    void withdrawGroupMessage(String id);
    void deleteMessage(String id);
    void deleteGroupMessage(String id);
    void clearMessage(String id);
}
