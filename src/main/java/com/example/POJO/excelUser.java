package com.example.POJO;


public class excelUser {

    private String id;
    private String name;
    private String tel;
    private String role;
    private String road;
    private String qizhi;

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

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
        return "excelUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", role='" + role + '\'' +
                ", road='" + road + '\'' +
                ", qizhi='" + qizhi + '\'' +
                '}';
    }
}
