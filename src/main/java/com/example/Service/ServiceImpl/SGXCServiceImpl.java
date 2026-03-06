package com.example.Service.ServiceImpl;

import com.example.Dao.SGXCMapper;
import com.example.POJO.SGXC;
import com.example.POJO.SGXCUSER;
import com.example.Service.SGXCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SGXCServiceImpl implements SGXCService {

    @Autowired
    private SGXCMapper sgxcMapper;

    //插入施工现场(admin)
    @Override
    public void saveSGXC(String id, String name, String road, String facilities, String gcname, String jianshe, String shigong, String time, String content, String location, String locationurl, String numadmin) {
        sgxcMapper.saveSGXC(id, name, road, facilities, gcname, jianshe, shigong, time, content, location, locationurl, numadmin);
    }

    //施工现场上报(user)
    @Override
    public void saveSGXCUSER(String id, String name, String pictureurl, String time, String num, String gcname) {
        sgxcMapper.saveSGXCUSER(id, name, pictureurl, time, num, gcname);
    }

    //获取所有施工现场(admin and user)
    @Override
    public List<SGXC> getGcname() {
        return sgxcMapper.getGcname();
    }

    //根据工程名称获取上传的图片(admin)
    @Override
    public List<SGXCUSER> getPicBygcname(String gcname) {
        return sgxcMapper.getPicBygcname(gcname);
    }
}
