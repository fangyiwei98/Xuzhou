package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Dao.MessageMapper;
import com.example.POJO.MESSAGE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping("/Message")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class MessageController {

    @Autowired
    private MessageMapper messageMapper;

    //插入消息
    @RequestMapping(value = "/saveMessage" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveMessage(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id= UUID.randomUUID().toString();
            String content= json_parameter.getString("content");
            String name= json_parameter.getString("name");
            String type= json_parameter.getString("type");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String now=df.format(new Date());
            messageMapper.saveNewmessage(id,content,name,type,now);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

    //查看消息
    @RequestMapping(value = "/read" , method = RequestMethod.POST)
    @ResponseBody
    public List<MESSAGE> showMessage(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);
        System.out.println("str:"+str);
        String name=json_parameter.getString("name");
        String type=json_parameter.getString("type");
        System.out.println(name+"^^^^^^^^^^"+type);

        return messageMapper.getMessage(name,type);
    }


    //修改消息状态
    @RequestMapping(value = "/updatestatue" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateMessage(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            System.out.println(id);
            messageMapper.updateMessage(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }



    //删除消息
    @RequestMapping(value = "/deletemessage" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteMessage(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            System.out.println(id);
            messageMapper.deleteMessage(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //获取未读消息数量
    @RequestMapping(value = "/getNum" , method = RequestMethod.POST)
    @ResponseBody
    public Integer getNum(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);
        String name=json_parameter.getString("name");
        String type=json_parameter.getString("type");
        //System.out.println(name+"^^^^^^^^^^"+type);

        return messageMapper.getNum(name,type);
    }
}
