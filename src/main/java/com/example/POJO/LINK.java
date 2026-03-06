package com.example.POJO;

public class LINK {
    private String id;
    private String name;
    private String roadid;

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

    public String getRoadid() {
        return roadid;
    }

    public void setRoadid(String roadid) {
        this.roadid = roadid;
    }

    @Override
    public String toString() {
        return "LINK{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", roadid='" + roadid + '\'' +
                '}';
    }
}
