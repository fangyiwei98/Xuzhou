package com.example.POJO;

public class fengong {
    private String id;
    private String qizhi;
    private String gouguan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQizhi() {
        return qizhi;
    }

    public void setQizhi(String qizhi) {
        this.qizhi = qizhi;
    }

    public String getGouguan() {
        return gouguan;
    }

    public void setGouguan(String gouguan) {
        this.gouguan = gouguan;
    }

    @Override
    public String toString() {
        return "fengong{" +
                "id='" + id + '\'' +
                ", qizhi='" + qizhi + '\'' +
                ", gouguan='" + gouguan + '\'' +
                '}';
    }
}
