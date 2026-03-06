package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.PROBLEM;

import com.example.Service.MessageService;
import com.example.Service.ProblemService;
import com.example.Service.TaskService;
import com.example.Service.YanghuService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Problem")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class ProblemController {

    @Value("${file.problemimg}")
    private String problemimgpath;


    @Autowired
    private ProblemService problemService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private YanghuService yanghuService;

    //查询所有的问题上报信息（admin）
    @RequestMapping(value = "/showproblem" , method = RequestMethod.POST)
    @ResponseBody
    public List<PROBLEM> showProblem(){
        return problemService.showProblem();
    }


    //上报问题(user,上报到Problem表,其中部分信息上传到养护信息表)
    @RequestMapping(value = "/reportproblem" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addMediaAction(HttpServletRequest request) {
        JSONObject obj=new JSONObject();

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pictureurl");
        String id= UUID.randomUUID().toString();
        String name=request.getParameter("name");
        String people=request.getParameter("people");//上报人
        String lng = request.getParameter("lng");
        String lat = request.getParameter("lat");
        String time = request.getParameter("time");
        String content = request.getParameter("content");
        String type=request.getParameter("type");
        String taskcode = request.getParameter("taskcode");//巡检任务号
        String dangerlevel = request.getParameter("dangerlevel");
        String num = request.getParameter("num");
        String area=request.getParameter("area");


        System.out.println("日常上报的参数："+request.toString());
        MultipartFile file = null;

        try {
                for (int i = 0; i < files.size(); i++) {
                    file=files.get(i);
                        System.out.println("文件名称" + file.getOriginalFilename());
                        //上传文件名
                        String filename = file.getOriginalFilename();//上传文件的真实名称
                        File filepath = new File(problemimgpath, filename);
                        System.out.println("路径名："+filepath);
                        System.out.println("随机数文件名称" + filepath.getName());
                        //判断路径是否存在，没有就创建一个
                        if (!filepath.getParentFile().exists()) {
                            filepath.getParentFile().mkdirs();
                        }
                        Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                        //file.transferTo(filepath);
                }
            problemService.saveProblem(id,people,lng,lat,time,content,type,problemimgpath,taskcode,dangerlevel,num,area);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            obj.put("code",0);
            return obj;
        }

    }


    //问题上报记录(user)
    @RequestMapping(value = "/record" , method = RequestMethod.POST)
    @ResponseBody
    public List<PROBLEM> getProblemBypeople(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);

        String people=json_parameter.getString("people");

        //System.out.println(people);
        return problemService.getProblemBypeople(people);
    }


    //问题上报结果返回(admin)
    @RequestMapping(value = "/returnproblem" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateMessage(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            String msgid=UUID.randomUUID().toString();
            //String returnresult=json_parameter.getString("returnresult");
            String name=json_parameter.getString("name");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String now=df.format(new Date());
            //将状态修改为1
            problemService.updateProblem(id,"1");

            //根据问题id删除养护消息
            yanghuService.deleteYanghuInfoById(id);

            //给巡检人员发消息
            messageService.saveNewmessage(msgid,"您上报的问题已处理！问题号是:"+id,name,"巡检人员",now);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //根据任务号查询问题(admin)
    @RequestMapping(value = "/getProBytaskcode" , method = RequestMethod.POST)
    @ResponseBody
    public List<PROBLEM> getProBytaskcode(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);

        String taskcode=json_parameter.getString("taskcode");

        //System.out.println(people);
        return problemService.getProBytaskcode(taskcode);
    }


}
