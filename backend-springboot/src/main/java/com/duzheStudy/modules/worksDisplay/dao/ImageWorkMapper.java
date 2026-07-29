package com.duzheStudy.modules.worksDisplay.dao;

import com.alibaba.fastjson.JSON;
import com.duzheStudy.modules.worksDisplay.domain.ImageWork;
import com.duzheStudy.modules.worksDisplay.domain.Work;
import org.bson.types.ObjectId;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ImageWorkMapper {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ImageWork> getSimpleNewsImageList() {
        Query query = new Query();
        List<ImageWork> imageWorkList = this.mongoTemplate.find(query, ImageWork.class, "simpleNewsImage");
        List<ImageWork> result = imageWorkList.subList(0,4);
        return result;
    }

    public ImageWork getImageById(String id){
        Query query = new Query();
        ObjectId objectId = new ObjectId(id);
        Criteria criteria = Criteria.where("_id").is(objectId);
        query.addCriteria(criteria);
        query.fields().exclude("thumbList");
        query.fields().exclude("commentList");


        ImageWork imageWork = this.mongoTemplate.findOne(query, ImageWork.class,"works");
        Update update = new Update().set("views",imageWork.getViews()+1);
        this.mongoTemplate.updateFirst(query,update,"works");
        return imageWork;
    }

    public void addImageWork(ImageWork imageWork) throws IOException {
        ImageWork imageWorkSaveResult = this.mongoTemplate.insert(imageWork,"works");

        imageWorkSaveResult.setWorkName(imageWorkSaveResult.get_id());

//        IndexRequest request = new IndexRequest("works");
//
//        request.id("1");
//        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
//
//        request.source(JSON.toJSONString(imageWorkSaveResult), XContentType.JSON);
//        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }
}
