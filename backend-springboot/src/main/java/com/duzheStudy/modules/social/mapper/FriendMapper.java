package com.duzheStudy.modules.social.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.controller.FriendController;
import com.duzheStudy.modules.social.domain.Friend;
import com.duzheStudy.modules.social.service.impl.MessageServiceImp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendMapper extends BaseMapper<Friend> {

    @Select("SELECT f.id,f.friend_id,f.notation,f.session_id,f.last_message,f.unread,f.is_hidden,date_format(f.update_time, '%Y-%m-%d %H:%i:%s') update_time,u.avatar " +
            "FROM soc_friend f,sys_user u " +
            "where u.user_id=f.friend_id and f.is_deleted=0 and f.my_id=#{myId}")
    List<JSONObject> getFriendList(String myId);

    ///更新状态
    @Update("update soc_friend " +
            "set last_message=#{lastMessage},unread=unread+1,update_time=NOW() " +
            "where session_id=#{sessionId} and is_deleted=0")
    void updateFriend(MessageServiceImp.TestParam param);

    ///清空已读
    @Update("update soc_friend " +
            "set unread=0 " +
            "where my_id=#{myId} and friend_id=#{friendId}")
    void clearUnread(FriendController.ClearParam param);

    @Update("update soc_friend " +
            "set last_message=#{lastMessage} " +
            "where session_id=#{sessionId} and is_deleted=0")
    void updateSession(MessageServiceImp.TestParam param);

    @Select("SELECT friend_id from soc_friend " +
            "WHERE my_id=#{myId} AND is_deleted=0")
    List<String> getFriendIds(String myId);


    @Update("update soc_friend " +
            "set is_deleted=1 " +
            "WHERE session_id=#{sessionId}" )
    void deleteSession(String sessionId);

    @Update("update soc_friend " +
            "set last_message=null " +
            "WHERE session_id=#{sessionId}" )
    void deleteLastMessage(String sessionId);


//    //获取用户信息
//    @Select("select * from sys_user where user_id = #{arg0}")
//    public Map<String, Object> getUserInfo(Integer uid);
}
