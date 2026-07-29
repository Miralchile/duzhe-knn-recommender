package com.duzheStudy.modules.worksDisplay.dao;

import com.duzheStudy.modules.worksDisplay.domain.Danmu;
import com.duzheStudy.modules.worksDisplay.domain.DanmuList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class VideoWorkDanmuMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public DanmuList getDanmuById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().include("danmuList");
        return this.mongoTemplate.findOne(query, DanmuList.class,"works");
    }

    public void addDanmuById(String id, Danmu danmu){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update = new Update().push("danmuList",danmu);
        this.mongoTemplate.updateFirst(query,update, DanmuList.class,"works");
    }
}
