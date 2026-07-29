package com.duzheStudy.modules.social.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
@TableName("soc_article")
public class ArticleSoc  {
    @TableId(type = IdType.AUTO)
    private String id;
    private String userId;
    private Integer authority; //访问权限 0：全部人可见，1：仅好友可见
    private String publishTime;
    private String content;
    private String imgList;
    private Integer viewNum; //查阅数量

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public ArticleSoc(String id, String userId, Integer authority, String publishTime, String content, String imgList, Integer viewNum) {
        this.id = id;
        this.userId = userId;
        this.authority = authority;
        this.publishTime = publishTime;
        this.content = content;
        this.imgList = imgList;
        this.viewNum = viewNum;
    }
}
