package com.duzheStudy.modules.social.domain;

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
@TableName("soc_comment")
public class Comment {
    private String id;
    private String articleId;
    private String userId;
    private String content;
    private String publishTime;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Comment(String id, String articleId, String userId, String content, String publishTime) {
        this.id = id;
        this.articleId = articleId;
        this.userId = userId;
        this.content = content;
        this.publishTime = publishTime;
    }
}
