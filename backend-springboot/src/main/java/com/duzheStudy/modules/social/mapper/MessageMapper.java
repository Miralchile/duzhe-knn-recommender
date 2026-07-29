package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    //撤回消息
    @Update("update soc_message " +
            "set is_withdrawn=1 " +
            "where id=#{id}")
    void withdrawMessage(String id);

    //删除消息
    @Update("update soc_message " +
            "set is_deleted=1 " +
            "where id=#{id}")
    void deleteMessage(String id);

    @Update("update soc_message " +
            "set is_deleted=1 " +
            "WHERE session_id=#{sessionId}" )
    void clearMessage(String id);
}
