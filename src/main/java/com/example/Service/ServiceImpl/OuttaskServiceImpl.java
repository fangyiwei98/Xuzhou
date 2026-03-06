package com.example.Service.ServiceImpl;

import com.example.Dao.OutTaskMapper;
import com.example.Dao.UserMapper;
import com.example.POJO.LINK;
import com.example.POJO.OUTTASK;
import com.example.POJO.fengong;
import com.example.Service.OuttaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OuttaskServiceImpl implements OuttaskService {

    @Autowired
    private OutTaskMapper outTaskMapper;
    @Autowired
    private UserMapper userMapper;


    //查询外来任务(admin)
    @Override
    public List<OUTTASK> getOuttask() {
        return outTaskMapper.getOuttask();
    }

    //根据路段获取未接收的外来任务(user)
    @Override
    public List<OUTTASK> getOuttaskuser(String road) {
        return outTaskMapper.getOuttaskuser(road);
    }

    //根据roadid查询道路名称
    @Override
    public String getroadname(String roadid) {
        return outTaskMapper.getroadname(roadid);
    }

//    //搜索外来任务(巡检人员)
//    @Override
//    public List<OUTTASK> selectOutTaskByroad(String road) {
//        return outTaskMapper.selectOutTaskByroad(road);
//    }

//    //搜索外来任务(养护人员)
//    @Override
//    public List<OUTTASK> selectOutTaskByroadYH(String road) {
//        return outTaskMapper.selectOutTaskByroadYH(road);
//    }

    //接取外来任务(user)
    @Override
    public void receiveOutTask(String id, String people,String tel) {
        outTaskMapper.receiveTask(id, people,tel);
    }

    //完成外来任务(user)
    @Override
    public void finishoutTask(String id, String result,String numfinish,String reporttime,String belong,String returnword) {
        outTaskMapper.finishoutTask(id, result,numfinish,reporttime,belong,returnword);
    }

    //获取已接收的外来任务(user)
    @Override
    public List<OUTTASK> getMyouttask(String people) {
        return outTaskMapper.getMyouttask(people);
    }

    //历史外来任务(user)
    @Override
    public List<OUTTASK> getHisouttask(String people) {
        return outTaskMapper.getHisouttask(people);
    }

    //审核外来任务(admin)
    @Override
    public void shenheoutTask(String id) {
        outTaskMapper.shenheoutTask(id);
    }

    @Override
    public void deleteoutTask(String id) {
        outTaskMapper.deleteoutTask(id);
    }

    //管理员删除外来任务
    @Override
    public void deleteouttaskById(String id) {
        outTaskMapper.deleteouttaskById(id);
    }

    //根据用户名查询负责路段
    @Override
    public List<LINK> getroadbyname(String name) {
        return userMapper.getroad(name);
    }
}
