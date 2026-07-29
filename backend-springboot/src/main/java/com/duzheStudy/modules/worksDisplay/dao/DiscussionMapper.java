package com.duzheStudy.modules.worksDisplay.dao;

import com.duzheStudy.modules.worksDisplay.domain.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class DiscussionMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Integer PAGE_SIZE = 3;

    public List<Discussion> getDiscussionRecommendList(Integer pageNo){
        Query query = new Query();
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<Discussion> discussList = this.mongoTemplate.find(query, Discussion.class, "discussion");
        int sumPage = 0;
        if(discussList.size() % PAGE_SIZE == 0){
            sumPage = discussList.size() / PAGE_SIZE;
        } else{
            sumPage = discussList.size() / PAGE_SIZE + 1;
        }
        if (sumPage < pageNo) {
            if (pageNo % sumPage == 0) {
                pageNo = pageNo % sumPage + sumPage;
            } else {
                pageNo = pageNo % sumPage;
            }
        }
        List<Discussion> discussListForPage = discussList.subList((pageNo - 1) * PAGE_SIZE,
                (pageNo - 1) * PAGE_SIZE + PAGE_SIZE < discussList.size() ? (pageNo - 1) * PAGE_SIZE + PAGE_SIZE : discussList.size());

        return discussListForPage;
    }

    public List<Discussion> getDiscussionByChannel(Integer pageNo, String channel){
        Query query = new Query();

        Criteria criteria = Criteria.where("channel").is(channel);
        query.addCriteria(criteria);
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<Discussion> discussList = this.mongoTemplate.find(query, Discussion.class, "discussion");
        int sumPage = 0;
        if(discussList.size() % PAGE_SIZE == 0){
            sumPage = discussList.size() / PAGE_SIZE;
        } else{
            sumPage = discussList.size() / PAGE_SIZE + 1;
        }
        if (sumPage < pageNo) {
            if (pageNo % sumPage == 0) {
                pageNo = pageNo % sumPage + sumPage;
            } else {
                pageNo = pageNo % sumPage;
            }
        }
        List<Discussion> discussListForPage = discussList.subList((pageNo - 1) * PAGE_SIZE,
                (pageNo - 1) * PAGE_SIZE + PAGE_SIZE < discussList.size() ? (pageNo - 1) * PAGE_SIZE + PAGE_SIZE : discussList.size());

        return discussListForPage;
    }

    public void addDiscussion(Discussion discussion) throws IOException {
        this.mongoTemplate.save(discussion, "discussion");
    }

    public void addAnswerById(String id, Answer answer){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update = new Update().push("answerList",answer);
        this.mongoTemplate.updateFirst(query, update, AnswerList.class,"discussion");
    }

    public AnswerList getAnswerById(String id) {
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().include("answerList");
        return this.mongoTemplate.findOne(query, AnswerList.class, "discussion");
    }

    public BestAnswer getBestAnswerById(String id) {
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().include("bestAnswer");
        return this.mongoTemplate.findOne(query, BestAnswer.class, "discussion");
    }

    public void cancelBestAnswerById(String id) {
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update = new Update().set("bestAnswer",null);
        this.mongoTemplate.updateFirst(query, update, BestAnswer.class,"discussion");
    }

    public Discussion getDiscussionById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        return this.mongoTemplate.findOne(query, Discussion.class, "discussion");
    }

    public void addDiscussionBestAnswer(Answer answer,String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Update update = new Update().set("bestAnswer",answer);
        this.mongoTemplate.updateFirst(query,update,"discussion");
    }

    public boolean checkIsQuestioner(String id,long userId){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        Discussion discussion = this.mongoTemplate.findOne(query, Discussion.class, "discussion");
        if(discussion.getAuthor() == userId){
            return true;
        } else{
            return false;
        }
    }
}
