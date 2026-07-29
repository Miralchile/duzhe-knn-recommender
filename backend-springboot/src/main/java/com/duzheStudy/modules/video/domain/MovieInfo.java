package com.duzheStudy.modules.video.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel(value = "MovieInfo对象", description = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "works") // 指定 特定MongoDB 集合
public class MovieInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id // MongoDB 的 ID 字段
    @ExcelProperty("movieId")
    private String id;

    @ApiModelProperty("题目")
    @ExcelProperty("title")
    private String title;

    @ApiModelProperty("作者")
    @ExcelProperty("author")
    private String author;

    @ApiModelProperty("地址")
    private String url;

    @ApiModelProperty("类别")
    private String category;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("时长")
    private Long duration; // 使用 Long 对应 Int64
}