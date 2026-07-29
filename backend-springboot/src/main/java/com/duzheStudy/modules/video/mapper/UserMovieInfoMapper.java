package com.duzheStudy.modules.video.mapper;

import com.duzheStudy.modules.video.domain.UserMovieInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Delete;
import java.util.List;


@Repository
public interface UserMovieInfoMapper {

    UserMovieInfo selectById(Integer id);

    List<UserMovieInfo> selectAll();

    int insert(UserMovieInfo userMovieInfo);

    int update(UserMovieInfo userMovieInfo);

    int deleteById(Integer id);

    List<UserMovieInfo> getUserLast5Data(Long userId);

    void updateByIds(@Param("ids") List<Long> ids);
    @Select("SELECT * FROM user_movie_info WHERE user_id = #{userId} AND movie_id = #{movieId}")
    UserMovieInfo selectByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") String movieId);
    @Delete("DELETE FROM user_movie_info WHERE user_id = #{userId} AND movie_id = #{movieId}")
    int deleteByUserIdAndMovieId(@Param("userId") Long userId, @Param("movieId") String movieId);
}

