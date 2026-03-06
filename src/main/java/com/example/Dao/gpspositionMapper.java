package com.example.Dao;

import java.util.List;

import com.example.POJO.gpsposition;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface gpspositionMapper {

    //根据任务id删除
    @Delete("delete from GPSPOSITION where taskcode=#{taskcode}")
    void deleteByPrimaryKey(@Param("taskcode")String taskcode);

    //插入临时信息
    @Insert("insert into GPSPOSITION (id,taskcode,lng,lat,createtime,people)values(#{id,jdbcType=VARCHAR},#{taskcode,jdbcType=VARCHAR},#{lng,jdbcType=REAL},#{lat,jdbcType=REAL}, #{createtime,jdbcType=VARCHAR}, #{people,jdbcType=VARCHAR})")
    void insert(gpsposition record);



    //void insertSelective(gpsposition record);

    //根据id查临时坐标
    @Select("select * from GPSPOSITION where taskcode=#{taskcode}")
    List<gpsposition> selectByPrimaryKey(@Param("taskcode")String taskcode);

    //int updateByPrimaryKeySelective(gpsposition record);

    //根据id更新临时轨迹表
    @Update("update GPSPOSITION set \n" +
            "      lng = #{lng,jdbcType=REAL},\n" +
            "      lat = #{lat,jdbcType=REAL},\n" +
            "      createtime = #{createtime,jdbcType=VARCHAR},\n" +
            "      people = #{people,jdbcType=VARCHAR}\n" +
            "    where taskcode = #{taskcode,jdbcType=VARCHAR}")
    void updateByPrimaryKey(gpsposition record);

    //根据任务编码查临时坐标
    @Select("select * from GPSPOSITION where taskcode=#{taskcode}")
    List<gpsposition> selectByTaskcode(@Param("taskcode")String taskcode);


    //查询任务id
    @Select("select taskcode from GPSPOSITION")
    String getTaskcode();


    //任务结束删除临时表里的轨迹
    @Delete("delete from GPSPOSITION where taskcode = #{taskcode,jdbcType=VARCHAR}")
    void deleteguiji(@Param("taskcode") String taskcode);

}