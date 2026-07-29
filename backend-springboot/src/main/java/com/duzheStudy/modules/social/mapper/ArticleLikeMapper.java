package com.duzheStudy.modules.social.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duzheStudy.modules.social.domain.ArticleLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
}
