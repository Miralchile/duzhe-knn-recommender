package com.duzheStudy.framework.config;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MongoDBConfig{

    @Value("${spring.data.mongodb.uri}")
    private String uri;


    @Bean
    public MongoDbFactory mongoDbFactory() {
        MongoClientURI mongoClientURI=new MongoClientURI(uri,new MongoClientOptions.Builder());
        SimpleMongoDbFactory simpleMongoDbFactory=new SimpleMongoDbFactory(mongoClientURI);
        return simpleMongoDbFactory;
    }

    @Bean
    public MongoCustomConversions  customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(new BsonTimestampToLongConverter());
        return new MongoCustomConversions(converters);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory){
        MappingMongoConverter converter = new MappingMongoConverter(mongoDbFactory, new MongoMappingContext());
        converter.setCustomConversions(customConversions()); // 添加自定义转换器
        converter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory,converter);
    }

}
