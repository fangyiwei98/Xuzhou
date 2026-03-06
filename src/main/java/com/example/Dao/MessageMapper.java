package com.example.Dao;

import com.example.POJO.MESSAGE;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MessageMapper {


    //插入消息(admin)
    @Insert("insert into MESSAGE(id,content,statue,name,type,time)values(#{id},#{content},0,#{name},#{type},#{time})")
    void saveNewmessage(@Param("id")String id, @Param("content")String content,@Param("name")String name,@Param("type")String type,@Param("time")String time);


    //查看消息(根据人姓名和类型)
    @Select("select * from MESSAGE where name=#{name} and type=#{type}")
    List<MESSAGE> getMessage(@Param("name")String name,@Param("type")String type);

    //根据id更改任务状态
    @Update("update MESSAGE SET statue=1 where id=#{id}")
    void updateMessage(@Param("id")String id);

    //根据id删除消息
    @Delete("delete from MESSAGE where id=#{id}")
    void deleteMessage(@Param("id")String id);


    //获取未读消息数量
    @Select("select count(*) from MESSAGE where statue=0 and name=#{name} and type=#{type}")
    Integer getNum(@Param("name")String name,@Param("type")String type);

}
