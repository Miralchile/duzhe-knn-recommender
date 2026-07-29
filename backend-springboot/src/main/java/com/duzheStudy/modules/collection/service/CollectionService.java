package com.duzheStudy.modules.collection.service;

import com.duzheStudy.modules.collection.domain.Collection;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CollectionService {

    void deleteByUserIdAndWorkId(String workId, Long userId);

    Collection selectByUserIdAndWorkId(String workId, Long userId);

    void insert(Collection collection);

    Collection selectById(Long id);

    void updateById(Collection collection);

    List<Work> selectByFolderId(Long folderId);

    int selectColletionNumByWorkId(String workId);

}
