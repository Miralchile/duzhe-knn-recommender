package com.duzheStudy.modules.collection.mapper;

import com.duzheStudy.modules.collection.domain.Folder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FolderMapper {
    int deleteById(Long id);

    int insert(Folder folder);

    Folder selectById(Long id);

    List<Folder> selectByUserId(Long userId);

    int updateById(Folder folder);

    List<Map<String,Object>> selectByUserIdWithWorkNum(Long userId);
}