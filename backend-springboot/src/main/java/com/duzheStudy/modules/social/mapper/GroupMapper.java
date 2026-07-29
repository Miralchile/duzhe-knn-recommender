package com.duzheStudy.modules.social.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.domain.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GroupMapper extends BaseMapper<Group> {

    @Select("SELECT m.id,m.member_id, m.group_id, m.unread,m.last_message,m.is_hidden," +
            "m.is_deleted, m.create_time, date_format(m.update_time, '%Y-%m-%d %H:%i:%s') update_time,g.avatar,g.name " +
            "from soc_member m,soc_group g " +
            "where m.group_id=g.id and m.is_deleted=0 and member_id=#{id}")
    List<JSONObject> getList(String id);

    //获取用户成员的数据
    @Select("SELECT u.user_id,u.avatar,u.nick_name " +
            "from sys_user u,soc_member m " +
            "where m.is_deleted=0 and u.user_id=m.member_id and m.group_id=#{id} " +
            "ORDER BY m.create_time ASC ")
    List<JSONObject> getMembers(String id);


    // 解散群聊
    @Update("update soc_group,soc_member" +
            " set soc_group.is_deleted=1,soc_member.is_deleted=1 " +
            "where soc_group.id=soc_member.group_id and group_id=#{id}")
    void deleteGroup(String id);


}
