package com.duzheStudy.modules.worksDisplay.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duzheStudy.common.utils.UserUtils;
import com.duzheStudy.modules.others.service.FollowService;
import com.duzheStudy.modules.user.domain.User;
import com.duzheStudy.modules.user.service.UserService;
import com.duzheStudy.modules.video.domain.MovieInfo;
import com.duzheStudy.modules.worksDisplay.domain.*;
import com.duzheStudy.modules.worksDisplay.domain.vo.VideoVo;
import org.bson.types.ObjectId;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.*;

@Repository
public class VideoWorkMapper {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private VideoWorkThumbMapper videoWorkThumbMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Integer PAGE_SIZE = 8;

    private static final Logger log = LoggerFactory.getLogger(VideoWorkMapper.class);

    public List<Work> getRecommendList(Integer pageNo) {
        List<Work> result = new ArrayList<>();

        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");

        Set<Integer> set = new HashSet<>();
        List<JSONObject> workListForPage = new ArrayList<>();
        while (set.size() < 8) {
            int randomIndex = (int) (Math.random() * workList.size());
            if(set.add(randomIndex)){
                JSONObject randomElement = workList.get(randomIndex);
                workListForPage.add(randomElement);
            }
        }

        for(JSONObject jsonObject : workListForPage){
            if((int)jsonObject.get("type") == 1){
                result.add(jsonObject.toJavaObject(VideoWork.class));
            } else if((int)jsonObject.get("type") == 2){
                result.add(jsonObject.toJavaObject(ImageWork.class));
            }
        }
        return result;
    }

    // 修改：返回 VideoWork 的方法保持不变
    public VideoWork getVideoById(String id) {
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().exclude("thumbList");
        query.fields().exclude("danmuList");
        query.fields().exclude("commentList");

        VideoWork videoWork = this.mongoTemplate.findOne(query, VideoWork.class, "works");
        Update update = new Update().set("views", videoWork.getViews()+1);
        this.mongoTemplate.updateFirst(query,update,"works");
        return videoWork;
    }

    // 新增：根据 ID 获取 VideoVo
    public VideoVo getVideoByIdAsVo(String videoId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(new ObjectId(videoId)));
        VideoWork videoWork = mongoTemplate.findOne(query, VideoWork.class, "works");

        if (videoWork == null) {
            return null;
        }

        VideoVo videoVo = new VideoVo();
        videoVo.set_id(videoWork.get_id());
        videoVo.setCategory(videoWork.getCategory());
        videoVo.setChannel(videoWork.getChannel());
        videoVo.setUid(videoWork.getAuthor());
        videoVo.setTitle(videoWork.getTitle());
        videoVo.setUrl(videoWork.getUrl());

        User user = userService.selectById(videoWork.getAuthor());
        if (user != null) {
            videoVo.setAvatarUrl(user.getAvatarUrl());
            videoVo.setUsername(user.getUserName());
        }

        User currentUser = UserUtils.getLoginUser();
        if (currentUser != null) {
            videoVo.setFollow(followService.isFollowThisUser(currentUser.getId(), videoWork.getAuthor()));
            videoVo.setThumb(videoWorkThumbMapper.checkIsThumb(videoWork.get_id(), currentUser.getUserName()));
        }

        return videoVo;
    }

    public List<Work> getRelation(String category){
        List<Work> result = new ArrayList<>();

        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        Criteria criteriaCategory = Criteria.where("category").is(category);
        query.addCriteria(criteriaAudit);
        query.addCriteria(criteriaCategory);
        query.with(Sort.by(Sort.Order.desc("_id")));
        query.limit(6);
        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");
        Collections.shuffle(workList);
        for(JSONObject jsonObject : workList){
            if((int)jsonObject.get("type") == 1){
                result.add(jsonObject.toJavaObject(VideoWork.class));
            } else if((int)jsonObject.get("type") == 2){
                result.add(jsonObject.toJavaObject(ImageWork.class));
            }
        }
        return result;
    }

    public void addVideoWork(VideoWork videoWork) throws IOException {
        VideoWork videoWorkSaveResult = this.mongoTemplate.save(videoWork, "works");
        videoWorkSaveResult.setWorkName(videoWorkSaveResult.get_id());

//        IndexRequest request = new IndexRequest("works");
//        request.id("1");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//        request.source(JSON.toJSONString(videoWorkSaveResult), XContentType.JSON);
//        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public void deleteWorkById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        this.mongoTemplate.remove(query,"works");
    }

    public List<Work> getRecommendListByCategory(Integer pageNo,String category) {
        List<Work> result = new ArrayList<>();

        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);
        query.addCriteria(Criteria.where("category").is(category));
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");
        int sumPage = 0;
        if(workList.size() % PAGE_SIZE == 0){
            sumPage = workList.size() / PAGE_SIZE;
        } else{
            sumPage = workList.size() / PAGE_SIZE + 1;
        }
        if (sumPage < pageNo) {
            if (pageNo % sumPage == 0) {
                pageNo = pageNo % sumPage + sumPage;
            } else {
                pageNo = pageNo % sumPage;
            }
        }
        List<JSONObject> workListForPage = workList.subList((pageNo - 1) * PAGE_SIZE,
                (pageNo - 1) * PAGE_SIZE + PAGE_SIZE < workList.size() ? (pageNo - 1) * PAGE_SIZE + PAGE_SIZE : workList.size());
        for(JSONObject jsonObject : workListForPage){
            if((int)jsonObject.get("type") == 1){
                result.add(jsonObject.toJavaObject(VideoWork.class));
            } else if((int)jsonObject.get("type") == 2){
                result.add(jsonObject.toJavaObject(ImageWork.class));
            }
        }
        return result;
    }

    // 新增：将 MovieInfo 转换为 VideoVo 列表
    public List<VideoVo> convertMovieInfosToVideoVos(List<MovieInfo> movieInfos) {
        List<VideoVo> result = new ArrayList<>();
        for (MovieInfo movieInfo : movieInfos) {
            VideoVo videoVo = getVideoByIdAsVo(movieInfo.getId());
            if (videoVo != null) {
                result.add(videoVo);
            }
        }
        return result;
    }

    // 已存在：获取随机视频 ID
    public String getRandomVideoId() {
        Query query = new Query();
        query.addCriteria(Criteria.where("auditStatus").is(0));
        query.addCriteria(Criteria.where("type").is(1));
        List<VideoWork> videoWorks = mongoTemplate.find(query, VideoWork.class, "works");

        if (videoWorks.isEmpty()) {
            return null;
        }

        int randomIndex = (int) (Math.random() * videoWorks.size());
        return videoWorks.get(randomIndex).get_id();
    }
}