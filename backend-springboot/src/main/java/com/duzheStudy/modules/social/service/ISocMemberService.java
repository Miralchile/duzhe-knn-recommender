package com.duzheStudy.modules.social.service;

import com.duzheStudy.modules.social.domain.SocMember;

import java.util.List;

/**
 * 圈内成员Service接口
 * 
 * @author duzhe
 * @date 2022-11-21
 */
public interface ISocMemberService 
{
    /**
     * 查询圈内成员
     * 
     * @param id 圈内成员主键
     * @return 圈内成员
     */
    public SocMember selectSocMemberById(Long id);

    /**
     * 查询圈内成员列表
     * 
     * @param socMember 圈内成员
     * @return 圈内成员集合
     */
    public List<SocMember> selectSocMemberList(SocMember socMember);

    /**
     * 新增圈内成员
     * 
     * @param socMember 圈内成员
     * @return 结果
     */
    public int insertSocMember(SocMember socMember);

    /**
     * 修改圈内成员
     * 
     * @param socMember 圈内成员
     * @return 结果
     */
    public int updateSocMember(SocMember socMember);

    /**
     * 批量删除圈内成员
     * 
     * @param ids 需要删除的圈内成员主键集合
     * @return 结果
     */
    public int deleteSocMemberByIds(Long[] ids);

    /**
     * 删除圈内成员信息
     * 
     * @param id 圈内成员主键
     * @return 结果
     */
    public int deleteSocMemberById(Long id);
}
