package com.example.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OUTTASK {
    private String id;
    private String admin;
    private String people;
    private String pictureurl;
    private String statue;
    private String time;
    private String etime;
    private String origin;
    private String result;
    private String qinkuang;
    private String address;
    private String road;
    private String num;
    private String numfinish;
    private String reporttime;
    private String belong;
    private String returnword;
    private String dealafter;
    private String dangerlevel;
    private String tel;
    private String yhpeople;
    private String xjnumfinish;
    private String xjreporttime;
    private String xjreturnword;
    private String area;
    private String classify;
    private String xjbh;
    private String yhbh;
    private String xjth;
    private String yhth;
    private String lng;
    private String lat;
    private String quanshu;
    private String cljg;
    private String type;
    private String yhcontent;
    private String download;
    private String yhtel;
    private String islook;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getQinkuang() {
        return qinkuang;
    }

    public void setQinkuang(String qinkuang) {
        this.qinkuang = qinkuang;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getNumfinish() {
        return numfinish;
    }

    public void setNumfinish(String numfinish) {
        this.numfinish = numfinish;
    }

    public String getReporttime() {
        return reporttime;
    }

    public void setReporttime(String reporttime) {
        this.reporttime = reporttime;
    }

    public String getBelong() {
        return belong;
    }

    public void setBelong(String belong) {
        this.belong = belong;
    }

    public String getReturnword() {
        return returnword;
    }

    public void setReturnword(String returnword) {
        this.returnword = returnword;
    }

    public String getDealafter() {
        return dealafter;
    }

    public void setDealafter(String dealafter) {
        this.dealafter = dealafter;
    }



    @Override
    public String toString() {
        return "OUTTASK{" +
                "id='" + id + '\'' +
                ", admin='" + admin + '\'' +
                ", people='" + people + '\'' +
                ", pictureurl='" + pictureurl + '\'' +
                ", statue='" + statue + '\'' +
                ", time='" + time + '\'' +
                ", etime='" + etime + '\'' +
                ", origin='" + origin + '\'' +
                ", result='" + result + '\'' +
                ", qinkuang='" + qinkuang + '\'' +
                ", address='" + address + '\'' +
                ", road='" + road + '\'' +
                ", num='" + num + '\'' +
                ", numfinish='" + numfinish + '\'' +
                ", reporttime='" + reporttime + '\'' +
                ", belong='" + belong + '\'' +
                ", returnword='" + returnword + '\'' +
                ", dealafter='" + dealafter + '\'' +
                '}';
    }
}
