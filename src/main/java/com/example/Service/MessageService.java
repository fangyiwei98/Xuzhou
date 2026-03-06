package com.example.Service;

import com.example.POJO.MESSAGE;

import java.util.List;

public interface MessageService {


    //插入消息
    void saveNewmessage(String id,String content,String name,String type,String time);

    //获取消息
    List<MESSAGE> getMessage(String name,String type);

    //更改消息状态
    void updateMessage(String id);

    //删除消息
    void deleteMessage(String id);

    //获取未读消息数量
    Integer getNum(String name,String type);

}
