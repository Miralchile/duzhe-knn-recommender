package com.duzheStudy.modules.worksDisplay.dao;

import com.alibaba.fastjson.JSONObject;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.Page;
import com.duzheStudy.modules.worksDisplay.domain.VideoWork;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WorkSearchMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static Integer PAGE_SIZE = 8;


    /**
     * 主页搜索
     * @param keywords 关键词
     * @param pageNo 当前页
     * @return
     * @throws IOException
     */
    public Page search(String keywords, Integer pageNo,Integer type) throws IOException {
        List<Work> result = new ArrayList<>();
        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);
        if(type!=null&&type!=0){
            query.addCriteria(Criteria.where("type").is(type));
        }
        query.addCriteria(Criteria.where("title").regex(".*?\\" +keywords+ ".*"));
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");
        Page page = new Page();
        page.setPage(pageNo);
        page.setTotal(workList.size());
        if(workList.size()!=0){
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
        }
        page.setData(result);
        return page;
    }

    /**
     * 一二级分类查询

     * @return
     * @throws IOException
     */
    public List<Work> searchByCategory(Integer pageNo, String categoryName, String channelName) throws IOException {
        List<Work> result = new ArrayList<>();
        Query query = new Query();
        Criteria criteriaAudit = Criteria.where("auditStatus").is(0);
        query.addCriteria(criteriaAudit);

        query.addCriteria(Criteria.where("category").is(categoryName));
        if(channelName!=null&&channelName.length()>0){
            query.addCriteria(Criteria.where("channel").is(channelName));
        }
        query.with(Sort.by(Sort.Order.desc("_id")));
        List<JSONObject> workList = this.mongoTemplate.find(query, JSONObject.class, "works");
        if(workList.size()!=0){
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
        }
        return result;
    }

}
