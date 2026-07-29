package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocNotice;
import com.duzheStudy.modules.social.mapper.SocNoticeMapper;
import com.duzheStudy.modules.social.service.ISocNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通知Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocNoticeServiceImpl implements ISocNoticeService
{
    @Autowired
    private SocNoticeMapper socNoticeMapper;

    /**
     * 查询通知
     * 
     * @param id 通知主键
     * @return 通知
     */
    @Override
    public SocNotice selectSocNoticeById(Long id)
    {
        return socNoticeMapper.selectSocNoticeById(id);
    }

    /**
     * 查询通知列表
     * 
     * @param socNotice 通知
     * @return 通知
     */
    @Override
    public List<SocNotice> selectSocNoticeList(SocNotice socNotice)
    {
        return socNoticeMapper.selectSocNoticeList(socNotice);
    }

    /**
     * 新增通知
     * 
     * @param socNotice 通知
     * @return 结果
     */
    @Override
    public int insertSocNotice(SocNotice socNotice)
    {
        socNotice.setCreateTime(DateUtils.getNowDate());
        return socNoticeMapper.insertSocNotice(socNotice);
    }

    /**
     * 修改通知
     * 
     * @param socNotice 通知
     * @return 结果
     */
    @Override
    public int updateSocNotice(SocNotice socNotice)
    {
        socNotice.setUpdateTime(DateUtils.getNowDate());
        return socNoticeMapper.updateSocNotice(socNotice);
    }

    /**
     * 批量删除通知
     * 
     * @param ids 需要删除的通知主键
     * @return 结果
     */
    @Override
    public int deleteSocNoticeByIds(Long[] ids)
    {
        return socNoticeMapper.deleteSocNoticeByIds(ids);
    }

    /**
     * 删除通知信息
     * 
     * @param id 通知主键
     * @return 结果
     */
    @Override
    public int deleteSocNoticeById(Long id)
    {
        return socNoticeMapper.deleteSocNoticeById(id);
    }
}
