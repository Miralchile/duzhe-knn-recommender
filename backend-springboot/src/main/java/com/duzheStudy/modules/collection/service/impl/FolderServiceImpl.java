package com.duzheStudy.modules.collection.service.impl;

import com.duzheStudy.modules.collection.domain.Folder;
import com.duzheStudy.modules.collection.mapper.CollectionMapper;
import com.duzheStudy.modules.collection.mapper.FolderMapper;
import com.duzheStudy.modules.collection.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private CollectionMapper collectionMapper;

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.folderMapper.deleteById(id);
        this.collectionMapper.deleteByFolderId(id);
    }

    @Override
    public void insert(Folder folder) {
        this.folderMapper.insert(folder);
    }

    @Override
    public Folder selectById(Long id) {
        return this.folderMapper.selectById(id);
    }

    @Override
    public void updateById(Folder folder) {
        this.folderMapper.updateById(folder);
    }

    @Override
    public List<Folder> selectByUserId(Long userId) {
        return this.folderMapper.selectByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> selectByUserIdWithWorkNum(Long userId) {
        return this.folderMapper.selectByUserIdWithWorkNum(userId);
    }
}
