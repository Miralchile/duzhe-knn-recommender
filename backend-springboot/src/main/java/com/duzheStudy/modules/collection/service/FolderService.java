package com.duzheStudy.modules.collection.service;

import com.duzheStudy.modules.collection.domain.Folder;

import java.util.List;
import java.util.Map;

public interface FolderService {

    void deleteById(Long id);

    void insert(Folder folder);

    Folder selectById(Long id);

    void updateById(Folder folder);

    List<Folder> selectByUserId(Long userId);

    List<Map<String,Object>> selectByUserIdWithWorkNum(Long userId);

}
