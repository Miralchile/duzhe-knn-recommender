package com.duzheStudy.modules.social.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import com.duzheStudy.modules.social.domain.GroupMessage;
import com.duzheStudy.modules.social.domain.GroupMessageComplete;
import com.duzheStudy.modules.social.domain.Message;
import com.duzheStudy.modules.social.domain.MessageComplete;
import com.duzheStudy.modules.social.mapper.FriendMapper;
import com.duzheStudy.modules.social.mapper.GroupMessageMapper;
import com.duzheStudy.modules.social.mapper.MessageMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ry-vue/message")
public class MessageController {
    @Resource
    private MessageMapper messageMapper;
    @Resource
    private GroupMessageMapper groupMessageMapper;

    @Resource
    private FriendMapper friendMapper;

    ///获取用户消息
    @PostMapping("/person")
    public ResponseDto<?> person(@RequestBody Map<String,String> param){
        try{
            String pageSize = param.get("pageSize");
            String pageNum = param.get("pageNum");
            String sessionId = param.get("sessionId");
            String currentUserId = param.get("currentUserId");
            String lastMessageId = param.get("lastMessageId");
            if(pageSize==null||pageNum==null||sessionId==null) throw new Exception("获取消息时参数缺失");
            IPage<Message> page = new Page<>( Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("is_deleted", false).eq("session_id",sessionId);
            queryWrapper.eq("session_id",sessionId);
            queryWrapper.orderByDesc("id");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sessionId",sessionId);
            if(lastMessageId!=null){
                queryWrapper.le("id", lastMessageId);
            }
            //---------------------
            IPage pageResult = messageMapper.selectPage(page, queryWrapper);
            List<Message> messageList = pageResult.getRecords();
            List<MessageComplete> messageCompleteList = new ArrayList<>();
            for(Message message : messageList){
                Boolean isShowMessage = true;
                if(currentUserId.equals(message.getSenderId()) && message.getIsDeleted() == true){
                    isShowMessage = false;
                }
                MessageComplete messageComplete = new MessageComplete(message,isShowMessage);
                messageCompleteList.add(messageComplete);
            }
            pageResult.setRecords(messageCompleteList);

            //---------------------
            jsonObject.put("pageInfo",pageResult);
            return new ResponseDto<>(jsonObject);
        }catch (Exception e){
            return new ResponseDto<>(false, e.getMessage());
        }
    }

    //获取群聊消息
    ///获取用户消息
    @PostMapping("/group")
    public ResponseDto<?> group(@RequestBody Map<String,String> param){
        try{
            String pageSize = param.get("pageSize");
            String pageNum = param.get("pageNum");
            String groupId = param.get("groupId");
            String currentSenderId = param.get("currentSenderId");
            String lastMessageId = param.get("lastMessageId");
            if(pageSize==null||pageNum==null||groupId==null) throw new Exception("获群取消息时参数缺失");
            IPage<GroupMessage> page = new Page<>(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
            QueryWrapper<GroupMessage> queryWrapper = new QueryWrapper<>();
            //queryWrapper.eq("is_deleted", false).eq("group_id",groupId);

            queryWrapper.eq("group_id",groupId);
            queryWrapper.orderByDesc("id");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("groupId",groupId);
            if(lastMessageId!=null){
                queryWrapper.le("id", lastMessageId);
            }
            //

            IPage pageResult = groupMessageMapper.selectPage(page, queryWrapper);
            List<GroupMessage> groupmessageList = pageResult.getRecords();
            List<GroupMessageComplete> groupmessageCompleteList = new ArrayList<>();
            for(GroupMessage groupMessage : groupmessageList){
                Boolean isShowGroupMessage = true;
                if(currentSenderId.equals(groupMessage.getSenderId()) && groupMessage.getIsDeleted()  == true){
                    isShowGroupMessage = false;
                }
                GroupMessageComplete groupMessageComplete = new GroupMessageComplete(groupMessage,isShowGroupMessage);
                groupmessageCompleteList.add(groupMessageComplete);
            }
            pageResult.setRecords(groupmessageCompleteList);

            //
            //jsonObject.put("pageInfo",groupMessageMapper.selectPage(page, queryWrapper));
            jsonObject.put("pageInfo",pageResult);
            return new ResponseDto<>(jsonObject);
        }catch (Exception e){
            return new ResponseDto<>(false, e.getMessage());
        }
    }
    //删除消息
    @PostMapping("/delete")
    public ResponseDto<?> delete(@RequestBody Map<String,String> param){
        String id = param.get("id");
/*        String pageSize = param.get("pageSize");
        String pageNum = param.get("pageNum");
        String currentUserId = param.get("currentUserId");*/
        try {
//            if(id==null) throw new Exception("缺少id字段！");
            messageMapper.deleteMessage(id);

            return new ResponseDto<>("删除成功！");
        }catch (Exception e){
            return new ResponseDto<>(false, e.getMessage());
        }
    }

    @PostMapping("/clearMessage")
    public ResponseDto<?> clearMessage(@RequestBody Map<String,String> param){
        String sessionId = param.get("sessionId");
        try {
            if(sessionId==null) throw new Exception("缺少sessionId字段！");
            messageMapper.clearMessage(sessionId);
            friendMapper.deleteLastMessage(sessionId);
            return new ResponseDto<>("删除成功！");
        }catch (Exception e){
            return new ResponseDto<>(false, e.getMessage());
        }
    }
}
