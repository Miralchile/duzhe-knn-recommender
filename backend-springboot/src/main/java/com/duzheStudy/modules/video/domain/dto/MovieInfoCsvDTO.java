package com.duzheStudy.modules.video.domain.dto;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieInfoCsvDTO {

    @ExcelProperty("userId")
    private Long userId;

    @ExcelProperty("movieId")
//    private Long movieId;
    private String movieId;
    @ExcelProperty("rating")
    private Double rating;

//    @ExcelProperty("timestamp")
//    private String timestamp;

}
