package com.example.POJO;

public class PLAN {
    private String id;
    private String type;
    private String area;
    private String name;
    private String counts;
    private String content;
    private String time;
    private String stime;
    private String etime;
    private String admin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }


    @Override
    public String toString() {
        return "PLAN{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", area='" + area + '\'' +
                ", name='" + name + '\'' +
                ", counts='" + counts + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", stime='" + stime + '\'' +
                ", etime='" + etime + '\'' +
                ", admin='" + admin + '\'' +
                '}';
    }
}
