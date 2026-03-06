package com.example.Service.ServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.example.Dao.UserMapper;
import com.example.POJO.USERINFO;
import com.example.POJO.fengong;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    //用户和管理员登录
    @Override
    public JSONObject Login(String name, String pwd, String role) {
        return userMapper.login(name, pwd,role);
    }

    //用户第一次登录修改密码
    @Override
    public void updatePwd(String name, String pwd) {
        userMapper.updatePwd(name, pwd);
    }

    //根据用户名和密码查询角色
    @Override
    public String getRole(String name) {
        return userMapper.getRole(name);
    }

    //根据用户名查询密码
    @Override
    public String getPwdByname(String name) {
        return userMapper.getPwdByname(name);
    }

    //根据用户名、密码、角色查询id
    @Override
    public String getRoleByThree(String name, String pwd, String role) {
        return userMapper.getRoleByThree(name, pwd, role);
    }

    //根据用户名查询信息
    @Override
    public String getInfoByname(String name) {
        return userMapper.getInfoByname(name);
    }


    //超级管理员注册用户和管理员
    @Override
    public void registerUser(String id, String name, String pwd,String role,String tel) {
        userMapper.registerUser(id, name, pwd,role,tel);
    }
    //超级管理员删除用户和管理员
    @Override
    public void deletePeopleById(String id) {
        userMapper.deletePeopleById(id);
    }


    //超级管理员查询用户和管理员
    @Override
    public List<USERINFO> getUserInfo() {
        return userMapper.getUserInfo();
    }


    //获取分工区域
    @Override
    public List<fengong> getFengong() {
        return userMapper.getFengong();
    }

    //超级管理员重置密码
    @Override
    public void resetPwd(String name, String pwd) {
        userMapper.resetPwd(name, pwd);
    }

    //把道路名和起止点插入到道路表里
    @Override
    public void insertroad(String id, String road, String qizhi) {
        userMapper.insertfengong(id, road, qizhi);
    }

    //根据道路名和起止点查询道路id
    @Override
    public String getdaoluidbyroadandqizhi(String road, String qizhi) {
        return userMapper.getidbygouguan(road, qizhi);
    }

    //往关联表里插入数据
    @Override
    public void insertintolink(String linkid, String name, String roadid) {
        userMapper.insertLink(linkid, name, roadid);
    }

    //用name和daoluid更新link表
    @Override
    public void updatelinkbydaoluidandname(String name,String daoluid) {
        userMapper.updatelink(name,daoluid);
    }

    //根据路段id查询道路表里道路和起止点信息
    @Override
    public List<fengong> getroadbyroadid(String roadid) {
        return userMapper.getroadbyroadid(roadid);
    }

    //上传excel存在用户更新数据库
    @Override
    public void uploaduser(String name, String tel, String role) {
        userMapper.uploaduser(name, tel, role);
    }


    //根据道路和起止点查询daoluid
    @Override
    public String getidbygouguan(String gouguan, String qizhi) {
        return userMapper.getidbygouguan(gouguan,qizhi);
    }

    @Override
    public void deletelinkByname(String name) {
        userMapper.deletelinkByname(name);
    }

    //把道路表全删掉
    @Override
    public void deletefengong() {
        userMapper.deletefengong();
    }

    //上传excel把link表里的东西全删掉
    @Override
    public void deletelink() {
        userMapper.deletelink();
    }


}
