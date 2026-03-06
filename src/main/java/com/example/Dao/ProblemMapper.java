package com.example.Dao;

import com.example.POJO.PROBLEM;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProblemMapper {

    //问题上报查询
    @Select("select * from PROBLEM order by time")
    List<PROBLEM> getProblem();


    //存入问题上报信息
    @Insert("insert into PROBLEM(id,people,lng,lat,time,content,type,PICTUREURL,taskcode,returnresult,dangerlevel,num,area)values(#{id},#{people},#{lng},#{lat},#{time},#{content},#{type},#{PICTUREURL},#{taskcode},0,#{dangerlevel},#{num},#{area})")
    void saveProblem(@Param("id")String id,@Param("people")String people,@Param("lng")String lng,@Param("lat")String lat,@Param("time")String time,@Param("content")String content,@Param("type")String type,@Param("PICTUREURL")String picutreurl,@Param("taskcode")String taskcode,@Param("dangerlevel")String dangerlevel,@Param("num")String num,@Param("area")String area);

    //问题上报记录
    @Select("select * from PROBLEM where people=#{people}")
    List<PROBLEM> getProblemBypeople(@Param("people")String people);


    //上报结果返回
    @Update("update PROBLEM set returnresult=#{returnresult,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void updateProblem(@Param("id")String id,@Param("returnresult")String returnresult);


    //根据任务号查问题
    @Select("select * from PROBLEM where taskcode=#{taskcode}")
    List<PROBLEM> getProBytaskcode(@Param("taskcode")String taskcode);



}
