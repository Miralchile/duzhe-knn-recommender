package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocArticle;
import com.duzheStudy.modules.social.mapper.SocArticleMapper;
import com.duzheStudy.modules.social.service.ISocArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocArticleServiceImpl implements ISocArticleService
{
    @Autowired
    private SocArticleMapper socArticleMapper;

    /**
     * 查询动态
     * 
     * @param id 动态主键
     * @return 动态
     */
    @Override
    public SocArticle selectSocArticleById(Long id)
    {
        return socArticleMapper.selectSocArticleById(id);
    }

    /**
     * 查询动态列表
     * 
     * @param socArticle 动态
     * @return 动态
     */
    @Override
    public List<SocArticle> selectSocArticleList(SocArticle socArticle)
    {
        return socArticleMapper.selectSocArticleList(socArticle);
    }

    /**
     * 新增动态
     * 
     * @param socArticle 动态
     * @return 结果
     */
    @Override
    public int insertSocArticle(SocArticle socArticle)
    {
        socArticle.setCreateTime(DateUtils.getNowDate());
        return socArticleMapper.insertSocArticle(socArticle);
    }

    /**
     * 修改动态
     * 
     * @param socArticle 动态
     * @return 结果
     */
    @Override
    public int updateSocArticle(SocArticle socArticle)
    {
        socArticle.setUpdateTime(DateUtils.getNowDate());
        return socArticleMapper.updateSocArticle(socArticle);
    }

    /**
     * 批量删除动态
     * 
     * @param ids 需要删除的动态主键
     * @return 结果
     */
    @Override
    public int deleteSocArticleByIds(Long[] ids)
    {
        return socArticleMapper.deleteSocArticleByIds(ids);
    }

    /**
     * 删除动态信息
     * 
     * @param id 动态主键
     * @return 结果
     */
    @Override
    public int deleteSocArticleById(Long id)
    {
        return socArticleMapper.deleteSocArticleById(id);
    }
}
