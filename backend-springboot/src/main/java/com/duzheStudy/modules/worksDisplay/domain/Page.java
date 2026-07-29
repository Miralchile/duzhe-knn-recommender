package com.duzheStudy.modules.worksDisplay.domain;

public class Page {
    //当前页
    private int page;
    //数据
    private Object data;
    //总计多少条数据
    private int total;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
