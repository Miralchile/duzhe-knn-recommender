package com.duzheStudy.modules.worksDisplay.dao;

import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class HotMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ImageWork> getHotImageWork(){
        Query query = new Query();
        Criteria criteria = Criteria.where("type").is(2);
        query.addCriteria(criteria);
        List<ImageWork> imageWorkList = this.mongoTemplate.find(query, ImageWork.class, "hot");
        List<ImageWork> result = imageWorkList.subList(imageWorkList.size()-4,imageWorkList.size());
        Collections.reverse(result);
        return result;
    }

    public List<VideoWork> getHotVideoWork(){
        Query query = new Query();
        Criteria criteria = Criteria.where("type").is(1);
        query.addCriteria(criteria);
        List<VideoWork> videoWorkList = this.mongoTemplate.find(query, VideoWork.class, "hot");
        List<VideoWork> result = videoWorkList.subList(videoWorkList.size()-4,videoWorkList.size());
        Collections.reverse(result);
        return result;
    }

}
