package com.duzheStudy.modules.worksDisplay.mapper;

import com.duzheStudy.modules.worksDisplay.domain.Channel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelMapper {
    int deleteById(Long id);

    int insert(Channel channel);

    Channel selectById(Long id);

    int updateById(Channel channel);

    List<Channel> selectByCategoryId(Long categoryId);

    List<Channel> selectByName(String name);
}