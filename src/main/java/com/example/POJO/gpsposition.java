package com.example.POJO;

public class gpsposition {
    private String id;

    private String taskcode;

    private double lng;

    private double lat;

    private String createtime;

    private String people;


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

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "gpsposition{" +
                "id='" + id + '\'' +
                ", taskcode='" + taskcode + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", createtime='" + createtime + '\'' +
                ", people='" + people + '\'' +
                '}';
    }
}