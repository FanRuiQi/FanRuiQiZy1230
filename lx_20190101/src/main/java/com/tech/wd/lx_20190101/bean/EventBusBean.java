package com.tech.wd.lx_20190101.bean;

public class EventBusBean {
    private int id;
    private String title;
    private String price;

    public EventBusBean(int id, String title, String price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }
}
