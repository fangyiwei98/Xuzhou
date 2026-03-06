package com.example.POJO;

public class roads {
    private String road;
    private String qizhi;

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getQizhi() {
        return qizhi;
    }

    public void setQizhi(String qizhi) {
        this.qizhi = qizhi;
    }

    @Override
    public String toString() {
        return "roads{" +
                "road='" + road + '\'' +
                ", qizhi='" + qizhi + '\'' +
                '}';
    }
}
