package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.PLAN;
import com.example.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Plan")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class PlanController {

    @Autowired
    private PlanService planService;

    //添加新计划
    @RequestMapping(value = "/addplan" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject taskXiafa(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {

            String id= UUID.randomUUID().toString();
            String type= json_parameter.getString("type");
            String area=json_parameter.getString("area");
            String name=json_parameter.getString("name");
            String content= json_parameter.getString("content");
            String counts= json_parameter.getString("counts");
            String admin= json_parameter.getString("admin");
            String time= json_parameter.getString("time");
            String stime= json_parameter.getString("stime");
            String etime= json_parameter.getString("etime");
            planService.saveNewplan(id,type,area,name,content,counts,admin,time,stime,etime);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }



    //查询所有的任务信息
    @RequestMapping(value = "/showplan" , method = RequestMethod.POST)
    @ResponseBody
    public List<PLAN> showPlan(){
        JSONObject obj=new JSONObject();
        return planService.showPlan();
    }

    //修改计划
    @RequestMapping(value = "/updateplan" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateplan(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {

            String id= json_parameter.getString("id");
            String type= json_parameter.getString("type");
            String area=json_parameter.getString("area");
            String name=json_parameter.getString("name");
            String content= json_parameter.getString("content");
            String counts= json_parameter.getString("counts");
            String admin= json_parameter.getString("admin");
            String time= json_parameter.getString("time");
            String stime= json_parameter.getString("stime");
            String etime= json_parameter.getString("etime");
            planService.updatePlan(id,type,area,name,content,counts,admin,stime,etime,time);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //删除计划
    @RequestMapping(value = "/deleteplan" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteplan(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {
            String id= json_parameter.getString("id");
            planService.deletePlanById(id);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

}
