package com.example.Service;

import com.example.POJO.SGXC;
import com.example.POJO.SGXCUSER;

import java.util.List;

public interface SGXCService {

    //插入施工现场(admin)
    void saveSGXC(String id,String name,String road,String facilities,String gcname,String jianshe,String shigong,String time,String content,String location,String locationurl,String numadmin);

    //施工现场上报(user)
    void saveSGXCUSER(String id,String name,String pictureurl,String time,String num,String gcname);

    //获取所有施工现场(admin and user)
    List<SGXC> getGcname();

    //根据工程名称获取上传的图片(admin)
    List<SGXCUSER> getPicBygcname(String gcname);
}
