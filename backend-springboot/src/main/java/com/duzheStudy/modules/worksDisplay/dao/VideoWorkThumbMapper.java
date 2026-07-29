package com.duzheStudy.modules.worksDisplay.dao;

import com.duzheStudy.modules.worksDisplay.domain.Thumb;
import com.duzheStudy.modules.worksDisplay.domain.ThumbList;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class VideoWorkThumbMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void thumbWorkById(String id, Thumb thumb){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update;
        if(this.checkIsThumb(id,thumb.getThumber())){
            update = new Update().pull("thumbList",Query.query(Criteria.where("thumber").is(thumb.getThumber())));
        }
        else{
            update = new Update().push("thumbList",thumb);
        }
        this.mongoTemplate.updateFirst(query,update, VideoWork.class,"works");
    }

    public Integer getThumbNumById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().include("thumbList");
        return this.mongoTemplate.findOne(query, ThumbList.class,"works").getThumbList().size();
    }

    public boolean checkIsThumb(String id, String userName){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteriaById = Criteria.where("_id").is(objectId);
        Criteria criteriaByThumberList = Criteria.where("thumbList").elemMatch(Criteria.where("thumber").is(userName));
        query.addCriteria(criteriaById);
        query.addCriteria(criteriaByThumberList);
        if(this.mongoTemplate.findOne(query, VideoWork.class,"works")!=null){
            return true;
        }
        else {
            return false;
        }
    }

}
