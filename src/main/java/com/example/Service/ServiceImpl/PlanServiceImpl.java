package com.example.Service.ServiceImpl;

import com.example.Dao.PlanMapper;
import com.example.POJO.PLAN;
import com.example.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanMapper planMapper;

    //制定新计划
    @Override
    public void saveNewplan(String id, String type, String area, String name, String content, String counts, String admin, String time, String stime, String etime) {
        planMapper.saveNewplan(id, type, area, name, content, counts, admin, time, stime, etime);
    }

    //查看所有计划信息
    @Override
    public List<PLAN> showPlan() {
        return planMapper.showPlan();
    }

    //修改计划
    @Override
    public void updatePlan(String id, String type, String area, String name, String content, String counts, String admin, String stime, String etime, String time) {
        planMapper.updatePlan(id, type, area, name, content, counts, admin, stime, etime, time);
    }

    //删除计划
    @Override
    public void deletePlanById(String id) {
        planMapper.deletePlanById(id);
    }


}
