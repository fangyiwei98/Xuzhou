package com.example.POJO;

public class TASKINFO {
    private String id;
    private String area;
    private String type;
    private String character;
    private String content;
    private String admin;
    private String time;
    private String endtime;
    private String statue;
    private String people;
    private String peopletype;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPeopletype() {
        return peopletype;
    }

    public void setPeopletype(String peopletype) {
        this.peopletype = peopletype;
    }

    @Override
    public String toString() {
        return "TASKINFO{" +
                "id='" + id + '\'' +
                ", area='" + area + '\'' +
                ", type='" + type + '\'' +
                ", character='" + character + '\'' +
                ", content='" + content + '\'' +
                ", admin='" + admin + '\'' +
                ", time='" + time + '\'' +
                ", endtime='" + endtime + '\'' +
                ", statue='" + statue + '\'' +
                ", people='" + people + '\'' +
                ", peopletype='" + peopletype + '\'' +
                '}';
    }
}
