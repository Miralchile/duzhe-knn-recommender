package com.duzheStudy.modules.collection.service.impl;

import com.duzheStudy.modules.collection.domain.Collection;
import com.duzheStudy.modules.collection.mapper.CollectionMapper;
import com.duzheStudy.modules.collection.service.CollectionService;
import com.duzheStudy.modules.worksDisplay.dao.WorkMapper;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private CollectionMapper collectionMapper;

    @Autowired
    private WorkMapper workMapper;

    @Override
    public void deleteByUserIdAndWorkId(String workId, Long userId) {
        this.collectionMapper.deleteByUserIdAndWorkId(workId, userId);
    }

    @Override
    public Collection selectByUserIdAndWorkId(String workId, Long userId) {
        return this.collectionMapper.selectByUserIdAndWorkId(workId, userId);
    }

    @Override
    public void insert(Collection collection) {
        this.collectionMapper.insert(collection);
    }

    @Override
    public Collection selectById(Long id) {
        return this.collectionMapper.selectById(id);
    }

    @Override
    public void updateById(Collection collection) {
        this.collectionMapper.updateById(collection);
    }

    @Override
    public List<Work> selectByFolderId(Long folderId) {

        List<Collection> collectionList= this.collectionMapper.selectByFolderId(folderId);
        List<Work> collectionWorkList = new ArrayList<>();
        for(Collection collection: collectionList){
            Work work = this.workMapper.getWorkById(collection.getWorkId());
            if(work!=null){
                collectionWorkList.add(work);
            }
        }
        return collectionWorkList;

    }

    @Override
    public int selectColletionNumByWorkId(String workId) {
        return this.collectionMapper.selectColletionNumByWorkId(workId);
    }
}
