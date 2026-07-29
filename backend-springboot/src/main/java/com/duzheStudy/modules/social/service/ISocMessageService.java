package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocMessage;

import java.util.List;

/**
 * 好友信息Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocMessageService 
{
    /**
     * 查询好友信息
     * 
     * @param id 好友信息主键
     * @return 好友信息
     */
    public SocMessage selectSocMessageById(Long id);

    /**
     * 查询好友信息列表
     * 
     * @param socMessage 好友信息
     * @return 好友信息集合
     */
    public List<SocMessage> selectSocMessageList(SocMessage socMessage);

    /**
     * 新增好友信息
     * 
     * @param socMessage 好友信息
     * @return 结果
     */
    public int insertSocMessage(SocMessage socMessage);

    /**
     * 修改好友信息
     * 
     * @param socMessage 好友信息
     * @return 结果
     */
    public int updateSocMessage(SocMessage socMessage);

    /**
     * 批量删除好友信息
     * 
     * @param ids 需要删除的好友信息主键集合
     * @return 结果
     */
    public int deleteSocMessageByIds(Long[] ids);

    /**
     * 删除好友信息信息
     * 
     * @param id 好友信息主键
     * @return 结果
     */
    public int deleteSocMessageById(Long id);
}
