package com.duzheStudy.modules.worksDisplay.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * channel
 * @author 
 */
public class Channel implements Serializable {
    private Long id;

    private String channelName;

    private Integer channelOrder;

    private Long categoryId;

    private Date createdby;

    private Date updatedby;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getChannelOrder() {
        return channelOrder;
    }

    public void setChannelOrder(Integer channelOrder) {
        this.channelOrder = channelOrder;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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