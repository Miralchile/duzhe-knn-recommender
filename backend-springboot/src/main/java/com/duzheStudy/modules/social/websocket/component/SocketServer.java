package com.duzheStudy.modules.social.websocket.component;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duzheStudy.modules.common.content.ScanText;
import com.duzheStudy.modules.social.domain.*;
import com.duzheStudy.modules.social.mapper.FriendMapper;
import com.duzheStudy.modules.social.mapper.MemberMapper;
import com.duzheStudy.modules.social.mapper.MessageMapper;
import com.duzheStudy.modules.social.mapper.NoticeMapper;
import com.duzheStudy.modules.social.service.FriendService;
import com.duzheStudy.modules.social.service.MessageService;
import com.duzheStudy.modules.social.util.SnowFlakeUtil;
import com.duzheStudy.modules.social.websocket.domain.SocketMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/socket/{connectId}")
@Component
public class SocketServer {
    public static Map<String, Session> userSessionMap = new ConcurrentHashMap<>();
    public static MessageMapper messageMapper;
    public static NoticeMapper noticeMapper;
    public static FriendMapper friendMapper;
    public static FriendService friendService;
    public static MessageService messageService;
    public static MemberMapper memberMapper;
    public static ScanText scanText;
    @Autowired
    public void  setMemberMapper(MemberMapper memberMapper){SocketServer.memberMapper = memberMapper;}
    @Autowired
    public void setMessageService(MessageService messageService){SocketServer.messageService = messageService;}
    @Autowired
    public void setFriendService(FriendService friendService){SocketServer.friendService = friendService;}
    @Autowired
    public void setFriendMapper(FriendMapper friendMapper){SocketServer.friendMapper = friendMapper;}
    @Autowired
    public void setMessageMapper(MessageMapper messageMapper){
        SocketServer.messageMapper = messageMapper;
    }
    @Autowired
    void setNoticeMapper(NoticeMapper noticeMapper){SocketServer.noticeMapper = noticeMapper;}
    @OnOpen
    public void onOpen(Session session, @PathParam("connectId")String connectId){
        ///检查connectId是否生效
        userSessionMap.put(connectId, session);
        //System.out.println("用户："+id+"已经上线");
        this.sendToAll(new SocketMessage<>("count", userSessionMap.keySet().toArray()));
    }
    @OnClose
    public void onClose(Session session, @PathParam("connectId") String connectId){
        userSessionMap.remove(connectId);
        //System.out.println("用户："+id+"已经离线");
        this.sendToAll(new SocketMessage<>("count", userSessionMap.keySet().toArray()));

    }
    @OnError
    public void onError(Session session, Throwable error, @PathParam("connectId") String connectId){
        userSessionMap.remove(connectId);
       // System.out.println("用户："+id+"已经离线");
        this.sendToAll(new SocketMessage<>("count", userSessionMap.keySet().toArray()));

    }
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("connectId") String connectId){
        JSONObject m = JSONUtil.parseObj(message);
        //System.out.println(m.get("type"));
        JSONObject data = m.getJSONObject("data");
        switch (m.getStr("type")){
            case "person-apply":
                ////构造通知消息对象
                Notice notice = new Notice(null,data.getLong("senderId"),
                        data.getLong("receiverId"),
                        "person-apply",
                        m.getStr("data"),false);

                noticeMapper.insert(notice);
                this.sendToUserById(data.getStr("receiverId"),new SocketMessage<>("person-apply",
                        m.getStr("data")));
                break;
            case "person-apply-agree":
                Notice notice1 = noticeMapper.selectById(data.getStr("id"));
                JSONObject jsonObject = JSONUtil.parseObj(notice1.getInformation());
                ////检查两人是否为好友
                if(friendService.checkIfFriend(notice1.getSenderId().toString(),
                        notice1.getReceiverId().toString())){
                    notice1.setIsRead(true);
                    noticeMapper.updateById(notice1);
                    this.sendToUserById(notice1.getReceiverId().toString(),
                            new SocketMessage<>("notice-refresh", "TA已经是你的好友了！"));
                    break;
                }
                ////构造好友关系
                Long sessionId = SnowFlakeUtil.getSnowFlakeId();
                Friend friend1 = new Friend(null,notice1.getSenderId(),notice1.getReceiverId(),
                        jsonObject.getStr("notation"),sessionId,"",0,false);
                Friend friend2 = new Friend(null,notice1.getReceiverId(),notice1.getSenderId(),
                        jsonObject.getStr("senderName"),sessionId,"",0,false);
                friendMapper.insert(friend1);
                friendMapper.insert(friend2);
                notice1.setIsRead(true);
                noticeMapper.updateById(notice1);
                ///构造消息
                SocketMessage<Friend> socketMessage = new SocketMessage<>("person-apply-agree", friend2);
                this.sendToUserById(notice1.getSenderId().toString(),socketMessage);
                this.sendToUserById(notice1.getReceiverId().toString(),socketMessage);
                break;
            case "person-message":
                ///////构造消息
                Message message1 = new Message(SnowFlakeUtil.getSnowFlakeId().toString(),data.getStr("sessionId"),
                        data.getStr("senderId"),
                        data.getStr("receiverId"),data.getStr("sendTime"),data.getStr("content"),
                        data.getStr("messageType"),
                        0);

                messageService.storeMessage(message1);
                this.sendToUserById(message1.getSenderId(),new SocketMessage<>("person-message",message1));
                this.sendToUserById(message1.getReceiverId(),new SocketMessage<>("person-message",message1));
                break;
            case "join-group":
                //////先检查该用户是否在群里
                String userId = data.getStr("userId");
                String groupId = data.getStr("groupId");
                QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("member_id",userId).eq("group_id",groupId).eq("is_deleted",false);
                List<Member> members = memberMapper.selectList(queryWrapper);
                if(members.size()!=0){
                    Member member = members.get(0);
                    this.sendToUserById(member.getMemberId().toString(),new SocketMessage<>("join-success", member));
                    return;
                }
                ////创建关系
                Member member = new Member(null,Long.parseLong(userId),groupId,0,"",false);
                memberMapper.insert(member);
                this.sendToUserById(member.getMemberId().toString(),new SocketMessage<>("join-success", member));
                break;
            case "group-message":
                ///查找该群的全部用户
                String group_id = data.getStr("groupId");
                QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
                memberQueryWrapper.eq("is_deleted",false).eq("group_id",group_id);
                memberQueryWrapper.select("member_id");
                ////保存聊天记录
                GroupMessage groupMessage = new GroupMessage(SnowFlakeUtil.getSnowFlakeId().toString(),group_id,data.getStr("senderId"),data.getStr("senderName"),
                        data.getStr("avatar"),data.getStr("sendTime"),data.getStr("content"),data.getStr("messageType"),0);
                messageService.StoreGroupMessage(groupMessage);
                List<Object> ids = memberMapper.selectObjs(memberQueryWrapper);
                for (Object id:ids){
                    ////发送给每一个人
                    this.sendToUserById(id.toString(),new SocketMessage<>("group-message", groupMessage));
                }
                break;
                ///收到了撤回消息
            case "person-withdraw":
                String messageId = data.getStr("messageId");
                String senderId = data.getStr("senderId");
                String receiverId = data.getStr("receiverId");
                ///更新数据库
                messageService.withdrawMessage(messageId);
                /////发送给用户
                this.sendToUserById(senderId, new SocketMessage<>("person-withdraw", data));
                this.sendToUserById(receiverId, new SocketMessage<>("person-withdraw", data));

            case "group-withdraw":
                String groupMessageId = data.getStr("messageId");
                String groupId1 = data.getStr("groupId");
                ///更新数据库
                messageService.withdrawGroupMessage(groupMessageId);
                /////发送给用户
                QueryWrapper<Member> memberQueryWrapper1 = new QueryWrapper<>();
                memberQueryWrapper1.eq("is_deleted",false).eq("group_id",groupId1);
                memberQueryWrapper1.select("member_id");
                List<Object> ids1 = memberMapper.selectObjs(memberQueryWrapper1);
                for (Object id:ids1){
                    ////发送给每一个人
                    this.sendToUserById(id.toString(),new SocketMessage<>("group-withdraw", data));
                }
                break;
        }

    }
    public void sendToAll(SocketMessage<?> message){
        for(Session session:userSessionMap.values()){
            this.sendMessage(message,session);
        }
    }

    public void sendToUserById(String id,SocketMessage<?> message){
        for(String key:userSessionMap.keySet()){
            if(Objects.equals(key, id)){
                this.sendMessage(message,userSessionMap.get(key));
                return;
            }
        }
    }

    public void sendMessage(SocketMessage<?> message, Session toSession){
        try {
            if(toSession.isOpen()) {
                synchronized (toSession) {
                    toSession.getAsyncRemote().sendText(JSONUtil.toJsonStr(message));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
