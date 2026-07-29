package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocFriend;

import java.util.List;

/**
 * 好友Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocFriendService 
{
    /**
     * 查询好友
     * 
     * @param id 好友主键
     * @return 好友
     */
    public SocFriend selectSocFriendById(Long id);

    /**
     * 查询好友列表
     * 
     * @param socFriend 好友
     * @return 好友集合
     */
    public List<SocFriend> selectSocFriendList(SocFriend socFriend);

    /**
     * 新增好友
     * 
     * @param socFriend 好友
     * @return 结果
     */
    public int insertSocFriend(SocFriend socFriend);

    /**
     * 修改好友
     * 
     * @param socFriend 好友
     * @return 结果
     */
    public int updateSocFriend(SocFriend socFriend);

    /**
     * 批量删除好友
     * 
     * @param ids 需要删除的好友主键集合
     * @return 结果
     */
    public int deleteSocFriendByIds(Long[] ids);

    /**
     * 删除好友信息
     * 
     * @param id 好友主键
     * @return 结果
     */
    public int deleteSocFriendById(Long id);
}
