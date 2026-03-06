package com.example.Service.ServiceImpl;

import com.example.Dao.TaskMapper;
import com.example.Dao.YanghuMapper;
import com.example.POJO.TASKINFO;
import com.example.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private YanghuMapper yanghuMapper;

    //发布任务
    @Override
    public void paifaTask(String id, String area, String type, String character,String content, String admin, String time, String endtime) {
        taskMapper.paifaTask(id, area,type, character,content, admin, time, endtime);
        /*if(character.equals("养护任务")){
            yanghuMapper.insertYanghuInfo(id, area, type, content, time, endtime);
        }*/

    }

    //放到养护信息表的
    @Override
    public void insertYanghuInfo(String id, String area, String type, String content, String time, String endtime,String yanghuid) {
        yanghuMapper.insertYanghuInfo(id, area, type, content, time, endtime,yanghuid);
    }


    //派发任务
    @Override
    public void saveSpecialTask(String id, String area,String type, String character,String content, String admin, String time, String endtime, String people, String peopletype) {
        taskMapper.saveSpecialTask(id, area,type, character,content, admin, time, endtime, people, peopletype);
    }


    //查询所有的任务信息
    @Override
    public List<TASKINFO> showTask() {
        return taskMapper.showTask();
    }

    //管理员审核通过任务状态（任务表里）
    @Override
    public void finishTask(String id) {
        taskMapper.finishTask(id);
    }

    //管理员驳回任务
    @Override
    public void bohuiTask(String id) {
        taskMapper.bohuiTask(id);
    }

    //管理员通过养护任务（养护信息表里）
    @Override
    public void finishYanghuTask(String id) {
        taskMapper.finishYanghuTask(id);
    }

    //驳回养护任务
    @Override
    public void bohuiYanghuTask(String id) {
        taskMapper.bohuiYanghuTask(id);
    }

    //巡检人员提交将任务状态改为待审核
    @Override
    public void finishXunjianTask(String id) {
        taskMapper.finishXunjianTask(id);
    }


    //根据类型查询任务信息(admin)
    @Override
    public List<TASKINFO> showTaskBytype(String type) {
        return taskMapper.showTaskBytype(type);
    }

    //根据类型查询任务信息(user)
    @Override
    public List<TASKINFO> showTaskBystatue(String character) {
        return taskMapper.showTaskBystatue(character);
    }

    //我的任务(user)
    @Override
    public List<TASKINFO> mytask(String people, String peopletype,String statue) {
        return taskMapper.myTask(people,peopletype,statue);
    }

    //暂停任务(user)
    @Override
    public void pauseTask(String id) {
        taskMapper.pauseTask(id);
    }

    //接取任务(user)
    @Override
    public void jiequTask(String id, String people,String peopletype) {
        taskMapper.receiveTask(id, people,peopletype);

        if(peopletype.equals("养护人员")){
            yanghuMapper.updateYanghupeople(id, people);
        }

    }

    //删除任务(user)
    @Override
    public void deleteTask(String id, String people) {
        taskMapper.deleteTask(id, people);
    }

    //巡检待审核(admin)
    @Override
    public List<TASKINFO> XunjianDaishenhe() {
        return taskMapper.XunjianDaishenhe();
    }

    //根据type和character查询任务
    @Override
    public List<TASKINFO> selectBoth(String type, String character) {
        return taskMapper.selectBoth(type, character);
    }

    //根据养护id从养护信息表获取经纬度
    @Override
    public Map<String, String> seleLocaByyanghuid(String yanghuid) {
        return taskMapper.seleLocaByyanghuid(yanghuid);
    }

    //获取养护任务
    @Override
    public List<Map<String, String>> getYHtask(String character) {
        return taskMapper.getYHtask(character);
    }

    //管理人员修改任务表里养护任务的信息
    @Override
    public void updateyhtask(String id, String content, String etime) {
        taskMapper.updateyhtask(id,content,etime);
    }

    //把任务从任务库里删除
    @Override
    public void deleteyhtask(String id) {
        taskMapper.deleteyhtask(id);
    }
}
