package com.example.Dao;

import com.example.POJO.SGXC;
import com.example.POJO.SGXCUSER;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SGXCMapper {

    //插入施工现场(admin)
    @Insert("insert into SGXC(id,name,road,facilities,gcname,jianshe,shigong,time,content,location,locationurl,numadmin)values(#{id},#{name},#{road},#{facilities},#{gcname},#{jianshe},#{shigong},#{time},#{content},#{location},#{locationurl},#{numadmin})")
    void saveSGXC(@Param("id")String id,@Param("name")String name,@Param("road")String road,@Param("facilities")String facilities,@Param("gcname")String gcname,@Param("jianshe")String jianshe,@Param("shigong")String shigong,@Param("time")String time,@Param("content")String content,@Param("location")String location,@Param("locationurl")String locationurl,@Param("numadmin")String numadmin);


    //施工现场上报(user)
    @Insert("insert into SGXCUSER(id,name,pictureurl,time,num,gcname)values(#{id},#{name},#{pictureurl},#{time},#{num},#{gcname})")
    void saveSGXCUSER(@Param("id")String id,@Param("name")String name,@Param("pictureurl")String pictureurl,@Param("time")String time,@Param("num")String num,@Param("gcname")String gcname);


    //获取所有施工现场(admin and user)
    @Select("select * from SGXC")
    List<SGXC> getGcname();


    //根据工程名称获取上传的图片(admin)
    @Select("select * from SGXCUSER where gcname=#{gcname} order by time asc")
    List<SGXCUSER> getPicBygcname(@Param("gcname")String gcname);

    //获取所有的施工现场

}
