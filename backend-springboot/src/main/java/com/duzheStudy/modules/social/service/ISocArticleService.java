package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocArticle;

import java.util.List;

/**
 * 动态Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocArticleService 
{
    /**
     * 查询动态
     * 
     * @param id 动态主键
     * @return 动态
     */
    public SocArticle selectSocArticleById(Long id);

    /**
     * 查询动态列表
     * 
     * @param socArticle 动态
     * @return 动态集合
     */
    public List<SocArticle> selectSocArticleList(SocArticle socArticle);

    /**
     * 新增动态
     * 
     * @param socArticle 动态
     * @return 结果
     */
    public int insertSocArticle(SocArticle socArticle);

    /**
     * 修改动态
     * 
     * @param socArticle 动态
     * @return 结果
     */
    public int updateSocArticle(SocArticle socArticle);

    /**
     * 批量删除动态
     * 
     * @param ids 需要删除的动态主键集合
     * @return 结果
     */
    public int deleteSocArticleByIds(Long[] ids);

    /**
     * 删除动态信息
     * 
     * @param id 动态主键
     * @return 结果
     */
    public int deleteSocArticleById(Long id);
}
