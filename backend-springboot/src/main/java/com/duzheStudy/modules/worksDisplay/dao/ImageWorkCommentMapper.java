package com.duzheStudy.modules.worksDisplay.dao;

import com.duzheStudy.modules.worksDisplay.domain.Comment;
import com.duzheStudy.modules.worksDisplay.domain.CommentList;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class ImageWorkCommentMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public CommentList getCommentById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().include("commentList");
        return this.mongoTemplate.findOne(query, CommentList.class,"works");
    }

    public void addCommentById(String id,Comment comment){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update = new Update().push("commentList",comment);
        this.mongoTemplate.updateFirst(query,update, CommentList.class,"works");
    }

    public Integer getCommentNumById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, CommentList.class,"works").getCommentList().size();
    }
}
