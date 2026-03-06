package com.example.Service;

import com.alibaba.fastjson.JSONObject;
import com.example.POJO.USERINFO;
import com.example.POJO.fengong;

import java.util.List;

public interface UserService {


    //用户和管理员登录
    JSONObject Login(String name,String pwd,String role);

    //用户第一次登录修改密码
    void updatePwd(String name,String pwd);

    //根据用户名查询角色
    String getRole(String name);


    //根据用户名查询密码
    String getPwdByname(String name);


    //根据用户名、密码、角色查询id
    String getRoleByThree(String name,String pwd,String role);

    //根据用户名查询信息
    String getInfoByname(String name);

    //超级管理员注册用户和管理员
    void registerUser(String id,String name,String pwd ,String role,String tel);


    //超级管理员删除用户和管理员
    void deletePeopleById(String id);



    //超级管理员查询用户和管理员
    List<USERINFO> getUserInfo();

    //获取分工区域
    List<fengong> getFengong();


    //超级管理员重置密码
    void resetPwd(String name,String pwd);


    //把道路名和起止点插入到道路表里
    void insertroad(String id,String road,String qizhi);


    //根据道路名和起止点查询道路id
    String getdaoluidbyroadandqizhi(String road,String qizhi);

    //往关联表里插入数据
    void insertintolink(String linkid,String name,String roadid);

    //用daoluid和name更新link表
    void updatelinkbydaoluidandname(String name,String daoluid);



    //根据路段id查询道路表里道路和起止点信息
    List<fengong> getroadbyroadid(String roadid);


    //上传excel存在用户更新数据库
    void uploaduser(String name,String tel,String role);

    //根据道路和起止点查询daoluid
    String getidbygouguan(String gouguan,String qizhi);


    //根据用户名把该用户的路段删掉
    void deletelinkByname(String name);

    //把道路表全删掉
    void deletefengong();


    //上传excel把link表里的东西全删掉
    void deletelink();

}
