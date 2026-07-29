package com.duzheStudy.modules.video.mapper;

import com.duzheStudy.modules.video.domain.MovieInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieInfoMapper {

    MovieInfo selectById(@Param("id") Integer id);

    List<MovieInfo> selectAll();

    int insert(MovieInfo movieInfo);

    int update(MovieInfo movieInfo);

    int deleteById(@Param("id") Integer id);

    int batchInsert(@Param("list") List<MovieInfo> list);

    List<MovieInfo> selectBatchIds(@Param("movieIds")List<String> movieIds);
}
