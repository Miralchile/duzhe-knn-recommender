package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.controller.GroupController;
import com.duzheStudy.modules.social.domain.Member;
import com.duzheStudy.modules.social.service.impl.MessageServiceImp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberMapper extends BaseMapper<Member> {

    @Update("update soc_member " +
            "set unread=0 " +
            "where member_id=#{memberId} and group_id=#{groupId}")
    void clearUnread(GroupController.MemberParam memberParam);

    ///更新状态
    @Update("update soc_member " +
            "set last_message=#{lastMessage},unread=unread+1,update_time=NOW() " +
            "where group_id=#{sessionId} and is_deleted=0")
    void updateFriend(MessageServiceImp.TestParam param);

    //退出群聊
    @Update("update soc_member " +
            "set is_deleted=1 ,update_time=NOW()" +
            "where member_id=#{memberId} and group_id=#{groupId}")
    void exitGroup(GroupController.MemberParam memberParam);
}
