package com.example.POJO;

public class SGXC {
    private String id;
    private String name;
    private String road;
    private String facilities;
    private String gcname;
    private String jianshe;
    private String time;
    private String content;
    private String location;
    private String locationurl;
    private String shigong;
    private String numadmin;

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

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getGcname() {
        return gcname;
    }

    public void setGcname(String gcname) {
        this.gcname = gcname;
    }

    public String getJianshe() {
        return jianshe;
    }

    public void setJianshe(String jianshe) {
        this.jianshe = jianshe;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationurl() {
        return locationurl;
    }

    public void setLocationurl(String locationurl) {
        this.locationurl = locationurl;
    }

    public String getShigong() {
        return shigong;
    }

    public void setShigong(String shigong) {
        this.shigong = shigong;
    }

    public String getNumadmin() {
        return numadmin;
    }

    public void setNumadmin(String numadmin) {
        this.numadmin = numadmin;
    }

    @Override
    public String toString() {
        return "SGXC{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", road='" + road + '\'' +
                ", facilities='" + facilities + '\'' +
                ", gcname='" + gcname + '\'' +
                ", jianshe='" + jianshe + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", location='" + location + '\'' +
                ", locationurl='" + locationurl + '\'' +
                ", shigong='" + shigong + '\'' +
                ", numadmin='" + numadmin + '\'' +
                '}';
    }
}
