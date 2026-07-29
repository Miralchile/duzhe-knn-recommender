package com.duzheStudy.framework.convert;

import org.bson.BsonTimestamp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class BsonTimestampToLongConverter implements Converter<BsonTimestamp, Long> {

    @Override
    public Long convert(BsonTimestamp bsonTimestamp) {
        return (long)bsonTimestamp.getTime();
    }
}