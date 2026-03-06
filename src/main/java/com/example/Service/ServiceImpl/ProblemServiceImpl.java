package com.example.Service.ServiceImpl;

import com.example.Dao.PlanMapper;
import com.example.Dao.ProblemMapper;
import com.example.Dao.YanghuMapper;
import com.example.POJO.PROBLEM;
import com.example.Service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;

    @Autowired
    private YanghuMapper yanghuMapper;

    //上报问题展示
    @Override
    public List<PROBLEM> showProblem() {
        return problemMapper.getProblem();
    }

    //问题上报
    @Override
    public void saveProblem(String id, String people, String lng, String lat, String time, String content, String type, String picutreurl,String taskcode,String dangerlevel,String num,String area) {
        //System.out.println("执行到问题上报Service了！");
        //将信息插入到问题表
        problemMapper.saveProblem(id, people, lng, lat,time, content,type, picutreurl,taskcode,dangerlevel,num,area);
        //将信息插入到养护信息表
        yanghuMapper.insertYanghuInfoProblem(id,lng,lat,picutreurl,taskcode,num,time);
        //System.out.println("问题上报Service执行完成了！");

    }

    //问题上报记录
    @Override
    public List<PROBLEM> getProblemBypeople(String people) {
        return problemMapper.getProblemBypeople(people);
    }

    @Override
    public void updateProblem(String id, String returnresult) {
        problemMapper.updateProblem(id, returnresult);
    }

    //根据任务号查问题
    @Override
    public List<PROBLEM> getProBytaskcode(String taskcode) {
        return problemMapper.getProBytaskcode(taskcode);
    }
}
