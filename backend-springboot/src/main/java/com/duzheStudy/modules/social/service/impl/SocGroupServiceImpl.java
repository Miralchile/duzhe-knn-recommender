package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocGroup;
import com.duzheStudy.modules.social.mapper.SocGroupMapper;
import com.duzheStudy.modules.social.service.ISocGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 读友圈Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocGroupServiceImpl implements ISocGroupService
{
    @Autowired
    private SocGroupMapper socGroupMapper;

    /**
     * 查询读友圈
     * 
     * @param id 读友圈主键
     * @return 读友圈
     */
    @Override
    public SocGroup selectSocGroupById(Long id)
    {
        return socGroupMapper.selectSocGroupById(id);
    }

    /**
     * 查询读友圈列表
     * 
     * @param socGroup 读友圈
     * @return 读友圈
     */
    @Override
    public List<SocGroup> selectSocGroupList(SocGroup socGroup)
    {
        return socGroupMapper.selectSocGroupList(socGroup);
    }

    /**
     * 新增读友圈
     * 
     * @param socGroup 读友圈
     * @return 结果
     */
    @Override
    public int insertSocGroup(SocGroup socGroup)
    {
        socGroup.setCreateTime(DateUtils.getNowDate());
        return socGroupMapper.insertSocGroup(socGroup);
    }

    /**
     * 修改读友圈
     * 
     * @param socGroup 读友圈
     * @return 结果
     */
    @Override
    public int updateSocGroup(SocGroup socGroup)
    {
        socGroup.setUpdateTime(DateUtils.getNowDate());
        return socGroupMapper.updateSocGroup(socGroup);
    }

    /**
     * 批量删除读友圈
     * 
     * @param ids 需要删除的读友圈主键
     * @return 结果
     */
    @Override
    public int deleteSocGroupByIds(Long[] ids)
    {
        return socGroupMapper.deleteSocGroupByIds(ids);
    }

    /**
     * 删除读友圈信息
     * 
     * @param id 读友圈主键
     * @return 结果
     */
    @Override
    public int deleteSocGroupById(Long id)
    {
        return socGroupMapper.deleteSocGroupById(id);
    }
}
