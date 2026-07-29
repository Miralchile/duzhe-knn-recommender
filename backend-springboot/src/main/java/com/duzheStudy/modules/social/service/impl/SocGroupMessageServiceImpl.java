package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocGroupMessage;
import com.duzheStudy.modules.social.mapper.SocGroupMessageMapper;
import com.duzheStudy.modules.social.service.ISocGroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 圈信息Service业务层处理
 *
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocGroupMessageServiceImpl implements ISocGroupMessageService
{
    @Autowired
    private SocGroupMessageMapper socGroupMessageMapper;

    /**
     * 查询圈信息
     *
     * @param id 圈信息主键
     * @return 圈信息
     */
    @Override
    public SocGroupMessage selectSocGroupMessageById(Long id)
    {
        return socGroupMessageMapper.selectSocGroupMessageById(id);
    }

    /**
     * 查询圈信息列表
     *
     * @param socGroupMessage 圈信息
     * @return 圈信息
     */
    @Override
    public List<SocGroupMessage> selectSocGroupMessageList(SocGroupMessage socGroupMessage)
    {
        return socGroupMessageMapper.selectSocGroupMessageList(socGroupMessage);
    }

    /**
     * 新增圈信息
     *
     * @param socGroupMessage 圈信息
     * @return 结果
     */
    @Override
    public int insertSocGroupMessage(SocGroupMessage socGroupMessage)
    {
        socGroupMessage.setCreateTime(DateUtils.getNowDate());
        return socGroupMessageMapper.insertSocGroupMessage(socGroupMessage);
    }

    /**
     * 修改圈信息
     *
     * @param socGroupMessage 圈信息
     * @return 结果
     */
    @Override
    public int updateSocGroupMessage(SocGroupMessage socGroupMessage)
    {
        socGroupMessage.setUpdateTime(DateUtils.getNowDate());
        return socGroupMessageMapper.updateSocGroupMessage(socGroupMessage);
    }

    /**
     * 批量删除圈信息
     *
     * @param ids 需要删除的圈信息主键
     * @return 结果
     */
    @Override
    public int deleteSocGroupMessageByIds(Long[] ids)
    {
        return socGroupMessageMapper.deleteSocGroupMessageByIds(ids);
    }

    /**
     * 删除圈信息信息
     *
     * @param id 圈信息主键
     * @return 结果
     */
    @Override
    public int deleteSocGroupMessageById(Long id)
    {
        return socGroupMessageMapper.deleteSocGroupMessageById(id);
    }
}
