package com.example.Service;

import com.example.POJO.PROBLEM;

import java.util.List;

public interface ProblemService {
    //查询问题上报信息
    List<PROBLEM> showProblem();

    //存入问题上报信息
    void saveProblem(String id,String people,String lng,String lat,String time,String content,String type,String picutreurl,String taskcode,String dangerlevel,String num,String area);

    //查询上报记录
    List<PROBLEM> getProblemBypeople(String people);

    //上报结果返回
    void updateProblem(String id,String returnresult);

    //根据任务号查问题
    List<PROBLEM> getProBytaskcode(String taskcode);
}
