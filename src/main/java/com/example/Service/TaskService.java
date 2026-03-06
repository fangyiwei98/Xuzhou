package com.example.Service;

import com.example.POJO.TASKINFO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TaskService {

    //发布任务
    void paifaTask(String id,String area,String type, String character,String content, String admin, String time, String endtime);


    //放到养护信息表的
    void insertYanghuInfo(String id,String area,String type, String content, String time,String endtime,String yanghuid);

    //派发任务
    void saveSpecialTask(String id,String area,String type, String character,String content, String admin, String time, String endtime,String people,String peopletype);


    //查询所有的任务信息
    List<TASKINFO> showTask();

    //管理员审核通过任务状态（任务表里）
    void finishTask(String id);

    //管理员驳回任务
    void bohuiTask(String id);

    //管理员通过养护任务（养护信息表里）
    void finishYanghuTask(String id);

    //管理员驳回养护任务
    void bohuiYanghuTask(String id);


    //巡检人员提交将任务改为待审核
    void finishXunjianTask(String id);


    //根据类型查询任务信息(admin)
    List<TASKINFO> showTaskBytype(String type);

    //根据类型查询任务信息(user)
    List<TASKINFO> showTaskBystatue(String character);



    //我的任务
    List<TASKINFO> mytask(String people,String peopletype,String statue);

    //暂停任务(user)
    void pauseTask(String id);

    //任务接取（主动接取）
    void jiequTask(String id,String people,String peopletype);

    //删除任务
    void deleteTask(String id,String people);

    //巡检待审核(admin)
    List<TASKINFO> XunjianDaishenhe();


    //根据type和character查询任务(admin)
    List<TASKINFO> selectBoth(String type,String character);

    //根据养护id从养护信息表获取经纬度
    Map<String,String> seleLocaByyanghuid(String yanghuid);

    //获取养护任务
    List<Map<String ,String>> getYHtask(String character);

    //管理人员修改任务表里养护任务的信息
    void updateyhtask(String id,String content,String etime);

    //把任务从任务库里删除
    void deleteyhtask(String id);

}
