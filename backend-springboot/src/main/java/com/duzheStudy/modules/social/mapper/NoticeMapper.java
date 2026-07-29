package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.domain.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface NoticeMapper extends BaseMapper<Notice> {
    @Update("update soc_notice " +
            "set is_deleted=1 " +
            "where id=#{id}")
    void deleteNotice(String id);
    @Update("update soc_notice " +
            "set is_read=1 " +
            "where id=#{id}")
    void checkNotice(String id);
}
