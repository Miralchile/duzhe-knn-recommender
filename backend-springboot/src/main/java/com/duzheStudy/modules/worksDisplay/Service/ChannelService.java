package com.duzheStudy.modules.worksDisplay.Service;

import com.duzheStudy.modules.worksDisplay.domain.Channel;

import java.util.List;
import java.util.Map;

public interface ChannelService {
    public int deleteById(Long id);

    public int insert(Channel channel);

    public Channel selectById(Long id);

    public int updateById(Channel channel);

    public List<Map<String, Object>> selectAllChannel();

    public List<List<Channel>> selectByCategoryId();

    public List<Channel> selectByName(String name);
}
