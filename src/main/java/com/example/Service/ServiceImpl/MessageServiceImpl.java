package com.example.Service.ServiceImpl;

import com.example.Dao.MessageMapper;
import com.example.POJO.MESSAGE;
import com.example.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;

    //插入消息
    @Override
    public void saveNewmessage(String id, String content,String name,String type,String time) {
        messageMapper.saveNewmessage(id, content,name,type,time);
    }

    //获取消息
    @Override
    public List<MESSAGE> getMessage(String name, String type) {
        return messageMapper.getMessage(name, type);
    }

    //更改消息状态
    @Override
    public void updateMessage(String id) {
        messageMapper.updateMessage(id);
    }

    //删除消息
    @Override
    public void deleteMessage(String id) {
        messageMapper.deleteMessage(id);
    }

    //获取未读消息数量
    @Override
    public Integer getNum(String name, String type) {
        return messageMapper.getNum(name, type);
    }
}
