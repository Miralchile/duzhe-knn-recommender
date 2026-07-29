package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocComment;
import com.duzheStudy.modules.social.mapper.SocCommentMapper;
import com.duzheStudy.modules.social.service.ISocCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论Service业务层处理
 * 
 * @author duzhe
 * @date 2023-10-16
 */
@Service
public class SocCommentServiceImpl implements ISocCommentService
{
    @Autowired
    private SocCommentMapper socCommentMapper;

    /**
     * 查询评论
     * 
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public SocComment selectSocCommentById(Long id)
    {
        return socCommentMapper.selectSocCommentById(id);
    }

    /**
     * 查询评论列表
     * 
     * @param socComment 评论
     * @return 评论
     */
    @Override
    public List<SocComment> selectSocCommentList(SocComment socComment)
    {
        return socCommentMapper.selectSocCommentList(socComment);
    }

    /**
     * 新增评论
     * 
     * @param socComment 评论
     * @return 结果
     */
    @Override
    public int insertSocComment(SocComment socComment)
    {
        socComment.setCreateTime(DateUtils.getNowDate());
        return socCommentMapper.insertSocComment(socComment);
    }

    /**
     * 修改评论
     * 
     * @param socComment 评论
     * @return 结果
     */
    @Override
    public int updateSocComment(SocComment socComment)
    {
        socComment.setUpdateTime(DateUtils.getNowDate());
        return socCommentMapper.updateSocComment(socComment);
    }

    /**
     * 批量删除评论
     * 
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteSocCommentByIds(Long[] ids)
    {
        return socCommentMapper.deleteSocCommentByIds(ids);
    }

    /**
     * 删除评论信息
     * 
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteSocCommentById(Long id)
    {
        return socCommentMapper.deleteSocCommentById(id);
    }
}
