package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Dao.UserMapper;
import com.example.POJO.LINK;
import com.example.POJO.USERINFO;
import com.example.POJO.fengong;
import com.example.POJO.roads;
import com.example.Service.OuttaskService;
import com.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.util.DigestUtils;

@RestController
@RequestMapping("/SVIP")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class SVIPController {
    @Autowired
    private UserService userService;
    @Autowired
    private OuttaskService outtaskService;

    @Autowired
    private UserMapper userMapper;

    //超级管理员注册用户和管理员
    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject register(@RequestBody Map aaa) throws IOException {
        JSONObject obj=new JSONObject();
        String data= (String) aaa.get("data");
        //获取道路列表
        List<roads> roadslist= (List<roads>) aaa.get("roads");
        JSONObject json_parameter = JSON.parseObject(data);

        String id= UUID.randomUUID().toString();
        String name= json_parameter.getString("name");
        String pwd= json_parameter.getString("pwd");
        String role=json_parameter.getString("role");
        String tel=json_parameter.getString("tel");
        System.out.println("注册的用户名："+name);
        System.out.println("密码是："+pwd);

        //该用户名已注册
        for (int i = 0; i < userService.getUserInfo().size(); i++) {
            if (name .equals(userService.getUserInfo().get(i).getName())) {
                obj.put("code",2);
                return obj;
            }
        }

        String md5Password = DigestUtils.md5DigestAsHex(pwd.getBytes());
        //该用户名未被注册
        try {
            //是巡检人员（关联道路和起止点，外来任务）
            if(role.equals("巡检人员")){
                //注册巡检人员时没有注册道路和起止点
                if(roadslist.size()==0){
                    //直接注册
                    userService.registerUser(id,name,md5Password,role,tel);
                }else {
                    //注册巡检人员
                    userService.registerUser(id,name,md5Password,role,tel);
                    //这边只能这样写
                    for (int i=0;i<roadslist.size();i++) {
                        // 将list中的数据转成json字符串
                        String jsonObject = JSON.toJSONString(roadslist.get(i));
                        //将json转成需要的对象
                        roads roadsnow = JSONObject.parseObject(jsonObject, roads.class);

                        String road = roadsnow.getRoad();
                        String qizhi = roadsnow.getQizhi();
                        //关联路段信息
                        //首先判断该路段和起止点是否存在(不存在)
                        if (userService.getdaoluidbyroadandqizhi(road, qizhi) == null) {
                            //往道路表里插入数据
                            String daoluid = UUID.randomUUID().toString();
                            userService.insertroad(daoluid, road, qizhi);
                            //往关联表里插入数据
                            String linkid = UUID.randomUUID().toString();
                            userService.insertintolink(linkid, name, daoluid);
                        } else {
                            //道路表里有该路段和起止点的数据
                            //根据道路和起止点获取daoluid
                            String daoluid = userService.getdaoluidbyroadandqizhi(road, qizhi);
                            //用daoluid和name更新link表
                            userService.updatelinkbydaoluidandname(name, daoluid);
                        }
                    }

                }
                //因为注册的时候路段和起止点是必选的，所以这边需要加上不明确路段
                //在所有操作结束后加一个不明确路段的选项，并和该巡检人员关联起来
                String specialid="unknownroadId";
                String speciallinkid=UUID.randomUUID().toString();
                //先要判断不明确路段是否存在
                if (userMapper.getidbygouguan("不明确路段","不明确路段起止点").equals("")) {
                    //不存在
                    userMapper.insertfengong(specialid,"不明确路段","不明确路段起止点");
                    //查询所有巡检人员的名字并和link表关联起来
                    userMapper.insertLink(speciallinkid,name,specialid);
                }else {
                    //存在
                    //查询所有巡检人员的名字并和link表关联起来
                    userMapper.insertLink(speciallinkid,name,specialid);
                }


            }else {
                //不是巡检人员直接注册用户
                userService.registerUser(id,name,md5Password,role,tel);
            }
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //超级管理员修改用户和管理员
    @RequestMapping(value = "/updateUser" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateUser(@RequestBody Map aaa) throws IOException {
        JSONObject obj=new JSONObject();
        String data= (String) aaa.get("data");
        //获取道路列表
        List<roads> roadslist= (List<roads>) aaa.get("roads");
        JSONObject json_parameter = JSON.parseObject(data);

        String id= json_parameter.getString("id");
        String name= json_parameter.getString("name");
        String role=json_parameter.getString("role");
        String tel=json_parameter.getString("tel");

        try {
            if(role.equals("巡检人员")){

                //1.先根据用户名查询link表里的道路id(这里删除了不明确路段)
                List<LINK> linkInfo=userMapper.getroad(name);
                //2.根据道路id删除fengong表里的数据
                for (int i = 0; i < linkInfo.size(); i++) {
                    //遍历link表里每一个对象
                    String roadid=linkInfo.get(i).getRoadid();
                    //根据获取到的roadid删除fengong表里的数据
                    userMapper.delroadByid(roadid);
                }
                //更新用户表里的信息
                userMapper.updatePeopleById(id,name,role,tel);

                //先把该用户的所有路段信息删掉
                userService.deletelinkByname(name);

                //这边只能这样写
                for (int i=0;i<roadslist.size();i++) {
                    System.out.println("调了修改接口,获取到的参数是："+roadslist.get(i));
                    // 将list中的数据转成json字符串
                    String jsonObject = JSON.toJSONString(roadslist.get(i));
                    //将json转成需要的对象
                    roads roadsnow = JSONObject.parseObject(jsonObject, roads.class);
                    String road = roadsnow.getRoad();
                    String qizhi = roadsnow.getQizhi();
                    //System.out.println("获取转换后的对象是：道路："+road+"起止点："+qizhi);

                    //首先判断该路段和起止点是否存在(不存在)
                    if (userService.getdaoluidbyroadandqizhi(road, qizhi) == null) {
                        //往道路表里插入数据
                        String daoluid = UUID.randomUUID().toString();
                        userService.insertroad(daoluid, road, qizhi);
                        //往关联表里插入数据
                        String linkid = UUID.randomUUID().toString();
                        userService.insertintolink(linkid, name, daoluid);
                    } else {
                        //道路表里有该路段和起止点的数据
                        //根据道路和起止点获取daoluid
                        String daoluid = userService.getdaoluidbyroadandqizhi(road, qizhi);
                        //用daoluid和name更新link表
                        //userService.updatelinkbydaoluidandname(name, daoluid);
                        String linkid = UUID.randomUUID().toString();
                        userService.insertintolink(linkid,name,daoluid);
                    }
                }
                //改完之后需要插入不明确路段的信息
                //4.在所有操作结束后加一个不明确路段的选项，并和该巡检人员关联起来
                String specialid="unknownroadId";
                String speciallinkid=UUID.randomUUID().toString();
                userMapper.insertfengong(specialid,"不明确路段","不明确路段起止点");
                //查询所有巡检人员的名字并和link表关联起来
                userMapper.insertLink(speciallinkid,name,specialid);

            }else {
                userMapper.updatePeopleById(id,name,role,tel);
            }
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //超级管理员删除用户和管理员
    @RequestMapping(value = "/deleteUser" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteUser(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id= json_parameter.getString("id");
            userService.deletePeopleById(id);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //超级管理员查询所有用户和管理员
    @RequestMapping(value = "/selectUser" , method = RequestMethod.POST)
    @ResponseBody
    public List<USERINFO> selectUser() throws IOException {
        return userService.getUserInfo();
    }
    //超级管理员查询巡检人员路段
    @RequestMapping(value = "/selectroad" , method = RequestMethod.POST)
    @ResponseBody
    public List<fengong> selectroad(@RequestBody String str) throws IOException {
        JSONObject json_parameter = JSON.parseObject(str);
        String name=json_parameter.getString("name");
        List<LINK> aaa=outtaskService.getroadbyname(name);
        List<fengong> result=new ArrayList<>();

        for (LINK link : aaa) {
            String roadid = link.getRoadid();
            result.addAll(userService.getroadbyroadid(roadid));
        }
        return result;
    }


    //超级管理员重置密码
    @RequestMapping(value = "/resetpwd" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject resetpwd(@RequestBody String str) throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String name= json_parameter.getString("name");
            userService.resetPwd(name,DigestUtils.md5DigestAsHex("123456".getBytes()));
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //根据路段名搜索负责的人
    @RequestMapping(value = "/selectUserbyroad" , method = RequestMethod.POST)
    @ResponseBody
    public List<USERINFO> selectUser(@RequestBody String str) throws IOException {
        //获取前端传来的道路名
        JSONObject json_parameter = JSON.parseObject(str);
        String road=json_parameter.getString("road");
        //System.out.println("获取到的道路名称是："+road);
        //1.根据道路名查询道路id列表
        List<String> roadid=userMapper.getroadidbyroad(road);
        //2.根据道路id查询负责人的名字
        //2.1先定义一个List列表
        List<String> username=new ArrayList<>();
        //2.2循环道路id列表,往里面增加元素
        for (int i = 0; i < roadid.size(); i++) {
            //根据道路id列表查询用户名列表，其中每一个道路id列表都对应一个用户名列表
            for (int j = 0; j < userMapper.getroadbyroadid(roadid.get(i)).size(); j++) {
                //往用户名列表里添加用户名
                //如果用户名列表里存在该用户
                if(!username.contains(userMapper.getnamebyroadid(roadid.get(i)).get(j))){
                    username.add(userMapper.getnamebyroadid(roadid.get(i)).get(j));
                }
            }

        }
        for (int i = 0; i < username.size(); i++) {
            System.out.println("获取到的用户名有："+username.get(i));
        }
        //3.定义一个最终返回的USERINFO的列表
        List<USERINFO> finaluser = new ArrayList<>();
        //根据用户名列表返回userinfo信息
        for (int i = 0; i < username.size(); i++) {

            finaluser.add(userMapper.getUserInfobyname(username.get(i)));
        }

        return finaluser;
    }
}
