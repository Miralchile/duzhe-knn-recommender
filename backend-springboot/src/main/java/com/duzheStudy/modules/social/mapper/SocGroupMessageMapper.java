package com.duzheStudy.modules.social.mapper;

import com.duzheStudy.modules.social.domain.SocGroupMessage;

import java.util.List;

/**
 * 圈信息Mapper接口
 *
 * @author duzhe
 * @date 2022-11-21
 */
public interface SocGroupMessageMapper
{
    /**
     * 查询圈信息
     *
     * @param id 圈信息主键
     * @return 圈信息
     */
    public SocGroupMessage selectSocGroupMessageById(Long id);

    /**
     * 查询圈信息列表
     *
     * @param socGroupMessage 圈信息
     * @return 圈信息集合
     */
    public List<SocGroupMessage> selectSocGroupMessageList(SocGroupMessage socGroupMessage);

    /**
     * 新增圈信息
     *
     * @param socGroupMessage 圈信息
     * @return 结果
     */
    public int insertSocGroupMessage(SocGroupMessage socGroupMessage);

    /**
     * 修改圈信息
     *
     * @param socGroupMessage 圈信息
     * @return 结果
     */
    public int updateSocGroupMessage(SocGroupMessage socGroupMessage);

    /**
     * 删除圈信息
     *
     * @param id 圈信息主键
     * @return 结果
     */
    public int deleteSocGroupMessageById(Long id);

    /**
     * 批量删除圈信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSocGroupMessageByIds(Long[] ids);
}
