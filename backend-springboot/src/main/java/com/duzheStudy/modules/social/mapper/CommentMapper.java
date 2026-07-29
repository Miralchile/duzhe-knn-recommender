package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
