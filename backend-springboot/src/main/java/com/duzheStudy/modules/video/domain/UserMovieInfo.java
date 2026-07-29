package com.duzheStudy.modules.video.domain;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@ApiModel(value = "UserMovieInfo对象", description = "")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMovieInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    @NotNull(message = "视频id不能为空")
//    private Long movieId;
    private String movieId;

    @NotNull(message = "评分不能为空")
    @Max(value = 10,message = "评分不能大于10")
    @Min(value = 0,message = "评分不能小于0")
    private Long score;

    @ApiModelProperty("0没处理;1处理")
    private String dealFlag;


}
