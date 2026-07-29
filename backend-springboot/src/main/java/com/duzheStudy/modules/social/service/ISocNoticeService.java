package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocNotice;

import java.util.List;

/**
 * 通知Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocNoticeService 
{
    /**
     * 查询通知
     * 
     * @param id 通知主键
     * @return 通知
     */
    public SocNotice selectSocNoticeById(Long id);

    /**
     * 查询通知列表
     * 
     * @param socNotice 通知
     * @return 通知集合
     */
    public List<SocNotice> selectSocNoticeList(SocNotice socNotice);

    /**
     * 新增通知
     * 
     * @param socNotice 通知
     * @return 结果
     */
    public int insertSocNotice(SocNotice socNotice);

    /**
     * 修改通知
     * 
     * @param socNotice 通知
     * @return 结果
     */
    public int updateSocNotice(SocNotice socNotice);

    /**
     * 批量删除通知
     * 
     * @param ids 需要删除的通知主键集合
     * @return 结果
     */
    public int deleteSocNoticeByIds(Long[] ids);

    /**
     * 删除通知信息
     * 
     * @param id 通知主键
     * @return 结果
     */
    public int deleteSocNoticeById(Long id);
}
