package com.duzheStudy.modules.worksDisplay.controller;

import com.duzheStudy.common.utils.FFmpegUtil;
import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.mapper.UserMapper;
import com.duzheStudy.modules.video.domain.MovieInfo;
import com.duzheStudy.modules.video.service.IMovieInfoService;
import com.duzheStudy.modules.video.service.impl.MovieInfoServiceImpl;
import com.duzheStudy.modules.worksDisplay.dao.VideoWorkMapper;
import com.duzheStudy.modules.worksDisplay.domain.*;
import com.duzheStudy.modules.worksDisplay.domain.vo.VideoVo;
import com.duzheStudy.modules.worksDisplay.domain.vo.VideoWorkVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
@RestController
public class VideoWorkController {
    private static final Logger log = LoggerFactory.getLogger(VideoWorkMapper.class);
    private static final int PAGE_SIZE = 8;
    @Autowired
    private VideoWorkMapper videoWorkMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IMovieInfoService movieInfoService;

    @GetMapping("/getRelation")
    public List<Work> getRelation(String category){
        return this.videoWorkMapper.getRelation(category);
    }

    @GetMapping("/getRecommendList")
    public List<Work> getRecommendList(Integer pageNo){
        return this.videoWorkMapper.getRecommendList(pageNo);
    }

    @GetMapping("/getRecommendListByCategory")
    public List<Work> getRecommendListByCategory(Integer pageNo,String category){
        return this.videoWorkMapper.getRecommendListByCategory(pageNo,category);
    }

    @GetMapping("/deleteWorkById")
    public void deleteWorkById(String id){
        this.videoWorkMapper.deleteWorkById(id);
    }

    @GetMapping("/getVideoWorkById")
    public VideoWorkVo getVideoWorkById(String id){
        VideoWork videoWork = this.videoWorkMapper.getVideoById(id);
        User author = this.userMapper.selectById(videoWork.getAuthor());
        VideoWorkVo videoWorkVo = new VideoWorkVo(videoWork);
        if(author == null){
            videoWorkVo.setAuthorName("用户已注销");
        } else {
            videoWorkVo.setAuthorName(author.getUserName());
            videoWorkVo.setAuthorAvatarUrl(author.getAvatarUrl());
        }
        return videoWorkVo;
    }

    @PostMapping("/addVideoWork")
    public void addVideoWork(@RequestBody VideoWork videoWork) throws Exception {
        User currentUser = UserUtils.getLoginUser();

        videoWork.setCreatedBy(System.currentTimeMillis());
        videoWork.setType(1);
        videoWork.setAuthor(currentUser.getId());
        videoWork.setViews((long) 0);
        videoWork.setWorkName("workName1");
        videoWork.setCommentList(new ArrayList<>());
        videoWork.setThumbList(new ArrayList<>());
        videoWork.setDanmuList(new ArrayList<>());
        videoWork.setAuditStatus(0);

        Long duration = FFmpegUtil.getVideoTime(videoWork.getUrl());
        videoWork.setDuration(duration);

        this.videoWorkMapper.addVideoWork(videoWork);
    }

    @PostMapping("/uploadVideo")
    public Map<String,Object> uploadImage(@RequestPart("file") MultipartFile multipartFile){
        Map<String,Object> resultMap = new HashMap<>();
        if (!multipartFile.isEmpty()){
            try {
                //上传的文件需要保存的路径和文件名称，路径需要存在，否则报错
                byte[] uploadBytes = multipartFile.getBytes();
                String md5Hex = DigestUtils.md5Hex(uploadBytes);
                String fileName = md5Hex + ".m3u8";
                String filePath = "/home/video/" + fileName;
                multipartFile.transferTo(new File(filePath));

                resultMap.put("videoMD5",md5Hex);
                resultMap.put("videoPath","http://localhost/video/" + fileName);
                resultMap.put("code",1);
                return resultMap;
            } catch (Exception e){
                e.printStackTrace();
                resultMap.put("code",0);
                return resultMap;
            }
        } else {
            resultMap.put("code",0);
            return resultMap;
        }
    }

    @GetMapping("/generateCoverImage")
    public Map<String,Object> generateCoverImage(String videoMD5) throws Exception {
        String coverImagePath = "/home/image/" + videoMD5 + ".jpg";
        String filePath = "/home/video/" + videoMD5 + ".m3u8";
        Map<String,Object> resultMap = new HashMap<>();
        FFmpegUtil.getCoverImagePath(coverImagePath,filePath);

        resultMap.put("coverImagePath","http://localhost/image/" + videoMD5 + ".jpg");
        resultMap.put("code",1);
        return resultMap;

    }

//    @GetMapping("/video/page")
//    public Page search(@RequestParam(required = true,defaultValue = "1")Integer pageNo) throws IOException {
//        return this.videoWorkMapper.page(pageNo);
//    }
// 新增 /video/page 接口
@GetMapping("/video/page")
public Page search(@RequestParam(required = true, defaultValue = "1") Integer pageNo) throws IOException {
    log.info("Request: pageNo={}", pageNo);
    List<VideoVo> result = new ArrayList<>();

    if (pageNo == 1) {
        String randomMovieId = videoWorkMapper.getRandomVideoId();
        log.info("Page 1: Random first video ID={}", randomMovieId);
        if (randomMovieId == null) {
            log.warn("No videos available in database, returning empty page");
            Page page = new Page(); // 使用无参构造
            page.setPage(pageNo);
            page.setTotal(0);
            page.setData(Collections.emptyList()); // data 是 Object，可以接受 List
            return page;
        }

        VideoVo firstVideo = videoWorkMapper.getVideoByIdAsVo(randomMovieId);
        if (firstVideo != null) {
            result.add(firstVideo);
        }

        ResponseDto responseDto = movieInfoService.getRecommendation(randomMovieId);
        List<MovieInfo> movieInfos = (List<MovieInfo>) responseDto.getData();
        List<VideoVo> recommendedVideos = videoWorkMapper.convertMovieInfosToVideoVos(movieInfos);
        result.addAll(recommendedVideos.subList(0, Math.min(7, recommendedVideos.size())));
    } else {
        Page prevPage = search(pageNo - 1);
        List<VideoVo> prevData = (List<VideoVo>) prevPage.getData();

        if (prevData.isEmpty()) {
            log.warn("Previous page (pageNo={}) is empty, returning empty page", pageNo - 1);
            Page page = new Page(); // 使用无参构造
            page.setPage(pageNo);
            page.setTotal(0);
            page.setData(Collections.emptyList()); // data 是 Object，可以接受 List
            return page;
        }

        String baseMovieId;
        if (pageNo == 2) {
            baseMovieId = prevData.get(prevData.size() - 1).get_id();
        } else {
            baseMovieId = prevData.size() >= 2
                    ? prevData.get(prevData.size() - 2).get_id()
                    : prevData.get(prevData.size() - 1).get_id();
        }
        log.info("Page {}: Base video ID for recommendation={}", pageNo, baseMovieId);

        ResponseDto responseDto = movieInfoService.getRecommendation(baseMovieId);
        List<MovieInfo> movieInfos = (List<MovieInfo>) responseDto.getData();
        List<VideoVo> recommendedVideos = videoWorkMapper.convertMovieInfosToVideoVos(movieInfos);
        result.addAll(recommendedVideos.subList(0, Math.min(7, recommendedVideos.size())));

        String randomMovieId = videoWorkMapper.getRandomVideoId();
        log.info("Page {}: Random last video ID={}", pageNo, randomMovieId);
        if (randomMovieId != null) {
            VideoVo randomVideo = videoWorkMapper.getVideoByIdAsVo(randomMovieId);
            if (randomVideo != null) {
                result.add(randomVideo);
            }
        }
    }

    Page page = new Page(); // 使用无参构造
    page.setPage(pageNo);
    page.setTotal(result.size());
    page.setData(result.size() > PAGE_SIZE ? result.subList(0, PAGE_SIZE) : result); // data 是 Object，直接赋值 List
    return page;
}
}
