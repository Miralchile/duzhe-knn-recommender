package com.duzheStudy.modules.social.mapper;

import com.duzheStudy.modules.social.domain.SocComment;

import java.util.List;

/**
 * 评论Mapper接口
 * 
 * @author duzhe
 * @date 2023-10-16
 */
public interface SocCommentMapper 
{
    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    public SocComment selectSocCommentById(Long id);

    /**
     * 查询评论列表
     * 
     * @param socComment 评论
     * @return 评论集合
     */
    public List<SocComment> selectSocCommentList(SocComment socComment);

    /**
     * 新增评论
     * 
     * @param socComment 评论
     * @return 结果
     */
    public int insertSocComment(SocComment socComment);

    /**
     * 修改评论
     * 
     * @param socComment 评论
     * @return 结果
     */
    public int updateSocComment(SocComment socComment);

    /**
     * 删除评论
     * 
     * @param id 评论主键
     * @return 结果
     */
    public int deleteSocCommentById(Long id);

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSocCommentByIds(Long[] ids);
}
