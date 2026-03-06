package com.example.Dao;

import com.example.POJO.YANGHUINFO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface YanghuMapper {

    //插入养护信息(发布任务时，插入到养护信息表)
    @Update("update YANGHUINFO SET area=#{area,jdbcType=VARCHAR},content=#{content,jdbcType=VARCHAR},statue='未签收',stime=#{stime,jdbcType=VARCHAR},etime=#{etime,jdbcType=VARCHAR},yanghuid=#{yanghuid,jdbcType=VARCHAR} where id=#{id}")
    void insertYanghuInfo(@Param("id")String id,@Param("area")String area,@Param("type")String type,@Param("content")String content,@Param("stime")String time,@Param("etime")String endtime,@Param("yanghuid")String yanghuid);

    //插入养护信息(问题上报时)
    @Insert("insert into YANGHUINFO(id,lng,lat,originpic,xunjianid,num,reporttime)values(#{id},#{lng},#{lat},#{originpic},#{xunjianid},#{num},#{reporttime})")
    void insertYanghuInfoProblem(@Param("id")String id,@Param("lng")String lng,@Param("lat")String lat,@Param("originpic")String originpic,@Param("xunjianid")String xunjianid,@Param("num")String num,@Param("reporttime")String reporttime);

    //查询与定位养护信息
    @Select("select * from YANGHUINFO")
    List<YANGHUINFO> getYanghuInfo();

    //修改养护信息
    @Update("update YANGHUINFO SET content=#{content,jdbcType=VARCHAR},etime=#{etime,jdbcType=VARCHAR} where yanghuid=#{id,jdbcType=VARCHAR}")
    void updateYanghuInfo(@Param("id")String id,@Param("content")String content,@Param("etime")String etime);


    //删除养护信息(id是问题id)
    @Delete("delete from YANGHUINFO where id=#{id}")
    void deleteYanghuInfoById(@Param("id")String id);

    //删除养护信息(id是养护id)
    @Delete("delete from YANGHUINFO where yanghuid=#{id}")
    void deleteYanghuInfoByyanghuId(@Param("id")String id);


/*

    //养护结果上报
    @Insert("insert into YANGHUPROBLEM(id,name,people,objectid,time,pictureurl)values(#{id},#{name},#{people},#{objectid},#{time},#{pictureurl},)")
    void reportYanghu(@Param("id")String id,@Param("name")String name,@Param("people")String people,@Param("objectid")String objectid,@Param("time")String time,@Param("pictureurl")String pictureurl);
*/

    //养护结果上报（上报到养护信息表,id是任务id）
    @Update("update YANGHUINFO SET objectid=#{objectid,jdbcType=VARCHAR},people=#{people,jdbcType=VARCHAR},pictureurl=#{pictureurl,jdbcType=VARCHAR},statue='待审核',numlater=#{numlater,jdbcType=VARCHAR} where yanghuid=#{id,jdbcType=VARCHAR}")
    void reportYanghu(@Param("id")String id,@Param("people")String people,@Param("objectid")String objectid,@Param("time")String time,@Param("pictureurl")String pictureurl,@Param("numlater")String numlater);

    //养护结果上报(修改任务信息表,id是任务id)
    @Update("update TASKINFO SET statue=2 where yanghuid=#{id,jdbcType=VARCHAR}")
    void updateYanghuTask(@Param("id")String id);


    //养护记录
    @Select("select * from YANGHUINFO where people=#{people} and (statue='待审核' or statue='待评分' or statue='已评分')")
    List<YANGHUINFO> getYanghurecord(@Param("people")String people);


    //接取养护任务更改养护信息表里信息
    @Update("update YANGHUINFO SET people=#{people,jdbcType=VARCHAR},statue='养护中' where yanghuid=#{id,jdbcType=VARCHAR}")
    void updateYanghupeople(@Param("id")String id,@Param("people")String people);

    //删除养护任务更改养护信息表里信息
    @Update("update YANGHUINFO SET people=null,statue='未签收' where yanghuid=#{id,jdbcType=VARCHAR}")
    void updateYanghudelete(@Param("id")String id,@Param("people")String people);



    //管理员评分
    @Update("update YANGHUINFO SET score=#{score,jdbcType=VARCHAR},statue='已评分' where yanghuid=#{yanghuid,jdbcType=VARCHAR}")
    void updateScore(@Param("yanghuid")String id,@Param("score")String score);


    //根据养护任务id查询养护前的图片
    @Select("select * from YANGHUINFO where yanghuid=#{yanghuid}")
    List<YANGHUINFO> getYHpic(@Param("yanghuid")String yanghuid);


    //获取未接收的养护任务
    @Select("select * from YANGHUINFO where statue='未签收'")
    List<YANGHUINFO> getYhtask();


}
