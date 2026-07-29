package com.duzheStudy.modules.collection.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.collection.domain.Folder;
import com.duzheStudy.modules.collection.service.CollectionService;
import com.duzheStudy.modules.collection.service.FolderService;
import com.duzheStudy.modules.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FolderController {

    @Autowired
    private FolderService folderService;

    @Autowired
    private CollectionService collectionService;

    @GetMapping("/getFolderListByCurrentUser")
    public List<Map<String,Object>> getFolderListByCurrentUser(){
        User currentUser = UserUtils.getLoginUser();
        return this.folderService.selectByUserIdWithWorkNum(currentUser.getId());
    }

    @GetMapping("/deleteFolderById")
    public void deleteFolderById(Long id){
        this.folderService.deleteById(id);
    }

    @PostMapping("/addFolder")
    public void addFolder(@RequestBody Folder folder){
        User currentUser = UserUtils.getLoginUser();
        folder.setUserId(currentUser.getId());
        this.folderService.insert(folder);
    }

    @PostMapping("/updateFolder")
    public void updateFolder(@RequestBody Folder folder){
        User currentUser = UserUtils.getLoginUser();
        folder.setUserId(currentUser.getId());
        this.folderService.updateById(folder);
    }

    @PostMapping("/uploadFolderCoverImage")
    public Map<String,Object> uploadFolderCoverImage(@RequestPart("file") MultipartFile multipartFile){
        Map<String,Object> resultMap = new HashMap<>();
        if (!multipartFile.isEmpty()){
            try {
                //上传的文件需要保存的路径和文件名称，路径需要存在，否则报错
                multipartFile.transferTo(new File("/home/image/" + multipartFile.getOriginalFilename()));
                resultMap.put("imagePath","http://localhost/image/" + multipartFile.getOriginalFilename());
                resultMap.put("code",1);
                return resultMap;
            } catch (IllegalStateException | IOException e){
                e.printStackTrace();
                resultMap.put("code",0);
                return resultMap;
            }
        } else {
            resultMap.put("code",0);
            return resultMap;
        }
    }

}
