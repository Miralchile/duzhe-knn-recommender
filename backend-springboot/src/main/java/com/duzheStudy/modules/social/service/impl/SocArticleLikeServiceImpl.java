package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocArticleLike;
import com.duzheStudy.modules.social.mapper.SocArticleLikeMapper;
import com.duzheStudy.modules.social.service.ISocArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态点赞Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocArticleLikeServiceImpl implements ISocArticleLikeService
{
    @Autowired
    private SocArticleLikeMapper socArticleLikeMapper;

    /**
     * 查询动态点赞
     * 
     * @param id 动态点赞主键
     * @return 动态点赞
     */
    @Override
    public SocArticleLike selectSocArticleLikeById(Long id)
    {
        return socArticleLikeMapper.selectSocArticleLikeById(id);
    }

    /**
     * 查询动态点赞列表
     * 
     * @param socArticleLike 动态点赞
     * @return 动态点赞
     */
    @Override
    public List<SocArticleLike> selectSocArticleLikeList(SocArticleLike socArticleLike)
    {
        return socArticleLikeMapper.selectSocArticleLikeList(socArticleLike);
    }

    /**
     * 新增动态点赞
     * 
     * @param socArticleLike 动态点赞
     * @return 结果
     */
    @Override
    public int insertSocArticleLike(SocArticleLike socArticleLike)
    {
        socArticleLike.setCreateTime(DateUtils.getNowDate());
        return socArticleLikeMapper.insertSocArticleLike(socArticleLike);
    }

    /**
     * 修改动态点赞
     * 
     * @param socArticleLike 动态点赞
     * @return 结果
     */
    @Override
    public int updateSocArticleLike(SocArticleLike socArticleLike)
    {
        socArticleLike.setUpdateTime(DateUtils.getNowDate());
        return socArticleLikeMapper.updateSocArticleLike(socArticleLike);
    }

    /**
     * 批量删除动态点赞
     * 
     * @param ids 需要删除的动态点赞主键
     * @return 结果
     */
    @Override
    public int deleteSocArticleLikeByIds(Long[] ids)
    {
        return socArticleLikeMapper.deleteSocArticleLikeByIds(ids);
    }

    /**
     * 删除动态点赞信息
     * 
     * @param id 动态点赞主键
     * @return 结果
     */
    @Override
    public int deleteSocArticleLikeById(Long id)
    {
        return socArticleLikeMapper.deleteSocArticleLikeById(id);
    }
}
