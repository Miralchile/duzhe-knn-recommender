package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.mapper.UserMapper;
import com.duzheStudy.modules.worksDisplay.dao.ImageWorkMapper;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import com.duzheStudy.modules.worksDisplay.domain.vo.ImageWorkVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@RestController
public class ImageWorkController {

    @Autowired
    private ImageWorkMapper imageWorkMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/getImageWorkById")
    public ImageWorkVo getImageWorkById(String id) {
        ImageWork imageWork = this.imageWorkMapper.getImageById(id);
        User author = this.userMapper.selectById(imageWork.getAuthor());
        ImageWorkVo imageWorkVo = new ImageWorkVo(imageWork);
        if(author == null){
            imageWorkVo.setAuthorName("用户已注销");
        } else {
            imageWorkVo.setAuthorName(author.getUserName());
            imageWorkVo.setAuthorAvatarUrl(author.getAvatarUrl());
        }
        return imageWorkVo;
    }

    @PostMapping("/uploadImage")
    public Map<String,Object> uploadImage(@RequestPart("file") MultipartFile multipartFile){
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

    @PostMapping("/addImageWork")
    public void addImageWork(@RequestBody ImageWork imageWork) throws IOException {
        User currentUser = UserUtils.getLoginUser();

        imageWork.setCreatedBy(System.currentTimeMillis());
        imageWork.setType(2);
        imageWork.setAuthor(currentUser.getId());
        imageWork.setViews((long) 0);
        imageWork.setWorkName("workName1");
        imageWork.setCommentList(new ArrayList<>());
        imageWork.setThumbList(new ArrayList<>());
        imageWork.setAuditStatus(0);

        this.imageWorkMapper.addImageWork(imageWork);
    }
}
