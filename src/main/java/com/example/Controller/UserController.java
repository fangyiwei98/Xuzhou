package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.POJO.USERINFO;
import com.example.Service.ServiceImpl.UserServiceImpl;
//import com.example.Utils.SecretAnnotation;
import com.example.Utils.AESUtil;
import com.example.Utils.AES;
import com.example.Utils.Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/User")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @Value("${AES.KEY}")
    private String aeskey;
    //用户登录
    //@SuppressWarnings("rawtypes")
    //@SecretAnnotation
    @RequestMapping(value = "/login")
    @ResponseBody
    public JSONObject login(@RequestBody Map aaa, HttpServletResponse response) throws Exception {

        JSONObject obj=new JSONObject();
        /*JSONObject json_parameter = JSON.parseObject(str);
        String name= json_parameter.getString("name");
        String pwd= json_parameter.getString("pwd");*/
        String name= (String) aaa.get("name");
        //String pwd= JSONObject.parseObject((String) aaa.get("pwd")).getString("requestData");
        String pwd= (String) aaa.get("pwd");

        Utils.settingCrossDomain(response);
        System.out.println("name:"+name);
        System.out.println("pwd:"+pwd);

        String decoderpassword = AES.decrypt(pwd).replace("\"", "");
        System.out.println("解密后的密码为："+decoderpassword.trim());

        String roleSelect=userService.getRole(name);
        //System.out.println("数据库中获得的角色是："+roleSelect);
        String password=userService.getPwdByname(name);
        //System.out.println("从数据库中获得的密码是："+password);

//        //初次登陆修改密码不返回token
//        if(roleSelect!=null&&password.equals(DigestUtils.md5DigestAsHex("123456".getBytes()))){
//            obj.put("code",5);
//            return obj;
//        }


        // 使用Shiro编写认证处理
        // 1、获取Subject
        Subject subject = SecurityUtils.getSubject();


        System.out.println("验证是否登录："+subject.isAuthenticated());

        UsernamePasswordToken token;

        String md5Password = DigestUtils.md5DigestAsHex(decoderpassword.trim().getBytes());
        //System.out.println("pwd加密后的密码是："+md5Password);

        token = new UsernamePasswordToken(name, md5Password);

            // 3、执行登录
            try {
                System.out.println("shiro认证！！！！！");

                // 登录成功
                subject.login(token);

                System.out.println("验证登陆后的状态："+subject.isAuthenticated());
                Serializable tokenId = subject.getSession().getId();
                if(password.equals(DigestUtils.md5DigestAsHex("123456".getBytes()))&&subject.isAuthenticated()){
                    obj.put("code",5);
                    obj.put("token",String.valueOf(tokenId));
                }else if(roleSelect.equals("超级管理员")&&subject.isAuthenticated()){
                    obj.put("code",1);
                    obj.put("token",String.valueOf(tokenId));
                    //obj.put("role","超级管理员");
                }else if(roleSelect.equals("管理人员")&&subject.isAuthenticated()){
                    obj.put("code",2);
                    obj.put("token",String.valueOf(tokenId));
                    //obj.put("role","管理人员");

                }else if(roleSelect.equals("巡检人员")&&subject.isAuthenticated()){
                    obj.put("code",3);
                    obj.put("token",String.valueOf(tokenId));

                }else if(roleSelect.equals("养护人员")&&subject.isAuthenticated()){
                    obj.put("code",4);
                    obj.put("token",String.valueOf(tokenId));
                }else {
                    System.out.println("无角色人员");
                    obj.put("code",0);
                }

                return obj;
            } catch (UnknownAccountException exception) {

                System.out.println("账号错误");
                obj.put("code",0);
                return obj;
            } catch (IncorrectCredentialsException exception) {
                System.out.println("密码错误");
                obj.put("code",0);
                return obj;
            }


    }



    //用户第一次登录修改密码
    //@SuppressWarnings("rawtypes")
    //@SecretAnnotation
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    public JSONObject updatePwd(@RequestBody Map aaa,HttpServletRequest request, HttpServletResponse response) throws Exception {
        //System.out.println("修改密码str:"+str);
        JSONObject obj=new JSONObject();
        /*JSONObject json_parameter = JSON.parseObject(str);
        String name= json_parameter.getString("name");
        String pwd= json_parameter.getString("pwd");*/
        String name= (String) aaa.get("name");
        String pwd= (String) aaa.get("pwd");
        Utils.settingCrossDomain(response);
        String decoderpassword = AES.decrypt(pwd).replace("\"", "");
        System.out.println("解密后的密码为："+decoderpassword.trim());

        System.out.println("name:"+name);
        System.out.println("pwd:"+pwd);

        try {

            String md5Password = DigestUtils.md5DigestAsHex(decoderpassword.trim().getBytes());

            userService.updatePwd(name,md5Password);

            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }



    }


    @RequestMapping("/tologin")
    public String toLogin() {
        return "请登录";
    }


    @RequestMapping("/logout")
    @ResponseBody
    public JSONObject logout() {

        JSONObject obj=new JSONObject();
        try {
            Subject subject=SecurityUtils.getSubject();

            subject.logout();
            System.out.println("退出登录成功！");
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

}
