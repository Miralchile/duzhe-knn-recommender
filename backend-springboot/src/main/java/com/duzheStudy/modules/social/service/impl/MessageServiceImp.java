package com.duzheStudy.modules.social.service.impl;


import com.duzheStudy.modules.social.domain.GroupMessage;
import com.duzheStudy.modules.social.domain.Message;
import com.duzheStudy.modules.social.mapper.FriendMapper;
import com.duzheStudy.modules.social.mapper.GroupMessageMapper;
import com.duzheStudy.modules.social.mapper.MemberMapper;
import com.duzheStudy.modules.social.mapper.MessageMapper;
import com.duzheStudy.modules.social.service.MessageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class MessageServiceImp implements MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private FriendMapper friendMapper;

    @Resource
    private GroupMessageMapper groupMessageMapper;
    @Resource
    private MemberMapper memberMapper;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TestParam{
        public String sessionId;
        public String lastMessage;
    }
   // @Async
    @Override
    @Transactional
    public void storeMessage(Message message) {
        ///保存聊天记录并且更新未读信息
        try {
            messageMapper.insert(message);
            String lastMessage = "";
            switch (message.getMessageType()){
                case "image":
                    lastMessage="【图片】";break;
                case "file":
                    lastMessage="【文件】";break;
                case "audio":
                    lastMessage="【语音】";break;
                case "video":
                    lastMessage="【视频】";break;
                default:
                    lastMessage = message.getContent();
            }
            friendMapper.updateFriend(new TestParam(message.getSessionId(),lastMessage));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void StoreGroupMessage(GroupMessage groupMessage) {
        ///保存聊天记录并且更新未读信息
        try {
            groupMessageMapper.insert(groupMessage);
            String lastMessage = "";
            switch (groupMessage.getMessageType()){
                case "image":
                    lastMessage=groupMessage.getSenderName()+":【图片】";break;
                case "file":
                    lastMessage=groupMessage.getSenderName()+":【文件】";break;
                case "audio":
                    lastMessage=groupMessage.getSenderName()+":【语音】";break;
                case "video":
                    lastMessage=groupMessage.getSenderName()+":【视频】";break;
                default:
                    lastMessage = groupMessage.getSenderName()+":"+groupMessage.getContent();
            }
            memberMapper.updateFriend(new TestParam(groupMessage.getGroupId(),lastMessage));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void withdrawMessage(String id) {
        messageMapper.withdrawMessage(id);
    }

    @Override
    public void withdrawGroupMessage(String id) {
        groupMessageMapper.withdrawMessage(id);
    }

    @Override
    public void deleteMessage(String id) {
        messageMapper.deleteMessage(id);
    }

    @Override
    public void deleteGroupMessage(String id) {
        groupMessageMapper.deleteGroupMessage(id);
    }

    @Override
    public void clearMessage(String id) {
        messageMapper.clearMessage(id);
    }
}
