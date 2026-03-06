package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.fengong;
import com.example.POJO.trajectoryWithBLOBs;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Info")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class InfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private com.example.Service.trajectoryService trajectoryService;


    //获取分工区域
    @RequestMapping(value = "/showArea" , method = RequestMethod.POST)
    @ResponseBody
    public List<fengong> showPlan(){
        return userService.getFengong();
    }



    //根据任务查询轨迹
    @RequestMapping(value = "realtrace")//, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public Object selectPathByTaskcode(@RequestBody String str){
        //String taskcode = request.getParameter("taskcode");
        JSONObject json_parameter = JSON.parseObject(str);
        String taskcode= json_parameter.getString("taskcode");
        //System.out.println("taskcode:"+taskcode);
        Map<String, String[]> resMap = new HashMap<String, String[]>();

        List<trajectoryWithBLOBs> res= (List<trajectoryWithBLOBs>) trajectoryService.selectPathByTaskcode(taskcode);

        byte[] ptx=res.get(0).getPathx();
        byte[] pty=res.get(0).getPathy();
       /* System.out.println("btx:" + ptx);
        System.out.println("pty:" + pty);*/

        String pathx=new String(ptx);
        String pathy=new String(pty);


        String[] lng=pathx.split(",");
        String[] lat=pathy.split(",");

/*
        System.out.println("lng:"+lng);
        System.out.println("lat:"+lat);


        System.out.println("pathx:" + pathx);
        System.out.println("pathy:" + pathy);*/
        resMap.put("pathx", lng);
        resMap.put("pathy", lat);

        //System.out.println("JSON.toJSONString(resMap):"+resMap);
        return JSON.toJSONString(resMap);


        //JSON.toJSONString(resMap)
    }


}
