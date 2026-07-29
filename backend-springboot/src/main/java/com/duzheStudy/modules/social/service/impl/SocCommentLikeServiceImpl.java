package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocCommentLike;
import com.duzheStudy.modules.social.mapper.SocCommentLikeMapper;
import com.duzheStudy.modules.social.service.ISocCommentLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论点赞Service业务层处理
 * 
 * @author duzhe
 * @date 2023-10-16
 */
@Service
public class SocCommentLikeServiceImpl implements ISocCommentLikeService
{
    @Autowired
    private SocCommentLikeMapper socCommentLikeMapper;

    /**
     * 查询评论点赞
     * 
     * @param id 评论点赞主键
     * @return 评论点赞
     */
    @Override
    public SocCommentLike selectSocCommentLikeById(Long id)
    {
        return socCommentLikeMapper.selectSocCommentLikeById(id);
    }

    /**
     * 查询评论点赞列表
     * 
     * @param socCommentLike 评论点赞
     * @return 评论点赞
     */
    @Override
    public List<SocCommentLike> selectSocCommentLikeList(SocCommentLike socCommentLike)
    {
        return socCommentLikeMapper.selectSocCommentLikeList(socCommentLike);
    }

    /**
     * 新增评论点赞
     * 
     * @param socCommentLike 评论点赞
     * @return 结果
     */
    @Override
    public int insertSocCommentLike(SocCommentLike socCommentLike)
    {
        socCommentLike.setCreateTime(DateUtils.getNowDate());
        return socCommentLikeMapper.insertSocCommentLike(socCommentLike);
    }

    /**
     * 修改评论点赞
     * 
     * @param socCommentLike 评论点赞
     * @return 结果
     */
    @Override
    public int updateSocCommentLike(SocCommentLike socCommentLike)
    {
        socCommentLike.setUpdateTime(DateUtils.getNowDate());
        return socCommentLikeMapper.updateSocCommentLike(socCommentLike);
    }

    /**
     * 批量删除评论点赞
     * 
     * @param ids 需要删除的评论点赞主键
     * @return 结果
     */
    @Override
    public int deleteSocCommentLikeByIds(Long[] ids)
    {
        return socCommentLikeMapper.deleteSocCommentLikeByIds(ids);
    }

    /**
     * 删除评论点赞信息
     * 
     * @param id 评论点赞主键
     * @return 结果
     */
    @Override
    public int deleteSocCommentLikeById(Long id)
    {
        return socCommentLikeMapper.deleteSocCommentLikeById(id);
    }
}
