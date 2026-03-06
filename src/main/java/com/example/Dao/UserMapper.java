package com.example.Dao;

import com.alibaba.fastjson.JSONObject;
import com.example.POJO.LINK;
import com.example.POJO.USERINFO;
import com.example.POJO.fengong;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //用户和管理员登录
    @Select("select * from USERINFO where name=#{name,jdbcType=VARCHAR} and pwd=#{pwd,jdbcType=VARCHAR} and role=#{role,jdbcType=VARCHAR}")
    JSONObject login(@Param("name")String name,@Param("pwd")String pwd,@Param("role")String role);

    //用户第一次登录修改密码
    @Update("update USERINFO set pwd=#{pwd,jdbcType=VARCHAR} where name=#{name,jdbcType=VARCHAR}")
    void updatePwd(@Param("name")String name,@Param("pwd")String pwd);

    //根据用户名查询手机号
    @Select("select tel from USERINFO where name=#{name,jdbcType=VARCHAR}")
    String getTel(@Param("name")String name);


    //根据用户名查询角色
    @Select("select role from USERINFO where name=#{name,jdbcType=VARCHAR}")
    String getRole(@Param("name")String name);

    //根据用户名、密码、角色查询id
    @Select("select id from USERINFO where name=#{name,jdbcType=VARCHAR} and pwd=#{pwd,jdbcType=VARCHAR} and role=#{role,jdbcType=VARCHAR}")
    String getRoleByThree(@Param("name")String name,@Param("pwd")String pwd,@Param("role")String role);


    //根据用户名查询信息
    @Select("select * from USERINFO where name=#{name,jdbcType=VARCHAR}")
    String getInfoByname(@Param("name")String name);


    //根据用户名查询密码
    @Select("select pwd from USERINFO where name=#{name,jdbcType=VARCHAR}")
    String getPwdByname(@Param("name")String name);


    //超级管理员注册用户和管理员
    @Insert("insert into USERINFO(id,name,pwd,role,tel)values(#{id},#{name},#{pwd},#{role},#{tel})")
    void registerUser(@Param("id")String id,@Param("name")String name,@Param("pwd")String pwd,@Param("role")String role,@Param("tel")String tel);


    //超级管理员删除用户和管理员
    @Delete("delete from USERINFO where id=#{id}")
    void deletePeopleById(@Param("id")String id);


    //超级管理员修改用户和管理员
    @Update("update USERINFO set name=#{name,jdbcType=VARCHAR},role=#{role,jdbcType=VARCHAR},tel=#{tel,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void updatePeopleById(@Param("id")String id,@Param("name")String name,@Param("role")String role,@Param("tel")String tel);


    //超级管理员查询所有用户和管理员
    @Select("select * from USERINFO")
    List<USERINFO> getUserInfo();

    //超级管理员根据用户名查询用户信息
    @Select("select name from USERINFO where role=#{role}")
    List<String> getnamebyrole(@Param("role")String role);



    //超级管理员重置密码
    @Update("update USERINFO set pwd=#{pwd,jdbcType=VARCHAR} where name=#{name,jdbcType=VARCHAR}")
    void resetPwd(@Param("name")String name,@Param("pwd")String pwd);


    //获取所有道路信息(fengong表，gouguan去重,确保路段名不为空,并将不明确路段放在第一个)
    //@Select("select DISTINCT gouguan from FENGONG where gouguan is not null ORDER BY CASE WHEN id = 'unknownroadId'  THEN 0 ELSE 1 END")

    @Select("SELECT DISTINCT gouguan FROM FENGONG WHERE gouguan IS NOT NULL ORDER BY (CASE WHEN gouguan = '不明确路段' THEN 0 ELSE 1 END)")
    List<fengong> getFengong();


    //往道路表里插入数据(fengong表)
    @Insert("insert into FENGONG(id,gouguan,qizhi)values(#{id},#{gouguan},#{qizhi})")
    void insertfengong(@Param("id")String id,@Param("gouguan")String gouguan,@Param("qizhi")String qizhi);


    //根据道路和起止点查询道路id(fengong表)
    @Select("select id from FENGONG where gouguan=#{gouguan,jdbcType=VARCHAR} and qizhi=#{qizhi,jdbcType=VARCHAR}")
    String getidbygouguan(@Param("gouguan")String gouguan,@Param("qizhi")String qizhi);


    //往关联表里插入数据(link表)
    @Insert("insert into LINK(id,name,roadid)values(#{id},#{name},#{roadid})")
    void insertLink(@Param("id")String id,@Param("name")String name,@Param("roadid")String roadid);


    //根据用户名查询道路和起止点信息(link表)
    @Select("select * from LINK where name=#{name}")
    List<LINK> getroad(@Param("name")String name);


    //根据路段id查询道路表里道路和起止点信息(fengong表)
    @Select("select * from FENGONG where id=#{id}")
    List<fengong> getroadbyroadid(@Param("id")String roadid);

    //根据道路名查询道路id
    @Select("select id from FENGONG where gouguan=#{road}")
    List<String> getroadidbyroad(@Param("road")String road);

    //根据道路id查询用户名
    @Select("select distinct name from LINK where roadid=#{roadid}")
    List<String> getnamebyroadid(@Param("roadid")String roadid);



    //根据道路id查询link表信息(link表)
    @Select("select * from LINK where roadid=#{roadid}")
    List<LINK> getlinkid(@Param("roadid")String roadid);


    //根据roadid跟新link表
    @Update("update LINK set name=#{name,jdbcType=VARCHAR} where roadid=#{roadid,jdbcType=VARCHAR}")
    void updatelink(@Param("name")String name, @Param("roadid")String roadid);


    //超级管理员根据用户名查询用户信息
    @Select("select * from USERINFO where name=#{name}")
    USERINFO getUserInfobyname(@Param("name")String name);




    //上传excel时存在用户更新用户（不更新密码）
    @Update("update USERINFO set tel=#{tel,jdbcType=VARCHAR},role=#{role,jdbcType=VARCHAR} where name=#{name,jdbcType=VARCHAR}")
    void uploaduser(@Param("name")String name, @Param("tel")String tel,@Param("role")String role);



    //根据用户名把该用户的路段删掉
    @Delete("delete from LINK where name=#{name}")
    void deletelinkByname(@Param("name")String name);


    //根据roadid删除fengong表里的数据
    @Delete("delete from FENGONG where id=#{roadid}")
    void delroadByid(@Param("roadid")String roadid);

    //上传excel根据道路表全删掉
    @Delete("delete from FENGONG")
    void deletefengong();

    //上传excel把link表里的东西全删掉
    @Delete("delete from LINK")
    void deletelink();

    //上传excel把用户表里所有用户删掉
    @Delete("delete from USERINFO")
    void deleteAllUser();

    //上传excel插入超级管理员
    @Insert("insert into USERINFO(id,name,tel,role,pwd)values(#{id},#{name},#{tel},#{role},#{pwd})")
    void insetSVIP(@Param("id")String id,@Param("name")String name,@Param("tel")String tel,@Param("role")String role,@Param("pwd")String pwd);




}
