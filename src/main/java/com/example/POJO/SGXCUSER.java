package com.example.POJO;

public class SGXCUSER {
    private String id;
    private String name;
    private String pictureurl;
    private String time;
    private String num;
    private String gcname;

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

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGcname() {
        return gcname;
    }

    public void setGcname(String gcname) {
        this.gcname = gcname;
    }

    @Override
    public String toString() {
        return "SGXCUSER{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pictureurl='" + pictureurl + '\'' +
                ", time='" + time + '\'' +
                ", num='" + num + '\'' +
                ", gcname='" + gcname + '\'' +
                '}';
    }
}
