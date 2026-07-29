package com.duzheStudy.modules.social.service.impl;

import com.duzheStudy.modules.common.utils.DateUtils;
import com.duzheStudy.modules.social.domain.SocMember;
import com.duzheStudy.modules.social.mapper.SocMemberMapper;
import com.duzheStudy.modules.social.service.ISocMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 圈内成员Service业务层处理
 * 
 * @author duzhe
 * @date 2022-11-21
 */
@Service
public class SocMemberServiceImpl implements ISocMemberService
{
    @Autowired
    private SocMemberMapper socMemberMapper;

    /**
     * 查询圈内成员
     * 
     * @param id 圈内成员主键
     * @return 圈内成员
     */
    @Override
    public SocMember selectSocMemberById(Long id)
    {
        return socMemberMapper.selectSocMemberById(id);
    }

    /**
     * 查询圈内成员列表
     * 
     * @param socMember 圈内成员
     * @return 圈内成员
     */
    @Override
    public List<SocMember> selectSocMemberList(SocMember socMember)
    {
        return socMemberMapper.selectSocMemberList(socMember);
    }

    /**
     * 新增圈内成员
     * 
     * @param socMember 圈内成员
     * @return 结果
     */
    @Override
    public int insertSocMember(SocMember socMember)
    {
        socMember.setCreateTime(DateUtils.getNowDate());
        return socMemberMapper.insertSocMember(socMember);
    }

    /**
     * 修改圈内成员
     * 
     * @param socMember 圈内成员
     * @return 结果
     */
    @Override
    public int updateSocMember(SocMember socMember)
    {
        socMember.setUpdateTime(DateUtils.getNowDate());
        return socMemberMapper.updateSocMember(socMember);
    }

    /**
     * 批量删除圈内成员
     * 
     * @param ids 需要删除的圈内成员主键
     * @return 结果
     */
    @Override
    public int deleteSocMemberByIds(Long[] ids)
    {
        return socMemberMapper.deleteSocMemberByIds(ids);
    }

    /**
     * 删除圈内成员信息
     * 
     * @param id 圈内成员主键
     * @return 结果
     */
    @Override
    public int deleteSocMemberById(Long id)
    {
        return socMemberMapper.deleteSocMemberById(id);
    }
}
