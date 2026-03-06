package com.example.Service.ServiceImpl;

import com.example.Dao.TaskMapper;
import com.example.Dao.YanghuMapper;
import com.example.POJO.YANGHUINFO;
import com.example.Service.YanghuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class YanghuServiceImpl implements YanghuService {
    @Autowired
    private YanghuMapper yanghuMapper;

    @Autowired
    private TaskMapper taskMapper;

    //查询与定位养护信息
    @Override
    public List<YANGHUINFO> getYanghuInfo() {
        return yanghuMapper.getYanghuInfo();
    }

    //修改养护信息
    @Override
    public void updateYanghuInfo(String id, String content, String etime) {
        yanghuMapper.updateYanghuInfo(id, content, etime);
    }



    //删除养护信息
    @Override
    public void deleteYanghuInfoById(String id) {
        yanghuMapper.deleteYanghuInfoById(id);
    }

    @Override
    public void deleteYanghuInfoByyanghuId(String yanghuid) {
        yanghuMapper.deleteYanghuInfoByyanghuId(yanghuid);
    }

    //养护结果上报(上报到养护信息表,id是任务id)
    @Override
    public void reportYanghu(String id,  String people, String objectid, String time, String pictureurl,String numlater) {
        yanghuMapper.reportYanghu(id,  people, objectid, time, pictureurl,numlater);

        taskMapper.finishXunjianTask(id);
    }

    //养护结果上报(修改任务信息表,id是任务id)
    @Override
    public void updateYanghuTask(String id) {
        yanghuMapper.updateYanghuTask(id);
    }

    //养护记录
    @Override
    public List<YANGHUINFO> getYanghurecord(String people) {
        return yanghuMapper.getYanghurecord(people);
    }

    @Override
    public void updateYanghupeople(String id, String people) {
        yanghuMapper.updateYanghupeople(id, people);
    }

    //养护人员删除任务，修改养护信息表里信息
    @Override
    public void updateYanghudelete(String id, String people) {
        yanghuMapper.updateYanghudelete(id, people);
    }

    @Override
    public void updateScore(String id, String socre) {
        yanghuMapper.updateScore(id, socre);
    }

    //根据养护任务id查询养护前的图片
    @Override
    public List<YANGHUINFO> getYHpic(String yanghuid) {
        return yanghuMapper.getYHpic(yanghuid);
    }

    @Override
    public List<YANGHUINFO> getYhtask() {
        return yanghuMapper.getYhtask();
    }


}
