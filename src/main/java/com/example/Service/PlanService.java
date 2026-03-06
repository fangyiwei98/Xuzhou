package com.example.Service;

import com.example.POJO.PLAN;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlanService {
    //制定新计划
    void saveNewplan(String id, String type, String area, String name, String content,String counts,String admin, String time, String stime, String etime);

    //查询计划
    List<PLAN> showPlan();

    //修改计划
    void updatePlan(String id,String type,String area,String name,String content,String counts,String admin,String stime,String etime,String time);

    //删除计划
    void deletePlanById(String id);
}
