package com.duzheStudy.modules.collection.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * collection
 * @author 
 */
public class Collection implements Serializable {
    private Long id;

    private Long userId;

    private Long folderId;

    private String workId;

    private Date createdby;

    private Date updatedby;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}