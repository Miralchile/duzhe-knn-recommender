package com.duzheStudy.modules.video.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.dtflys.forest.Forest;
import com.duzheStudy.modules.video.domain.MovieInfo;
import com.duzheStudy.modules.video.domain.dto.AlgResDTO;
import com.duzheStudy.modules.video.domain.dto.MovieDTO;
import com.duzheStudy.modules.video.repository.MovieInfoRepository;
import com.duzheStudy.modules.video.service.IMovieInfoService;
import com.duzheStudy.modules.social.controller.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieInfoServiceImpl implements IMovieInfoService {

    @Resource
    private MovieInfoRepository movieInfoRepository;

    @Resource
    private MongoTemplate mongoTemplate; // 用于随机查询

    @Override
    public void importData() {
        List<MovieInfo> readData = new ArrayList<>();

        EasyExcel.read("/duzhe/video_data/movie.csv", MovieInfo.class, new ReadListener<MovieInfo>() {
                    @Override
                    public void invoke(MovieInfo movieInfo, AnalysisContext analysisContext) {
                        readData.add(movieInfo);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {}

                    @Override
                    public void onException(Exception exception, AnalysisContext context) throws Exception {
                        Integer rowIndex = context.readRowHolder().getRowIndex();
                        log.error("Skipping invalid data at row {}: {}", rowIndex, exception.getMessage(), exception);
                    }
                })
                .excelType(ExcelTypeEnum.CSV)
                .sheet()
                .doRead();

        // 批量插入到 MongoDB
        movieInfoRepository.saveAll(readData);
    }

    @Override
    public ResponseDto getRecommendation(String movieId) {
        AlgResDTO queryResult = Forest.post("http://127.0.0.1:8082/get_recommendations/")
                .contentTypeJson()
                .addBody("movie_id", movieId)
                .execute(AlgResDTO.class);

        if (queryResult.getRecommendations() == null) {
            queryResult.setRecommendations(Collections.emptyList());
        }
        if (queryResult.getRecommendations().size() < 8) {
            queryResult.getRecommendations().addAll(getRandomMovieId(queryResult.getRecommendations().size()));
        }
        List<String> movieIds = queryResult.getRecommendations().stream()
                .map(MovieDTO::getMovieID)
                .collect(Collectors.toList());

        // 使用 MongoDB 查询
        List<MovieInfo> movieInfos = movieInfoRepository.findByIdIn(movieIds);
        return ResponseDto.success(movieInfos);
    }

    private List<MovieDTO> getRandomMovieId(int size) {
        List<MovieDTO> result = new ArrayList<>(8 - size); // 改为 8，与需求一致
        // 从 MongoDB 的 works 集合中随机获取 ID
        Query query = new Query().limit(8 - size);
        List<MovieInfo> randomMovies = mongoTemplate.find(query, MovieInfo.class, "works");
        for (MovieInfo movie : randomMovies) {
            result.add(MovieDTO.builder().movieID(movie.getId()).build());
        }
        return result;
    }
}