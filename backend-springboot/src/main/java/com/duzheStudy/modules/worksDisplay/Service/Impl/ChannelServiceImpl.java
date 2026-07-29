package com.duzheStudy.modules.worksDisplay.Service.Impl;

import com.duzheStudy.modules.worksDisplay.Service.ChannelService;
import com.duzheStudy.modules.worksDisplay.mapper.CategoryMapper;
import com.duzheStudy.modules.worksDisplay.mapper.ChannelMapper;
import com.duzheStudy.modules.worksDisplay.domain.Category;
import com.duzheStudy.modules.worksDisplay.domain.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public int deleteById(Long id){
        return this.channelMapper.deleteById(id);
    }

    public int insert(Channel channel){
        return this.channelMapper.insert(channel);
    }

    public Channel selectById(Long id){
        return this.channelMapper.selectById(id);
    }

    public int updateById(Channel channel){
        return this.channelMapper.updateById(channel);
    }

    @Override
    public List<Map<String, Object>> selectAllChannel() {
        List<Category> categoryList = this.categoryMapper.selectAll();
        List<Map<String, Object>> result = new ArrayList<>();
        for(Category category : categoryList){
            Map<String, Object> map = new HashMap<>();
            map.put("category",category.getCategoryName());
            map.put("channelList",this.channelMapper.selectByCategoryId(category.getId()));
            result.add(map);
        }
        return result;
    }

    public List<List<Channel>> selectByCategoryId(){
        List<Category> categoryList = this.categoryMapper.selectAll();
        List<List<Channel>> result = new ArrayList<>();
        for(Category category:categoryList){
            List<Channel> channelList = this.channelMapper.selectByCategoryId(category.getId());
            result.add(channelList);
        }
        return result;
    }

    @Override
    public List<Channel> selectByName(String name) {
        return channelMapper.selectByName(name);
    }
}
