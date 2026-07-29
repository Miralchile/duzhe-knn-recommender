package com.duzheStudy.modules.others.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * follow
 * @author 
 */
public class Follow implements Serializable {
    private Long id;

    private Long userId;

    private Long followUserId;

    private Date createdby;

    private Date updatedby;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFollowUserId() {
        return followUserId;
    }

    public void setFollowUserId(Long followUserId) {
        this.followUserId = followUserId;
    }

    public Date getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Date createdby) {
        this.createdby = createdby;
    }

    public Date getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(Date updatedby) {
        this.updatedby = updatedby;
    }
}