package com.duzheStudy.modules.collection.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * folder
 * @author 
 */
public class Folder implements Serializable {
    protected Long id;

    protected Long userId;

    protected String folderName;

    protected String folderCoverImage;

    protected Date createdby;

    protected Date updatedby;

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

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderCoverImage() {
        return folderCoverImage;
    }

    public void setFolderCoverImage(String folderCoverImage) {
        this.folderCoverImage = folderCoverImage;
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