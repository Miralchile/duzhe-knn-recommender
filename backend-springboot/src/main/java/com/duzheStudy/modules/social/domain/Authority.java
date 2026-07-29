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
@TableName("soc_authority")
public class Authority {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String type;  //权限类型{login-forbidden:禁止登录}
    private Long userId;
    private String information;  //权限类型
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Boolean isDeleted;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Authority(Long id, String type, Long userId, String information) {
        this.id = id;
        this.type = type;
        this.userId = userId;
        this.information = information;
    }
}
