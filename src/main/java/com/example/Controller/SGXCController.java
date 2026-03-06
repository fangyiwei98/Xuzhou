package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.SGXC;
import com.example.POJO.SGXCUSER;
import com.example.Service.SGXCService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/SGXC")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class SGXCController {
    @Value("${file.sgxcadmin}")
    private String sgxcadminpath;
    @Value("${file.sgxcuser}")
    private String sgxcuserpath;

    @Autowired
    private SGXCService sgxcService;


    //插入施工现场(admin)
    @RequestMapping(value = "/createSGXC" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject createSGXC(@RequestParam("data") String json,@RequestParam("pictures") MultipartFile[] files) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(json);
        String id= UUID.randomUUID().toString();
        String name= json_parameter.getString("name");
        String road= json_parameter.getString("road");
        String facilities=json_parameter.getString("facilities");
        String gcname = json_parameter.getString("gcname");
        String jianshe= json_parameter.getString("jianshe");
        String shigong= json_parameter.getString("shigong");
        String time=json_parameter.getString("time");
        String content = json_parameter.getString("content");
        String location=json_parameter.getString("location");
        //String numadmin=json_parameter.getString("numadmin");

        System.out.println("id:"+id);
        System.out.println("files.length:"+files.length);


        try {

            for (int i = 0; i < files.length; i++) {

                System.out.println("文件名称" + files[i].getOriginalFilename());
                String filename=gcname+i+".jpg";
                File filepath = new File(sgxcadminpath, filename);
                System.out.println("路径名："+filepath);
                System.out.println("随机数文件名称" + filepath.getName());
                //判断路径是否存在，没有就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                files[i].transferTo(filepath);
            }
            String numadmin=Integer.toString(files.length);

            sgxcService.saveSGXC(id,name,road,facilities,gcname,jianshe,shigong,time,content,location,sgxcadminpath,numadmin);

            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //施工现场上报(user)
    @RequestMapping(value = "/reportSGXC" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject reportSGXC( HttpServletRequest request) throws IOException {

        JSONObject obj=new JSONObject();


        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pictureurl");



        String id= UUID.randomUUID().toString();
        String name= request.getParameter("name");
        String time= request.getParameter("time");
        String num=request.getParameter("num");
        String gcname = request.getParameter("gcname");

        String transtime=request.getParameter("transtime");

        //String path = "D://uploadfile";

        MultipartFile file = null;

        try {


            for (int i = 0; i < files.size(); i++) {
                file=files.get(i);
                //System.out.println("文件名称" + file.getOriginalFilename());
                //上传文件名
                String filename = gcname+transtime+i+".jpg";//上传文件的真实名称
                File filepath = new File(sgxcuserpath, filename);
                System.out.println("路径名："+filepath);
                System.out.println("随机数文件名称" + filepath.getName());
                //判断路径是否存在，没有就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                //file.transferTo(filepath);
            }

            sgxcService.saveSGXCUSER(id,name,sgxcuserpath,time,num,gcname);

            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }



    //获取所有施工现场(admin and user)
    @RequestMapping(value = "/getGcname" , method = RequestMethod.POST)
    @ResponseBody
    public List<SGXC> getGcname() throws IOException {
        return sgxcService.getGcname();
    }

    //根据工程名字获取照片(admin)
    @RequestMapping(value = "/getPicBygcname" , method = RequestMethod.POST)
    @ResponseBody
    public List<SGXCUSER> getPicBygcname(@RequestBody String str) throws IOException {

        JSONObject json_parameter = JSON.parseObject(str);

        String gcname = json_parameter.getString("gcname");
        return sgxcService.getPicBygcname(gcname);

    }

    //施工现场批量下载(admin)
    @RequestMapping(value = "/patchdownload" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject patchdownload(@RequestBody HashMap<String, List<String>> gcnames){

        String strl=gcnames.values().toString();
        System.out.println("strl:"+strl);
        String bbb=strl.substring(2,strl.length()-2);
        String[] aaa=bbb.split(", ");


        JSONObject obj=new JSONObject();

        for (int i = 0; i < aaa.length; i++) {
            obj.put(aaa[i],sgxcService.getPicBygcname(aaa[i]));

        }

        System.out.println("返回给前端的施工现场："+obj);
        return obj;

    }

}
