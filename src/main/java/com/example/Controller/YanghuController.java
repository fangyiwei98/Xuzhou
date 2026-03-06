package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.YANGHUINFO;
import com.example.Service.TaskService;
import com.example.Service.YanghuService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/Yanghu")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class YanghuController {

    @Value("${file.yanghuimg}")
    private String yanghuimgpath;

    @Autowired
    private YanghuService yanghuService;

    @Autowired
    private TaskService taskService;
    //查询与定位养护信息(admin)
    @RequestMapping(value = "/selectandposition" , method = RequestMethod.POST)
    @ResponseBody
    public List<YANGHUINFO> getYanghuInfo(){
        return yanghuService.getYanghuInfo();
    }
    //修改养护信息(admin)
    @RequestMapping(value = "/updateinfo" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateYanghuInfo(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        System.out.println("后端获取到的养护信息是："+str);
        try {
            String id=json_parameter.getString("yanghuid");
           /* String singlenum= json_parameter.getString("singlenum");
            String objectid=json_parameter.getString("objectid");
            String name=json_parameter.getString("name");
            String company= json_parameter.getString("company");*/
            String content= json_parameter.getString("content");
            //String statue= json_parameter.getString("statue");
            //String stime=json_parameter.getString("stime");
            String etime=json_parameter.getString("etime");

            /*String lng= json_parameter.getString("lng");
            String lat =json_parameter.getString("lat");*/
            //String remark=json_parameter.getString("remark");
            //System.out.println(id+"aaa"+singlenum+"aaa"+company+"aaa"+content+"aaa"+statue+"aaa"+lng+"aaa"+lat+"aaa"+remark);
            yanghuService.updateYanghuInfo(id,content,etime);

            taskService.updateyhtask(id,content,etime);




            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

    //删除养护信息(admin，根据养护id删除)
    @RequestMapping(value = "/deleteinfo" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteYanghuInfoById(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id= json_parameter.getString("id");
            //System.out.println("获取到的养护id是:"+id);
            //yanghuService.deleteYanghuInfoById(id);
            yanghuService.deleteYanghuInfoByyanghuId(id);
            taskService.deleteyhtask(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //养护结果上报(user)
    @RequestMapping(value = "/problem" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reportYanghu(HttpServletRequest request,
                                   @RequestParam(value = "pictureurl", required = false) MultipartFile[] Pics)throws IOException {
            JSONObject obj=new JSONObject();

            String id= request.getParameter("taskcode");
            String name=request.getParameter("name");
            String people=request.getParameter("people");
            String objectid=request.getParameter("objectid");
            String time = request.getParameter("time");
            String numlater=request.getParameter("num");




            int n = Pics.length;
            try {
                //String path = "D://uploadfile";
                for (int i = 0; i < n; i++) {

                    System.out.println("文件名称" + Pics[i].getOriginalFilename());
                    //上传文件名
                    String filename = Pics[i].getOriginalFilename();//上传文件的真实名称
                    String suffixName = filename.substring(filename.lastIndexOf("."));//获取后缀名

                    File filepath = new File(yanghuimgpath, filename);
                    System.out.println("随机数文件名称" + filepath.getName());
                    //判断路径是否存在，没有就创建一个
                    if (!filepath.getParentFile().exists()) {
                        filepath.getParentFile().mkdirs();
                    }
                    //将上传文件保存到一个目标文档中
                    File tempFile = new File(yanghuimgpath + File.separator + filename);
                    Thumbnails.of(Pics[i].getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(tempFile);

                    //Pics[i].transferTo(tempFile);
                }

                //养护结果上报（上报到养护信息表,id是任务id，状态改为待审核）
                yanghuService.reportYanghu(id,people,objectid,time,yanghuimgpath,numlater);
                //养护结果上报(修改任务信息表,id是任务id,statue改为2)
                yanghuService.updateYanghuTask(id);

                obj.put("code",1);
                return obj;
            } catch (Exception e1) {
                obj.put("code",0);
                return obj;
            }



    }


    //养护记录(user)
    @RequestMapping(value = "/record" , method = RequestMethod.POST)
    @ResponseBody
    public List<YANGHUINFO> getYanghuInfo(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);

        String people=json_parameter.getString("people");
        return yanghuService.getYanghurecord(people);
    }


    //管理员评分(admin)
    @RequestMapping(value = "/score" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getScore(@RequestBody String str){
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");
        String score=json_parameter.getString("score");
        try {
            yanghuService.updateScore(id,score);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

    //根据养护任务id查询养护前的图片(user)
    @RequestMapping(value = "/getYHpic" , method = RequestMethod.POST)
    @ResponseBody
    public List<YANGHUINFO> getYHpic(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);

        String yanghuid=json_parameter.getString("yanghuid");
        return yanghuService.getYHpic(yanghuid);
    }

}
