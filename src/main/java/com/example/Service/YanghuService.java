package com.example.Service;

import com.example.POJO.YANGHUINFO;

import java.util.List;

public interface YanghuService {
    //查询与定位养护信息
    List<YANGHUINFO> getYanghuInfo();

    //修改养护信息
    void updateYanghuInfo(String id,String content,String etime);

    //删除养护信息
    void deleteYanghuInfoById(String id);


    void deleteYanghuInfoByyanghuId(String yanghuid);


    //养护结果上报(修改养护信息表,id是任务id)
    void reportYanghu(String id,String people,String objectid,String time,String pictureurl,String numlater);

    //养护结果上报（修改任务信息表,id是任务id）
    void updateYanghuTask(String id);

    //养护记录
    List<YANGHUINFO> getYanghurecord(String people);

    //养护人员接取任务，更新养护信息表
    void updateYanghupeople(String id,String people);

    //养护人员删除任务，更新养护信息表
    void updateYanghudelete(String id,String people);

    //管理员评分
    void updateScore(String id,String socre);

    //根据养护任务id查询养护前的图片(user)
    List<YANGHUINFO> getYHpic(String yanghuid);

    //获取未签收的养护任务
    List<YANGHUINFO> getYhtask();
}
