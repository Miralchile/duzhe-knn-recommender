package com.duzheStudy.modules.others.dao;

import com.alibaba.fastjson.JSONObject;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OthersMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Work> getOthersWorkList(Integer pageNo,Integer pageSize ,Long userId) {
        List<Work> result = new ArrayList<>();
        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);
        Criteria criteriaUserId = Criteria.where("author").is(userId);
        query.addCriteria(criteriaUserId);
        query.with(Sort.by(Sort.Order.desc("_id")));
        query.skip((pageNo-1)*pageSize);
        query.limit(pageSize);

        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");

        for(JSONObject jsonObject : workList){
            if((int)jsonObject.get("type") == 1){
                result.add(jsonObject.toJavaObject(VideoWork.class));
            } else if((int)jsonObject.get("type") == 2){
                result.add(jsonObject.toJavaObject(ImageWork.class));
            }
        }

        return result;
    }

    public Integer getOthersWorkListTotal(Long userId) {
        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);
        Criteria criteriaUserId = Criteria.where("author").is(userId);
        query.addCriteria(criteriaUserId);

        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");

        return workList.size();
    }

}
