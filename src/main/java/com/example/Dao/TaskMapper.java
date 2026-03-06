package com.example.Dao;

import com.example.POJO.TASKINFO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TaskMapper {
    //发布任务
    @Insert("insert into TASKINFO(id,area,type,character,content,admin,time,endtime,statue)values(#{id},#{area},#{type},#{character},#{content},#{admin},#{time},#{endtime},0)")
    void paifaTask(@Param("id")String id,@Param("area")String area,@Param("type")String type, @Param("character")String character,@Param("content")String content, @Param("admin")String admin, @Param("time")String time, @Param("endtime")String endtime);
    //派发任务
    @Insert("insert into TASKINFO(id,area,type,character,content,admin,time,endtime,statue,people,peopletype)values(#{id},#{area},#{type},#{character},#{content},#{admin},#{time},#{endtime},1,#{people},#{peopletype})")
    void saveSpecialTask(@Param("id")String id,@Param("area")String area,@Param("type")String type, @Param("character")String character,@Param("content")String content, @Param("admin")String admin, @Param("time")String time, @Param("endtime")String endtime,@Param("people")String people,@Param("peopletype")String peopletype);

    //查询所有的任务信息
    @Select("select * from TASKINFO order by endtime")
    List<TASKINFO> showTask();

    //管理员审核通过任务状态（任务表里）
    @Update("update TASKINFO set statue=3 where id=#{id,jdbcType=VARCHAR}")
    void finishTask(@Param("id")String id);

    //管理员驳回任务
    @Update("update TASKINFO set statue=1 where id=#{id,jdbcType=VARCHAR}")
    void bohuiTask(@Param("id")String id);


    //管理员通过养护任务（养护信息表里）
    @Update("update YANGHUINFO set statue='待评分' where yanghuid=#{id,jdbcType=VARCHAR}")
    void finishYanghuTask(@Param("id")String id);

    //管理员驳回养护任务
    @Update("update YANGHUINFO set statue='养护中' where yanghuid=#{id,jdbcType=VARCHAR}")
    void bohuiYanghuTask(@Param("id")String id);


    //巡检人员提交将任务状态改为待审核
    @Update("update TASKINFO set statue=2 where id=#{id,jdbcType=VARCHAR}")
    void finishXunjianTask(@Param("id")String id);


    //根据任务类型查询任务信息（admin）
    @Select("select * from TASKINFO where character=#{character}")
    List<TASKINFO> showTaskBytype(@Param("character")String type);


    //根据任务性质查询任务信息（user）
    @Select("select * from TASKINFO where character=#{character} and statue=0")
    List<TASKINFO> showTaskBystatue(@Param("character")String character);

    //我的任务
    @Select("select * from TASKINFO where people=#{people} and peopletype=#{peopletype} and statue=#{statue}")
    List<TASKINFO> myTask(@Param("people")String people,@Param("peopletype")String peopletype,@Param("statue")String statue);


    //暂停任务
    @Update("update TASKINFO set statue=4 where id=#{id,jdbcType=VARCHAR}")
    void pauseTask(@Param("id")String id);


    //任务接取（主动接取）
    @Update("update TASKINFO set people=#{people,jdbcType=VARCHAR},peopletype=#{peopletype,jdbcType=VARCHAR},statue=1 where id=#{id,jdbcType=VARCHAR}")
    void receiveTask(@Param("id")String id,@Param("people")String people,@Param("peopletype")String peopletype);


    //删除任务(user)
    @Update("update TASKINFO set people=null,peopletype=null,statue=0 where id=#{id,jdbcType=VARCHAR}")
    void deleteTask(@Param("id")String id,@Param("people")String people);


    //巡检待审核(admin)
    @Select("select * from TASKINFO where statue=2 and character='巡检任务'")
    List<TASKINFO> XunjianDaishenhe();


    //根据type和character查询任务
    @Select("select * from TASKINFO where type=#{type} and character=#{character}")
    List<TASKINFO> selectBoth(@Param("type")String type,@Param("character")String character);


    //根据养护id从养护信息表获取经纬度
    @Select("select lng,lat from YANGHUINFO where yanghuid=#{yanghuid}")
    Map<String,String> seleLocaByyanghuid(@Param("yanghuid")String yanghuid);

    //获取养护任务
    @Select("select * from TASKINFO where character=#{character} and statue=0")
    List<Map<String ,String>> getYHtask(@Param("character")String character);


    //管理人员修改任务表里养护任务的信息
    @Update("update TASKINFO set content=#{content},endtime=#{etime} where id=#{id,jdbcType=VARCHAR}")
    void updateyhtask(@Param("id")String id,@Param("content")String content,@Param("etime")String etime);


    //把任务从任务库中删除
    @Delete("delete from TASKINFO where id=#{id}")
    void deleteyhtask(@Param("id")String id);


}
