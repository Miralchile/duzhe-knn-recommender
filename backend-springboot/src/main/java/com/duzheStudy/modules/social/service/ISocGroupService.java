package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocGroup;

import java.util.List;

/**
 * 读友圈Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocGroupService 
{
    /**
     * 查询读友圈
     * 
     * @param id 读友圈主键
     * @return 读友圈
     */
    public SocGroup selectSocGroupById(Long id);

    /**
     * 查询读友圈列表
     * 
     * @param socGroup 读友圈
     * @return 读友圈集合
     */
    public List<SocGroup> selectSocGroupList(SocGroup socGroup);

    /**
     * 新增读友圈
     * 
     * @param socGroup 读友圈
     * @return 结果
     */
    public int insertSocGroup(SocGroup socGroup);

    /**
     * 修改读友圈
     * 
     * @param socGroup 读友圈
     * @return 结果
     */
    public int updateSocGroup(SocGroup socGroup);

    /**
     * 批量删除读友圈
     * 
     * @param ids 需要删除的读友圈主键集合
     * @return 结果
     */
    public int deleteSocGroupByIds(Long[] ids);

    /**
     * 删除读友圈信息
     * 
     * @param id 读友圈主键
     * @return 结果
     */
    public int deleteSocGroupById(Long id);
}
