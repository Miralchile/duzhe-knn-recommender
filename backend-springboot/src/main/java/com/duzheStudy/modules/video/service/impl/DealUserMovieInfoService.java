package com.duzheStudy.modules.video.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSONObject;
import com.dtflys.forest.Forest;
import com.duzheStudy.modules.video.domain.UserMovieInfo;
import com.duzheStudy.modules.video.domain.dto.MovieInfoCsvDTO;
import com.duzheStudy.modules.video.mapper.UserMovieInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Async
@Service
@Slf4j
public class DealUserMovieInfoService {

    private final static int batchSize = 10000;

    @Resource
    private UserMovieInfoMapper userMovieInfoMapper;

    public void dealUserMovieInfo(Long userId) {

        List<UserMovieInfo> lastData = userMovieInfoMapper.getUserLast5Data(userId);
        long count = lastData.stream().filter(r -> "0".equals(r.getDealFlag())).count();
        log.info("dealUserMovieInfo.user un deal movie info count={}", count);
        if (count < 5L) {
            return;
        }
        // 更新数据
        userMovieInfoMapper.updateByIds(lastData.stream().map(UserMovieInfo::getId).collect(Collectors.toList()));
        // 将数据更新到csv文件
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 调用算法处理数据
        String execute = Forest.post("http://127.0.0.1:8082/set_execution_flag/")
                .contentTypeJson()
                .addBody(JSONObject.of().toJSONString())
                .execute(String.class);
        log.info("dealUserMovieInfo.calculate.res=：{}", execute);

        String filePath = "/duzhe/video_data/rating.csv";
        List<MovieInfoCsvDTO> readData = readCsvFileInBatches(filePath, lastData);

//        Map<Long, UserMovieInfo> updateMap = lastData.stream().collect(Collectors.toMap(UserMovieInfo::getMovieId, Function.identity(), (o1, o2) -> o2));
        Map<String, UserMovieInfo> updateMap = lastData.stream()
                .collect(Collectors.toMap(UserMovieInfo::getMovieId, Function.identity(), (o1, o2) -> o2));
        //修改数据
        readData.forEach(obj -> {
            if (userId.equals(obj.getUserId()) && obj.getMovieId() != null && updateMap.containsKey(obj.getMovieId())) {
                obj.setRating(updateMap.get(obj.getMovieId()).getScore().doubleValue());
            }
        });

        // 写入数据
        EasyExcel.write(filePath, MovieInfoCsvDTO.class).sheet().doWrite(readData);
        stopWatch.stop();
        log.info("dealUserMovieInfo.time={} s", stopWatch.getTotalTimeSeconds());
    }

    private List<MovieInfoCsvDTO> readCsvFileInBatches(String filePath, List<UserMovieInfo> updateList) {
        List<MovieInfoCsvDTO> res = new ArrayList<>();
        ExcelReader build = EasyExcel.read(filePath, MovieInfoCsvDTO.class, new AnalysisEventListener<MovieInfoCsvDTO>() {
                    @Override
                    public void invoke(MovieInfoCsvDTO data, AnalysisContext context) {
                        res.add(data);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {

                    }
                })
                .build();
        try {
            // 执行读取
            build.readAll();
        } finally {
            // 确保释放资源
            build.finish();
        }
        return res;
    }
}
