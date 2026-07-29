package com.duzheStudy.modules.video.repository;

import com.duzheStudy.modules.video.domain.MovieInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MovieInfoRepository extends MongoRepository<MovieInfo, String> {
    List<MovieInfo> findByIdIn(List<String> ids);
}