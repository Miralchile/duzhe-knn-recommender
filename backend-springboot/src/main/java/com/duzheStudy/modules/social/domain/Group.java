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
//@EqualsAndHashCode(callSuper = true)
@TableName("soc_group")
public class Group {
    private Long id;
    private String name;
    private String avatar;
    private String introduction;
    private Long ownerId;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Group(Long id, String name, String avatar, String introduction, Long ownerId) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.introduction = introduction;
        this.ownerId = ownerId;
    }

}
