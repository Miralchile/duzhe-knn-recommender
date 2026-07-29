package com.duzheStudy.modules.collection.mapper;

import com.duzheStudy.modules.collection.domain.Collection;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionMapper {

    int deleteByUserIdAndWorkId(@Param("workId")String workId, @Param("userId")Long userId);

    Collection selectByUserIdAndWorkId(@Param("workId")String workId, @Param("userId")Long userId);

    int insert(Collection collection);

    Collection selectById(Long id);

    int updateById(Collection collection);

    List<Collection> selectByFolderId(Long folderId);

    int deleteByFolderId(Long folderId);

    int selectColletionNumByWorkId(String workId);

}