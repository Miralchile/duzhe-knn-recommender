package com.duzheStudy.modules.worksDisplay.dao;

import com.alibaba.fastjson.JSONObject;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class WorkMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Work getWorkById(String id) {
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().exclude("thumbList");
        query.fields().exclude("danmuList");
        query.fields().exclude("commentList");
        JSONObject work = this.mongoTemplate.findOne(query, JSONObject.class, "works");
        Work result = null;
        if(work!=null && (int)work.get("type") == 1){
            result = work.toJavaObject(VideoWork.class);
        } else if(work!=null && (int)work.get("type") == 2){
            result = work.toJavaObject(ImageWork.class);
        }
        return result;
    }
}
