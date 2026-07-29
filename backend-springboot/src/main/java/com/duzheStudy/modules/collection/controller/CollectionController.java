package com.duzheStudy.modules.collection.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.collection.domain.Collection;
import com.duzheStudy.modules.collection.service.CollectionService;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/getCollectionListByFolderId")
    public List<Work> getCollectionListByFolderId(Long folderId){
        return this.collectionService.selectByFolderId(folderId);
    }

    @GetMapping("/getCollectionNumByWorkId")
    public int getCollectionNumByWorkId(String workId){
        return this.collectionService.selectColletionNumByWorkId(workId);
    }

    @GetMapping("/deleteCollectionByUserIdAndWorkId")
    public void deleteCollectionByUserIdAndWorkId(String workId){
        User currentUser = UserUtils.getLoginUser();
        this.collectionService.deleteByUserIdAndWorkId(workId, currentUser.getId());
    }

    @PostMapping("/addColletionToFolder")
    public void addColletionToFolder(@RequestBody Collection collection){
        User currentUser = UserUtils.getLoginUser();
        collection.setUserId(currentUser.getId());
        this.collectionService.insert(collection);
    }

    @GetMapping("/checkIsCollection")
    public boolean checkIsCollection(String workId){
        User currentUser = UserUtils.getLoginUser();
        if(this.collectionService.selectByUserIdAndWorkId(workId, currentUser.getId()) == null){
            return false;
        }else {
            return true;
        }
    }
}
