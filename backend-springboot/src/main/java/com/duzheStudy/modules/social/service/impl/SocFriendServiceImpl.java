package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocFriend;
import com.duzheStudy.modules.social.mapper.SocFriendMapper;
import com.duzheStudy.modules.social.service.ISocFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 好友Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocFriendServiceImpl implements ISocFriendService
{
    @Autowired
    private SocFriendMapper socFriendMapper;

    /**
     * 查询好友
     * 
     * @param id 好友主键
     * @return 好友
     */
    @Override
    public SocFriend selectSocFriendById(Long id)
    {
        return socFriendMapper.selectSocFriendById(id);
    }

    /**
     * 查询好友列表
     * 
     * @param socFriend 好友
     * @return 好友
     */
    @Override
    public List<SocFriend> selectSocFriendList(SocFriend socFriend)
    {
        return socFriendMapper.selectSocFriendList(socFriend);
    }

    /**
     * 新增好友
     * 
     * @param socFriend 好友
     * @return 结果
     */
    @Override
    public int insertSocFriend(SocFriend socFriend)
    {
        socFriend.setCreateTime(DateUtils.getNowDate());
        return socFriendMapper.insertSocFriend(socFriend);
    }

    /**
     * 修改好友
     * 
     * @param socFriend 好友
     * @return 结果
     */
    @Override
    public int updateSocFriend(SocFriend socFriend)
    {
        socFriend.setUpdateTime(DateUtils.getNowDate());
        return socFriendMapper.updateSocFriend(socFriend);
    }

    /**
     * 批量删除好友
     * 
     * @param ids 需要删除的好友主键
     * @return 结果
     */
    @Override
    public int deleteSocFriendByIds(Long[] ids)
    {
        return socFriendMapper.deleteSocFriendByIds(ids);
    }

    /**
     * 删除好友信息
     * 
     * @param id 好友主键
     * @return 结果
     */
    @Override
    public int deleteSocFriendById(Long id)
    {
        return socFriendMapper.deleteSocFriendById(id);
    }
}
