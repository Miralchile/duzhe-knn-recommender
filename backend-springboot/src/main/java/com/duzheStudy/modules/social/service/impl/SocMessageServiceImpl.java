package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocMessage;
import com.duzheStudy.modules.social.mapper.SocMessageMapper;
import com.duzheStudy.modules.social.service.ISocMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 好友信息Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocMessageServiceImpl implements ISocMessageService
{
    @Autowired
    private SocMessageMapper socMessageMapper;

    /**
     * 查询好友信息
     * 
     * @param id 好友信息主键
     * @return 好友信息
     */
    @Override
    public SocMessage selectSocMessageById(Long id)
    {
        return socMessageMapper.selectSocMessageById(id);
    }

    /**
     * 查询好友信息列表
     * 
     * @param socMessage 好友信息
     * @return 好友信息
     */
    @Override
    public List<SocMessage> selectSocMessageList(SocMessage socMessage)
    {
        return socMessageMapper.selectSocMessageList(socMessage);
    }

    /**
     * 新增好友信息
     * 
     * @param socMessage 好友信息
     * @return 结果
     */
    @Override
    public int insertSocMessage(SocMessage socMessage)
    {
        socMessage.setCreateTime(DateUtils.getNowDate());
        return socMessageMapper.insertSocMessage(socMessage);
    }

    /**
     * 修改好友信息
     * 
     * @param socMessage 好友信息
     * @return 结果
     */
    @Override
    public int updateSocMessage(SocMessage socMessage)
    {
        socMessage.setUpdateTime(DateUtils.getNowDate());
        return socMessageMapper.updateSocMessage(socMessage);
    }

    /**
     * 批量删除好友信息
     * 
     * @param ids 需要删除的好友信息主键
     * @return 结果
     */
    @Override
    public int deleteSocMessageByIds(Long[] ids)
    {
        return socMessageMapper.deleteSocMessageByIds(ids);
    }

    /**
     * 删除好友信息信息
     * 
     * @param id 好友信息主键
     * @return 结果
     */
    @Override
    public int deleteSocMessageById(Long id)
    {
        return socMessageMapper.deleteSocMessageById(id);
    }
}
