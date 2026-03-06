package com.example.Dao;

import com.example.POJO.PLAN;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PlanMapper {
    //制定新计划(admin)
    @Insert("insert into PLAN(id,type,area,name,content,counts,admin,time,stime,etime)values(#{id},#{type},#{area},#{name},#{content},#{counts},#{admin},#{time},#{stime},#{etime})")
    void saveNewplan(@Param("id")String id, @Param("type")String type, @Param("area")String area, @Param("name")String name, @Param("content")String content, @Param("counts")String counts, @Param("admin")String admin, @Param("time")String time, @Param("stime")String stime, @Param("etime")String etime);

    //查询计划(admin)
    @Select("select * from PLAN")
    List<PLAN> showPlan();

    //修改计划(admin)
    @Update("update PLAN set type=#{type,jdbcType=VARCHAR},area=#{area,jdbcType=VARCHAR},name=#{name,jdbcType=VARCHAR},content=#{content,jdbcType=VARCHAR},counts=#{counts,jdbcType=VARCHAR},admin=#{admin,jdbcType=VARCHAR},stime=#{stime,jdbcType=VARCHAR},etime=#{etime,jdbcType=VARCHAR},time=#{time,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void updatePlan(@Param("id")String id,@Param("type")String type,@Param("area")String area,@Param("name")String name,@Param("content")String content,@Param("counts")String counts,@Param("admin")String admin,@Param("stime")String stime,@Param("etime")String etime,@Param("time")String time);

    //删除计划
    @Delete("delete from PLAN where id=#{id}")
    void deletePlanById(@Param("id")String id);

}
