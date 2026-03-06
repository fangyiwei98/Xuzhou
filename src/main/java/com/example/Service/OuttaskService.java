package com.example.Service;

import com.example.POJO.LINK;
import com.example.POJO.OUTTASK;
import com.example.POJO.fengong;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OuttaskService {


    //查询外来任务(user)
    List<OUTTASK> getOuttask();

    //获取未接收的外来任务(user)
    List<OUTTASK> getOuttaskuser(String road);

    //根据roadid查询道路名称
    String getroadname(String roadid);

    //搜索外来任务（巡检人员）
    //List<OUTTASK> selectOutTaskByroad(String road);

    //搜索外来任务（养护人员）
    //List<OUTTASK> selectOutTaskByroadYH(String road);

    //接取外来任务(user)
    void receiveOutTask(String id,String people,String tel);

    //完成外来任务(user)
    void finishoutTask(String id,String result,String numfinish,String reporttime,String belong,String returnword);

    //获取已接受的外来任务(user)
    List<OUTTASK> getMyouttask(String people);

    //历史外来任务(user)
    List<OUTTASK> getHisouttask(String people);

    //外来任务审核(admin)
    void shenheoutTask(String id);

    //删除外来任务(user)
    void deleteoutTask(String id);


    //管理员删除外来任务(admin)
    void deleteouttaskById(String id);

    //根据用户名查询负责路段
    List<LINK> getroadbyname(String name);


}
