package com.example.POJO;

import java.util.Date;

public class trajectory {
    private String id;

    private String taskcode;

    private String createtime;

    private String checkmile;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskcode() {
        return taskcode;
    }

    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCheckmile() {
        return checkmile;
    }

    public void setCheckmile(String checkmile) {
        this.checkmile = checkmile;
    }

    @Override
    public String toString() {
        return "trajectory{" +
                "id='" + id + '\'' +
                ", taskcode='" + taskcode + '\'' +
                ", createtime='" + createtime + '\'' +
                ", checkmile='" + checkmile + '\'' +
                '}';
    }
}