package com.example.a19526811_tranvannhan_androidexam.entity;

import java.sql.Date;

public class Task {
    private String id;
    private String name;
    private String priority;
    private Date date;

    public Task() {
    }

    public Task(String id, String name, String priority, Date date) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", priority='" + priority + '\'' +
                ", date=" + date +
                '}';
    }
}