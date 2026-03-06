package com.example.POJO;

public class PROBLEM {
    private String id;
    private String people;
    private String name;
    private String lng;
    private String lat;
    private String time;
    private String content;
    private String pictureurl;
    private String returnresult;
    private String taskcode;
    private String type;
    private String dangerlevel;
    private String num;
    private String area;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getReturnresult() {
        return returnresult;
    }

    public void setReturnresult(String returnresult) {
        this.returnresult = returnresult;
    }

    public String getTaskcode() {
        return taskcode;
    }

    public void setTaskcode(String taskcode) {
        this.taskcode = taskcode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDangerlevel() {
        return dangerlevel;
    }

    public void setDangerlevel(String dangerlevel) {
        this.dangerlevel = dangerlevel;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "PROBLEM{" +
                "id='" + id + '\'' +
                ", people='" + people + '\'' +
                ", name='" + name + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", pictureurl='" + pictureurl + '\'' +
                ", returnresult='" + returnresult + '\'' +
                ", taskcode='" + taskcode + '\'' +
                ", type='" + type + '\'' +
                ", dangerlevel='" + dangerlevel + '\'' +
                ", num='" + num + '\'' +
                ", area='" + area + '\'' +
                '}';
    }
}
