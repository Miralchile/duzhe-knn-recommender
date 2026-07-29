package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocCommentLike;

import java.util.List;

/**
 * 评论点赞Service接口
 * 
 * @author duzhe
 * @date 2023-10-16
 */
public interface ISocCommentLikeService 
{
    /**
     * 查询评论点赞
     * 
     * @param id 评论点赞主键
     * @return 评论点赞
     */
    public SocCommentLike selectSocCommentLikeById(Long id);

    /**
     * 查询评论点赞列表
     * 
     * @param socCommentLike 评论点赞
     * @return 评论点赞集合
     */
    public List<SocCommentLike> selectSocCommentLikeList(SocCommentLike socCommentLike);

    /**
     * 新增评论点赞
     * 
     * @param socCommentLike 评论点赞
     * @return 结果
     */
    public int insertSocCommentLike(SocCommentLike socCommentLike);

    /**
     * 修改评论点赞
     * 
     * @param socCommentLike 评论点赞
     * @return 结果
     */
    public int updateSocCommentLike(SocCommentLike socCommentLike);

    /**
     * 批量删除评论点赞
     * 
     * @param ids 需要删除的评论点赞主键集合
     * @return 结果
     */
    public int deleteSocCommentLikeByIds(Long[] ids);

    /**
     * 删除评论点赞信息
     * 
     * @param id 评论点赞主键
     * @return 结果
     */
    public int deleteSocCommentLikeById(Long id);
}
