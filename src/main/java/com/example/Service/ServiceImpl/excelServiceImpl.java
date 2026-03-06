package com.example.Service.ServiceImpl;

import com.example.Dao.UserMapper;
import com.example.POJO.excelUser;
import com.example.Service.excelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

@Service
public class excelServiceImpl implements excelService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save(List<excelUser> excelUsers) {
        //迭代的方法
        //excelMapper.saveAll(excelUsers);
        //System.out.println("能执行Service吗？");
        System.out.println("excelUsers是："+excelUsers);
        for (com.example.POJO.excelUser excelUser : excelUsers) {
            //数据库里没有用户，创建
            if (userMapper.getInfoByname(excelUser.getName()) == null) {

                //在用户表里新建一个用户
                String id = UUID.randomUUID().toString();
                userMapper.registerUser(id, excelUser.getName(), DigestUtils.md5DigestAsHex("123456".getBytes()), excelUser.getRole(), excelUser.getTel());
                //道路表里没有道路和起止点的组合
                if (userMapper.getidbygouguan(excelUser.getRoad(), excelUser.getQizhi()) == null) {

                    //道路表里插入道路和起止点信息
                    String daoluid = UUID.randomUUID().toString();
                    userMapper.insertfengong(daoluid, excelUser.getRoad(), excelUser.getQizhi());
                    //把道路id、人员姓名插入到link表里
                    String linkid = UUID.randomUUID().toString();
                    userMapper.insertLink(linkid, excelUser.getName(), daoluid);

                } else {
                    //System.out.println("道路表里有路段和起止点的组合");
                    //道路表里有路段和起止点的组合
                    //根据道路表里道路名称和起止点查询道路id
                    String daoluid = userMapper.getidbygouguan(excelUser.getRoad(), excelUser.getQizhi());
                    //System.out.println("daoluid:"+daoluid);
                    //如果link表里已经有道路id（该路段已关联其他人,更新link表）
                    if (userMapper.getlinkid(daoluid).size() != 0) {

                        userMapper.updatelink(excelUser.getName(), daoluid);

                    } else {

                        //如果没有，将道路Id和用户名字插入到用户道路关联表中
                        String linkid = UUID.randomUUID().toString();
                        userMapper.insertLink(linkid, excelUser.getName(), daoluid);
                    }
                }

            } else {

                //数据库有用户，更新角色，电话
                //excelMapper.uploaduser(excelUsers.get(i).getName(), excelUsers.get(i).getRole(),excelUsers.get(i).getTel());
                userMapper.uploaduser(excelUser.getName(), excelUser.getTel(),excelUser.getRole());

                //道路表里没有道路和起止点的组合
                if (userMapper.getidbygouguan(excelUser.getRoad(), excelUser.getQizhi()) == null) {


                    //道路表里插入道路和起止点信息
                    String daoluid = UUID.randomUUID().toString();
                    userMapper.insertfengong(daoluid, excelUser.getRoad(), excelUser.getQizhi());

                    //把道路id、人员姓名插入到link表里
                    String linkid = UUID.randomUUID().toString();
                    userMapper.insertLink(linkid, excelUser.getName(), daoluid);
                } else {

                    //根据道路表里道路名称和起止点查询道路id
                    String daoluid = userMapper.getidbygouguan(excelUser.getRoad(), excelUser.getQizhi());
                    System.out.println("daoluid:::::::::::::"+daoluid);

                    //如果link表里已经有道路id（该路段已关联其他人,更新link表）
                    if (userMapper.getlinkid(daoluid).size() != 0) {

                        userMapper.updatelink(excelUser.getName(), daoluid);

                    } else {
                        //如果没有，将道路Id和用户名字插入到用户道路关联表中
                        String linkid = UUID.randomUUID().toString();
                        userMapper.insertLink(linkid, excelUser.getName(), daoluid);
                    }
                }


            }


        }
    }
}
