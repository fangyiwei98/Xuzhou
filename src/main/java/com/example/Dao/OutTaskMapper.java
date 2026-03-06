package com.example.Dao;

import com.example.POJO.OUTTASK;
import com.example.POJO.fengong;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OutTaskMapper {

    //下发外来任务(admin)
    @Insert("insert into OUTTASK(id,admin,pictureurl,statue,time,etime,origin,qinkuang,address,road,num,dealafter,dangerlevel,area,classify,type,download,islook)values(#{id},#{admin},#{pictureurl},0,#{time},#{etime},#{origin},#{qinkuang},#{address},#{road},#{num},0,#{dangerlevel},#{area},#{classify},#{type},0,0)")
    void saveOuttask(@Param("id")String id, @Param("admin")String admin,@Param("pictureurl")String pictureurl, @Param("time")String time, @Param("etime")String etime, @Param("origin")String origin,@Param("qinkuang")String qinkuang,@Param("address")String address,@Param("road")String road,@Param("num") String num,@Param("dangerlevel") String dangerlevel,@Param("area")String area,@Param("classify")String classify,@Param("type")String type);



    //查询外来任务(admin)
    @Select("select * from OUTTASK order by TIME")
    List<OUTTASK> getOuttask();


    //根据路段获取未接收外来任务(巡检人员)
    @Select("select * from OUTTASK where statue=0 and road=#{road} and dealafter=0 order by TIME")
    List<OUTTASK> getOuttaskuser(@Param("road")String road);

    //根据路段获取未接收外来任务(养护人员)
    @Select("select * from OUTTASK where statue=0 and dealafter=1 order by TIME")
    List<OUTTASK> getOuttaskuserYH();

    //根据roadid查询道路名称
    @Select("select gouguan from FENGONG where id=#{roadid}")
    String getroadname(@Param("roadid")String roadid);


    //根据路段搜索外来任务(巡检人员)
    @Select("select * from OUTTASK where road like '%${road}%' and statue=#{statue,jdbcType=VARCHAR} and dealafter=0 and people=#{poeple,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByroad(@Param("road")String road,@Param("statue")String statue,@Param("poeple")String poeple);

    //根据路段搜索外来任务(养护人员)
    @Select("select * from OUTTASK where road like '%${road}%' and statue=#{statue,jdbcType=VARCHAR} and dealafter=1 and yhpeople=#{yhpoeple,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByroadYH(@Param("road")String road,@Param("statue")String statue,@Param("yhpoeple")String yhpoeple);


    //根据id搜索外来任务(巡检人员)
    @Select("select * from OUTTASK where id like '%${id}%' and statue=#{statue,jdbcType=VARCHAR} and dealafter=0 and people=#{poeple,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByid(@Param("id")String id,@Param("statue")String statue,@Param("poeple")String poeple);

    //根据id搜索外来任务(养护人员)
    @Select("select * from OUTTASK where id like '%${id}%' and statue=#{statue,jdbcType=VARCHAR} and dealafter=1 and yhpeople=#{yhpoeple,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByidYH(@Param("id")String id,@Param("statue")String statue,@Param("yhpoeple")String yhpoeple);



    //搜索外来任务(user,road为空时)
    @Select("select * from OUTTASK where road like '%${road}%' and statue=100")
    List<OUTTASK> selectnullOutTaskByroad(@Param("road")String road);

    //搜索外来任务(user,id为空时)
    @Select("select * from OUTTASK where id like '%${id}%' and statue=100")
    List<OUTTASK> selectnullOutTaskByid(@Param("id")String id);


    //接取外来任务(巡检人员)
    @Update("update OUTTASK set people=#{people,jdbcType=VARCHAR},tel=#{tel,jdbcType=VARCHAR},statue=1,islook=0 where id=#{id,jdbcType=VARCHAR}")
    void receiveTask(@Param("id")String id,@Param("people")String people,@Param("tel")String tel);

    //接取外来任务(养护人员)
    @Update("update OUTTASK set yhpeople=#{yhpeople,jdbcType=VARCHAR},yhtel=#{yhtel,jdbcType=VARCHAR},statue=1,islook=0 where id=#{id,jdbcType=VARCHAR}")
    void receiveoutYHTask(@Param("id")String id,@Param("yhpeople")String yhpeople,@Param("yhtel")String yhtel);


    //完成外来任务(巡检人员，此外来任务不属于管理处,dealafter不变)
    @Update("update OUTTASK set result=#{result,jdbcType=VARCHAR},xjnumfinish=#{numfinish,jdbcType=VARCHAR},statue=2,xjreporttime=#{reporttime,jdbcType=VARCHAR},belong=#{belong,jdbcType=VARCHAR},xjreturnword=#{returnword,jdbcType=VARCHAR},dealafter=0 where id=#{id,jdbcType=VARCHAR}")
    void finishoutTask(@Param("id")String id,@Param("result")String result,@Param("numfinish")String numfinish,@Param("reporttime")String reporttime,@Param("belong")String belong,@Param("returnword")String returnword);

    //完成外来任务时修改部分信息(巡检人员)
    @Update("update OUTTASK set area=#{area,jdbcType=VARCHAR},road=#{road,jdbcType=VARCHAR},classify=#{classify,jdbcType=VARCHAR},qinkuang=#{qinkuang,jdbcType=VARCHAR},address=#{address,jdbcType=VARCHAR},type=#{type,jdbcType=VARCHAR},islook=0 where id=#{id,jdbcType=VARCHAR}")
    void updateWLinfo(@Param("id")String id,@Param("area")String area,@Param("road")String road,@Param("classify")String classify,@Param("qinkuang")String qinkuang,@Param("address")String address,@Param("type")String type);

    //插入权属划分信息(巡检人员)
    @Update("update OUTTASK set quanshu=#{quanshu,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void insertQuanshu(@Param("id")String id,@Param("quanshu")String quanshu);

    //插入外来任务经纬度信息(巡检人员)
    @Update("update OUTTASK set lng=#{lng,jdbcType=VARCHAR},lat=#{lat,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void insertJW(@Param("id")String id,@Param("lng")String lng,@Param("lat")String lat);

    //完成外来任务,需要将该任务交给养护人员处理(巡检人员，此外来任务属于管理处,需要将statue改为2，dealafter不变)
    @Update("update OUTTASK set statue=2,belong=#{belong,jdbcType=VARCHAR},dealafter=0,xjreturnword=#{xjreturnword},dangerlevel=#{dangerlevel} where id=#{id,jdbcType=VARCHAR}")
    void finishoutTaskdealafter(@Param("id")String id,@Param("belong")String belong,@Param("xjreturnword")String xjreturnword,@Param("dangerlevel")String dangerlevel);

    //完成外来任务(养护人员)
    @Update("update OUTTASK set result=#{result,jdbcType=VARCHAR},numfinish=#{numfinish,jdbcType=VARCHAR},reporttime=#{reporttime,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void finishoutTaskYH(@Param("id")String id,@Param("result")String result,@Param("numfinish")String numfinish,@Param("reporttime")String reporttime);

    //更改一些外来任务信息,不属于管理处(养护人员)
    @Update("update OUTTASK set statue=2,belong=3,returnword=#{returnword,jdbcType=VARCHAR},quanshu=#{quanshu,jdbcType=VARCHAR},islook=0 where id=#{id,jdbcType=VARCHAR}")
    void updateYHWLBSY(@Param("id")String id,@Param("returnword")String returnword,@Param("quanshu")String quanshu);

    //更改一些外来任务信息,属于管理处(养护人员)
    @Update("update OUTTASK set statue=2,yhcontent=#{yhcontent,jdbcType=VARCHAR},returnword=#{returnword,jdbcType=VARCHAR},islook=0 where id=#{id,jdbcType=VARCHAR}")
    void updateYHWLSY(@Param("id")String id,@Param("yhcontent")String yhcontent,@Param("returnword")String returnword);

    //获取已接受的外来任务(巡检人员)
    @Select("select * from OUTTASK where people=#{people} and statue=1 order by etime desc")
    List<OUTTASK> getMyouttask(@Param("people")String people);
    //获取已接受的外来任务(养护人员)
    @Select("select * from OUTTASK where yhpeople=#{yhpeople} and statue=1 order by etime desc")
    List<OUTTASK> getMyouttaskYH(@Param("yhpeople")String yhpeople);

    //历史外来任务(巡检人员)
    //@Select("select * from OUTTASK where people=#{people} and (statue=2 or statue=3)")
    @Select("select * from OUTTASK where people=#{people} and (belong=1 or belong=2) order by etime desc")
    List<OUTTASK> getHisouttask(@Param("people")String people);

    //历史外来任务(养护人员)
    @Select("select * from OUTTASK where yhpeople=#{people} and (statue=2 or statue=3) order by etime desc")
    List<OUTTASK> getHisouttaskYH(@Param("people")String people);


    //外来任务审核通过(admin)，不需要养护，将dealafter改为1
    @Update("update OUTTASK set statue=3,dealafter=1,islook=0 where id=#{id,jdbcType=VARCHAR}")
    void shenheoutTask(@Param("id")String id);

    //外来养护任务审核通过(admin)和管理人员直接结束属于但不要处理的外来任务(admin)
    @Update("update OUTTASK set statue=3,islook=0 where id=#{id,jdbcType=VARCHAR}")
    void passYHoutTask(@Param("id")String id);


    //删除外来任务(巡检人员)
    @Update("update OUTTASK set people=null,statue=0 where id=#{id,jdbcType=VARCHAR}")
    void deleteoutTask(@Param("id")String id);

    //删除外来任务(养护人员)
    @Update("update OUTTASK set yhpeople=null,statue=0,belong=1,dealafter=1 where id=#{id,jdbcType=VARCHAR}")
    void deleteoutTaskYH(@Param("id")String id);

    //删除外来任务增加退回原因(巡检人员)
    @Update("update OUTTASK set xjth=#{xjth,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void insertXjth(@Param("id")String id,@Param("xjth")String xjth);

    //删除外来任务增加退回原因(养护人员)
    @Update("update OUTTASK set yhth=#{yhth,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void insertYhth(@Param("id")String id,@Param("yhth")String yhth);

    //管理员删除外来任务(admin)
    @Delete("delete from OUTTASK where id=#{id}")
    void deleteouttaskById(@Param("id")String id);

    //管理员驳回巡检人员提交的外来任务(dealafter=0该巡检人员处理)
    @Update("update OUTTASK set statue=1,belong=null,dealafter=0,xjbh=#{xjbh,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void BHXJouttask(@Param("id")String id,@Param("xjbh")String xjbh);

    //管理员驳回养护人员提交的外来任务
    @Update("update OUTTASK set statue=1,belong=1,result=null,numfinish=null,reporttime=null,returnword=null,yhcontent=null,yhbh=#{yhbh,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void BHYHouttask(@Param("id")String id,@Param("yhbh")String yhbh);

    //根据任务id查询巡检用户名
    @Select("select people from OUTTASK where id=#{id}")
    String getuserByid(@Param("id")String id);

    //根据任务id查询养护用户名
    @Select("select yhpeople from OUTTASK where id=#{id}")
    String getYHuserByid(@Param("id")String id);

    //外来任务审核通过,需要养护人员处理，并把dealafter改为1，经过了巡检人员处理
    @Update("update OUTTASK set statue=0,dealafter=1,islook=0 where id=#{id,jdbcType=VARCHAR}")
    void SHtoYH(@Param("id")String id);

    //根据任务id查询dealafter
    @Select("select belong from OUTTASK where id=#{id}")
    String getbelongByid(@Param("id")String id);

    //直接上报外来任务的问题(巡检人员),直接到巡检待审核，statue=2,belong=1,dealafter=0
    @Insert("insert into OUTTASK(id,admin,pictureurl,statue,time,origin,qinkuang,address,road,num,dealafter,belong)values(#{id},#{admin},#{pictureurl},2,#{time},#{etime},#{origin},#{qinkuang},#{address},#{road},#{num},0,1)")
    void reportOuttask(@Param("id")String id, @Param("admin")String admin,@Param("pictureurl")String pictureurl, @Param("time")String time,  @Param("origin")String origin,@Param("qinkuang")String qinkuang,@Param("address")String address,@Param("road")String road,@Param("num") String num);

    //根据id判断该外来任务是巡检的还是养护的
    @Select("select dealafter from OUTTASK where id=#{id}")
    String getdealafter(@Param("id")String id);

    //养护已审核管理员填写处理结果
    @Update("update OUTTASK set cljg=#{cljg,jdbcType=VARCHAR} where id=#{id,jdbcType=VARCHAR}")
    void cjlg(@Param("id")String id,@Param("cljg")String cljg);


    //根据单号查询来源
    @Select("select origin from OUTTASK where id=#{id}")
    String getorigin(@Param("id")String id);

    //根据单号查询任务所有信息
    @Select("select * from OUTTASK where id=#{id}")
    List<OUTTASK> getallinfo(@Param("id")String id);

    //根据id查询任务（只用于打印目录）
    @Select("select * from OUTTASK where id=#{id}")
    OUTTASK getallinfobyid(@Param("id")String id);

    //将下载状态改为已下载
    @Update("update OUTTASK set download=1 where id=#{id,jdbcType=VARCHAR}")
    void downloadstatue(@Param("id")String id);

    //将islook改为1，表示看过
    @Update("update OUTTASK set islook=1 where id=#{id,jdbcType=VARCHAR}")
    void changeislook(@Param("id")String id);


    //根据id模糊查询外来任务
    @Select("select * from OUTTASK where id like '%${id}%'")
    List<OUTTASK> selectOutTaskByidinweb(@Param("id")String id);

    //根据id精确查询外来任务
    @Select("select * from OUTTASK where id=#{id,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByidinwebJQ(@Param("id")String id);
    //根据巡检名字查询外来任务
    @Select("select * from OUTTASK where people=#{people,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskBypeopleinweb(@Param("people")String people);

    //根据下发日期(按年)查询外来任务
    @Select("select * from OUTTASK where SUBSTR(time, 1, 4)=#{year,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByyear(@Param("year")String year);

    //根据下发日期(按月)查询外来任务
    @Select("select * from OUTTASK where SUBSTR(time, 1, 4)=#{year,jdbcType=VARCHAR} and SUBSTR(time, 6, 2)=#{month,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskBymonth(@Param("year")String year,@Param("month")String month);

    //根据下发日期(按日)查询外来任务
    @Select("select * from OUTTASK where SUBSTR(time, 1, 4)=#{year,jdbcType=VARCHAR} and SUBSTR(time, 6, 2)=#{month,jdbcType=VARCHAR} and SUBSTR(time, 9, 2)=#{day,jdbcType=VARCHAR}")
    List<OUTTASK> selectOutTaskByday(@Param("year")String year,@Param("month")String month,@Param("day")String day);


}
