package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocArticleLike;

import java.util.List;

/**
 * 动态点赞Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocArticleLikeService 
{
    /**
     * 查询动态点赞
     * 
     * @param id 动态点赞主键
     * @return 动态点赞
     */
    public SocArticleLike selectSocArticleLikeById(Long id);

    /**
     * 查询动态点赞列表
     * 
     * @param socArticleLike 动态点赞
     * @return 动态点赞集合
     */
    public List<SocArticleLike> selectSocArticleLikeList(SocArticleLike socArticleLike);

    /**
     * 新增动态点赞
     * 
     * @param socArticleLike 动态点赞
     * @return 结果
     */
    public int insertSocArticleLike(SocArticleLike socArticleLike);

    /**
     * 修改动态点赞
     * 
     * @param socArticleLike 动态点赞
     * @return 结果
     */
    public int updateSocArticleLike(SocArticleLike socArticleLike);

    /**
     * 批量删除动态点赞
     * 
     * @param ids 需要删除的动态点赞主键集合
     * @return 结果
     */
    public int deleteSocArticleLikeByIds(Long[] ids);

    /**
     * 删除动态点赞信息
     * 
     * @param id 动态点赞主键
     * @return 结果
     */
    public int deleteSocArticleLikeById(Long id);
}
