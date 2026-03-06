package com.example.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Dao.OutTaskMapper;
import com.example.Dao.UserMapper;
import com.example.POJO.*;
import com.example.Service.*;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/Task")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class TaskController {

    @Value("${file.outadmin}")
    private String outadminpath;
    @Value("${file.outfinish}")
    private String outfinishpath;



    @Autowired
    private TaskService taskService;

    @Autowired
    private YanghuService yanghuService;

    @Autowired
    private OuttaskService outtaskService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private GpspositionService gpspositionService;

    @Autowired
    private OutTaskMapper outTaskMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    //下发任务(admin)
    @RequestMapping(value = "/xiafa" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject taskXiafa(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {

            String id=UUID.randomUUID().toString();
            String area=json_parameter.getString("area");
            String type= json_parameter.getString("type");
            String character=json_parameter.getString("character");
            String content= json_parameter.getString("content");
            String admin= json_parameter.getString("admin");
            String time= json_parameter.getString("time");
            String endtime= json_parameter.getString("endtime");

            taskService.paifaTask(id,area,type, character,content, admin, time, endtime);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //下发养护任务(admin)
    @RequestMapping(value = "/Yanghuxiafa" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject Yanghuxiafa(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {

            String id=json_parameter.getString("id");
            String taskid=UUID.randomUUID().toString();//是养护任务的id
            String area=json_parameter.getString("area");
            String type= json_parameter.getString("type");
            String character=json_parameter.getString("character");
            String content= json_parameter.getString("content");
            String admin= json_parameter.getString("admin");
            String time= json_parameter.getString("time");
            String endtime= json_parameter.getString("endtime");
            String name=json_parameter.getString("name");
            String msgid=UUID.randomUUID().toString();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String now=df.format(new Date());

            //String time=new Date()
            //更新
            problemService.updateProblem(id,"1");

            //给上报人发消息
            //messageService.saveNewmessage(msgid,"您上报的问题已处理！问题号是:"+id,name,"巡检人员",now);

            //放到任务表的
            taskService.paifaTask(taskid,area,type, character,content, admin, time, endtime);

            //放到养护信息表的
            taskService.insertYanghuInfo(id,area,type,content,time,endtime,taskid);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

    //分派指定接取人任务(admin)
    @RequestMapping(value = "/specialtask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject saveSpecialTask(@RequestBody String str) throws IOException {

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        try {
            String id= UUID.randomUUID().toString();
            String area=json_parameter.getString("area");
            String type= json_parameter.getString("type");
            String character=json_parameter.getString("character");
            String content= json_parameter.getString("content");
            String admin= json_parameter.getString("admin");
            String time= json_parameter.getString("time");//下发时间
            String endtime= json_parameter.getString("endtime");
            String people =json_parameter.getString("people");
            String peopletype=json_parameter.getString("peopletype");
            taskService.saveSpecialTask(id,area,type,character, content, admin, time, endtime,people,peopletype);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

    //查询所有的任务信息
    @RequestMapping(value = "/getTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> showTask(){
        return taskService.showTask();
    }

    //结束任务(admin,将任务表里statue改为3，这边可以给用户加个消息)
    @RequestMapping(value = "/finishTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject finishTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            String character=json_parameter.getString("character");
            System.out.println("id:"+id+"character:"+character);
            if(character.equals("巡检任务")){
                //修改任务表里状态（巡检,任务表里）（管理员审核任务通过，statue=3）
                taskService.finishTask(id);
            }else {
                //修改任务表里状态（养护,任务表里）（管理员审核任务通过，statue=3）
                taskService.finishTask(id);
                //管理员修改任务状态（养护,养护信息表里,改为待评分）
                taskService.finishYanghuTask(id);
            }


            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

    //根据任务类型查询任务信息(admin)
    @RequestMapping(value = "/showTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> showTaskBytype(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);
        String character=json_parameter.getString("character");
        return taskService.showTaskBytype(character);
    }

    //根据任务性质查询任务信息(user)(未接收的任务)
    @RequestMapping(value = "/showTaskstatue" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> showTaskBystatue(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);
        String character=json_parameter.getString("type");

            return taskService.showTaskBystatue(character);

    }

    //根据任务性质查询任务信息(user)(未接收的任务)
    @RequestMapping(value = "/showTaskstatueyh" , method = RequestMethod.POST)
    @ResponseBody
    public List<YANGHUINFO> showTaskBystatueyh(@RequestBody String str) {

            JSONObject json_parameter = JSON.parseObject(str);
            String character = json_parameter.getString("type");

            return yanghuService.getYhtask();
        }

    //我的任务
    @RequestMapping(value = "/myTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> myTask(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);
        String people=json_parameter.getString("people");
        String peopletype=json_parameter.getString("peopletype");
        String statue=json_parameter.getString("statue");
        return taskService.mytask(people,peopletype,statue);
    }

    //暂停任务(user)
    @RequestMapping(value = "/pauseTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject pauseTask(@RequestBody String str){
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");
        try {
             taskService.pauseTask(id);
             obj.put("code",1);
             return obj;
         }catch (Exception e) {
                e.printStackTrace();
                obj.put("code",0);
                return obj;
         }
    }

    //接取任务(user)
    @RequestMapping(value = "/receive" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject receiveTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            String people=json_parameter.getString("people");
            String peopletype=json_parameter.getString("peopletype");
            taskService.jiequTask(id,people,peopletype);

            if(peopletype.equals("养护人员")){
                yanghuService.updateYanghupeople(id,people);
            }
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

    //删除任务(user)
    @RequestMapping(value = "/deletetask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            String people=json_parameter.getString("people");
            String peopletype= json_parameter.getString("peopletype");
            System.out.println("删除任务接收到的参数："+str);
            if(peopletype.equals("养护人员")){
                yanghuService.updateYanghudelete(id,people);
                taskService.deleteTask(id,people);
            }else {
                taskService.deleteTask(id,people);
            }

            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //完成任务(user,巡检人员提交将任务状态改为待审核,statue改为2,这边可以给管理员加消息)
    @RequestMapping(value = "/finishXunjianTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject finishXunjianTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            //删除临时表里的轨迹
            gpspositionService.deleteguiji(id);
            //更改任务表里状态
            taskService.finishXunjianTask(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //下发外来任务(admin)
    @RequestMapping(value = "/outTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject outTask(@RequestParam("data") String json,@RequestParam("pictures") MultipartFile[] files) throws IOException {

        //,@RequestParam("addresspics") MultipartFile[] addresspics
        JSONObject obj=new JSONObject();
        //List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("fileList");
        System.out.println("获取到的json是："+json);
//        int begin = json.lastIndexOf("{");
//        int end = json.lastIndexOf("}");
//        String substring = json.substring(begin, end + 1);
//        System.out.println("截取字符串："+substring);

        JSONObject json_parameter = JSON.parseObject(json);
        String id= json_parameter.getString("id");
        String admin= json_parameter.getString("admin");
        String time= json_parameter.getString("time");
        String etime=json_parameter.getString("etime");
        String origin=json_parameter.getString("origin");
        String qinkuang=json_parameter.getString("qinkuang");
        String address=json_parameter.getString("address");
        String road=json_parameter.getString("road");
        //String num=json_parameter.getString("num");
        String dangerlevel=json_parameter.getString("dangerlevel");
        String area=json_parameter.getString("area");
        String classify=json_parameter.getString("classify");
        String type=json_parameter.getString("type");

        System.out.println("id:"+id);
        System.out.println("files.length:"+files.length);

        try {
            //上传情况描述图片
            for (int i = 0; i < files.length; i++) {
                //file=files.get(i);
                System.out.println("情况描述图片名称：" + files[i].getOriginalFilename());

                String filename=id+i+".jpg";
                File filepath = new File(outadminpath, filename);
                System.out.println("路径名："+filepath);
                //判断路径是否存在，没有就创建一个
                if (!filepath.getParentFile().exists()) {
                    filepath.getParentFile().mkdirs();
                }
                Thumbnails.of(files[i].getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                //files[i].transferTo(filepath);
            }

            String num= Integer.toString(files.length);
            //String numadd=Integer.toString(addresspics.length);
            //outtaskService.saveOuttask(id,admin,outadminpath,time,etime,origin,qinkuang,address,road,num);
            outTaskMapper.saveOuttask(id,admin,outadminpath,time,etime,origin,qinkuang,address,road,num,dangerlevel,area,classify,type);
            obj.put("code",1);
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //外来任务查询(admin)
    @RequestMapping(value = "/showOuttask" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> showoutTask(){
        return outtaskService.getOuttask();
    }


    //获取未接收的外来任务(巡检人员)
    @RequestMapping(value = "/getOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> showoutTaskuser(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);
        //获取用户名
        String name=json_parameter.getString("name");
        //现根据用户名查询路段
        List<LINK> links=outtaskService.getroadbyname(name);
        //System.out.println("links:"+links);
        List<OUTTASK> outtasks = new ArrayList<>();
        for (LINK link : links) {
            //根据道路名称查询外来任务
            //根据roadid查询road名称
            String road=outtaskService.getroadname(link.getRoadid());
            //根据road名称查询外来任务
            List<OUTTASK> aaa = outtaskService.getOuttaskuser(road);
            //遍历所有外来任务
            for (int i = 0; i < aaa.size(); i++) {
                //如果outtasks不包含该结果，插入
                if (!outtasks.contains(aaa.get(i))) {
                    //将所有外来任务插入返回的结果里
                    outtasks.add(aaa.get(i));
                }
            }

        }
        System.out.println("获取的外来任务是："+outtasks);

        return outtasks;
    }

    //获取未接收的外来任务(养护人员)
    @RequestMapping(value = "/getOutTaskYH" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> showoutTaskuserYH(){

        return outTaskMapper.getOuttaskuserYH();

    }


    //根据路段搜索外来任务(巡检人员和养护人员)(未用到，前端自己搜索的)
    @RequestMapping(value = "/selectOutTaskByroad" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> selectOutTaskByroad(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);
        String road=json_parameter.getString("road");
        String statue=json_parameter.getString("statue");
        String name=json_parameter.getString("name");
        //根据姓名查询角色
        String role=userMapper.getRole(name);
        if (role.equals("巡检人员")) {
            if(road.equals("")){
                return outTaskMapper.selectnullOutTaskByroad(road);
            }else {
                return outTaskMapper.selectOutTaskByroad(road,statue,name);
                //outtaskService.selectOutTaskByroad(road);
            }
        }else {
            if(road.equals("")){
                return outTaskMapper.selectnullOutTaskByroad(road);
            }else {
                return outTaskMapper.selectOutTaskByroadYH(road,statue,name);
                //outtaskService.selectOutTaskByroad(road);
            }
        }


    }


    //根据单号精准、模糊查询、巡检人姓名、下发日期搜索外来任务(web端)
    @RequestMapping(value = "/selectOutTaskInweb" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> selectOutTaskInweb(@RequestBody String str){
        //1.获取前端传的参数
        JSONObject json_parameter = JSON.parseObject(str);
        String idmh= json_parameter.getString("idmh");
        String id= json_parameter.getString("id");
        String people= json_parameter.getString("people");
        String year= json_parameter.getString("year");
        String month= json_parameter.getString("month");
        String day= json_parameter.getString("day");
        //2.定义最终返回的数组
        List<OUTTASK> finaltasks=new ArrayList<>();
        //用来判断是否传的都是空
        int count=0;
        //3.判断前端传的值哪些不为空,并放到map中
        if(idmh.length()!=0){
            //canshu.put("idmh",idmh);
            //获取模糊id查询的数据
            List<OUTTASK> idmhtasks=outTaskMapper.selectOutTaskByidinweb(idmh);
            //遍历所有外来任务
            for (int i = 0; i < idmhtasks.size(); i++) {
                //如果outtasks不包含该结果，插入
                if (!finaltasks.contains(idmhtasks.get(i))) {
                    //将所有外来任务插入返回的结果里
                    finaltasks.add(idmhtasks.get(i));
                }
            }
            count=count+1;
        }
        if(id.length()!=0){
            //canshu.put("id",id);
            List<OUTTASK> idtasks=outTaskMapper.selectOutTaskByidinwebJQ(id);
            //遍历所有外来任务
            for (int i = 0; i < idtasks.size(); i++) {
                //如果outtasks不包含该结果，插入
                if (!finaltasks.contains(idtasks.get(i))) {
                    //将所有外来任务插入返回的结果里
                    finaltasks.add(idtasks.get(i));
                }
            }
            count=count+1;
        }
        if(people.length()!=0){
            //canshu.put("people",people);
            List<OUTTASK> peopletasks=outTaskMapper.selectOutTaskBypeopleinweb(people);
            //遍历所有外来任务
            for (int i = 0; i < peopletasks.size(); i++) {
                //如果outtasks不包含该结果，插入
                if (!finaltasks.contains(peopletasks.get(i))) {
                    //将所有外来任务插入返回的结果里
                    finaltasks.add(peopletasks.get(i));
                }
            }
            count=count+1;
        }
        if(year.length()!=0){

            if(month.length()!=0){

                if(day.length()!=0){
                    //按日查找,year,month,day都不为空
                    List<OUTTASK> daytasks=outTaskMapper.selectOutTaskByday(year,month,day);
                    //遍历所有外来任务
                    for (int i = 0; i < daytasks.size(); i++) {
                        //如果outtasks不包含该结果，插入
                        if (!finaltasks.contains(daytasks.get(i))) {
                            //将所有外来任务插入返回的结果里
                            finaltasks.add(daytasks.get(i));
                        }
                    }

                }else {
                    //按月查找,month不为空，day为空
                    List<OUTTASK> monthtasks=outTaskMapper.selectOutTaskBymonth(year,month);
                    //遍历所有外来任务
                    for (int i = 0; i < monthtasks.size(); i++) {
                        //如果outtasks不包含该结果，插入
                        if (!finaltasks.contains(monthtasks.get(i))) {
                            //将所有外来任务插入返回的结果里
                            finaltasks.add(monthtasks.get(i));
                        }
                    }
                }
            }else {
                //按年查找,year不为空，month为空
                List<OUTTASK> yeartasks=outTaskMapper.selectOutTaskByyear(year);
                //遍历所有外来任务
                for (int i = 0; i < yeartasks.size(); i++) {
                    //如果outtasks不包含该结果，插入
                    if (!finaltasks.contains(yeartasks.get(i))) {
                        //将所有外来任务插入返回的结果里
                        finaltasks.add(yeartasks.get(i));
                    }
                }
            }
            count=count+1;
        }
        if (count == 0) {
            //传的参数都为空
            return outTaskMapper.getOuttask();
        }else {
            //传的参数不为空
            return finaltasks;
        }

    }


    //根据id搜索外来任务(巡检人员和养护人员)(未用到，前端自己搜索的)
    @RequestMapping(value = "/selectOutTaskByid" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> selectOutTaskByid(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");
        String statue=json_parameter.getString("statue");
        String name=json_parameter.getString("name");
        //根据姓名查询角色
        String role=userMapper.getRole(name);
        if (role.equals("巡检人员")) {
            if(id.equals("")){
                return outTaskMapper.selectnullOutTaskByid(id);
            }else {
                return outTaskMapper.selectOutTaskByid(id,statue,name);
                //outtaskService.selectOutTaskByroad(road);
            }
        }else {
            if(id.equals("")){
                return outTaskMapper.selectnullOutTaskByid(id);
            }else {
                return outTaskMapper.selectOutTaskByidYH(id,statue,name);
                //outtaskService.selectOutTaskByroad(road);
            }
        }


    }

    //点击更改查看状态（供前端加粗使用）
    @RequestMapping(value = "/changestatue" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changestatue(@RequestBody String str){
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");
        try {
            outTaskMapper.changeislook(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

//    //搜索外来任务(养护人员)
//    @RequestMapping(value = "/selectOutTaskByroadYH" , method = RequestMethod.POST)
//    @ResponseBody
//    public List<OUTTASK> selectOutTaskByroadYH(@RequestBody String str){
//        JSONObject json_parameter = JSON.parseObject(str);
//        String road=json_parameter.getString("road");
//        if(road.equals("")){
//            return outTaskMapper.selectnullOutTaskByroad(road);
//        }else {
//            return outtaskService.selectOutTaskByroadYH(road);
//        }
//
//    }


    //接取外来任务(巡检人员和养护人员)
    @RequestMapping(value = "/receiveOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject receiveOutTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            String people=json_parameter.getString("people");
            //根据用户名查询角色
            String role=userMapper.getRole(people);
            //如果是巡检人员接取
            if(role.equals("巡检人员")){
                //根据巡检用户名查询电话号码
                String tel=userMapper.getTel(people);
                outtaskService.receiveOutTask(id,people,tel);
                obj.put("code",1);
                return obj;
            }else {
                //养护人员接取
                //根据养护用户名查询养护手机号
                String yhtel=userMapper.getTel(people);
                outTaskMapper.receiveoutYHTask(id,people,yhtel);
                obj.put("code",1);
                return obj;
            }


        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //完成外来任务（巡检人员）
    @RequestMapping(value = "/finishOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject finishOutTask(HttpServletRequest request)throws IOException {
        JSONObject obj=new JSONObject();

        String id= request.getParameter("id");
        String people= request.getParameter("people");
        //System.out.println("前端传的是："+people);
        String belong=request.getParameter("belong");
        System.out.println("belong:"+belong);
        String area=request.getParameter("area");
        String road=request.getParameter("road");
        String classify=request.getParameter("classify");
        String qinkuang=request.getParameter("qinkuang");
        String address=request.getParameter("address");
        String type=request.getParameter("type");
        //获取经纬度
        String lng=request.getParameter("lng");
        String lat=request.getParameter("lat");
        try {
            //如果属于管理处,转交给养护组
            if(belong.equals("1")){
                //是管理处的也要拍照片
                List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("result");
                //完成图片的数量
                String numfinish = request.getParameter("xjnumfinish");
                String reporttime=request.getParameter("xjreporttime");
                String returnword=request.getParameter("xjreturnword");
                MultipartFile file = null;
                for (int i = 0; i < files.size(); i++) {
                    file=files.get(i);
                    System.out.println("文件名称" + file.getOriginalFilename());
                    //上传文件名
                    String filename = file.getOriginalFilename();//上传文件的真实名称
                    File filepath = new File(outfinishpath, filename);
                    System.out.println("路径名："+filepath);
                    System.out.println("随机数文件名称" + filepath.getName());
                    //判断路径是否存在，没有就创建一个
                    if (!filepath.getParentFile().exists()) {
                        filepath.getParentFile().mkdirs();
                    }
                    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                    //file.transferTo(filepath);
                }

                outtaskService.finishoutTask(id,outfinishpath,numfinish,reporttime,belong,returnword);
                String dangerlevel=request.getParameter("dangerlevel");
                outTaskMapper.finishoutTaskdealafter(id,belong,returnword,dangerlevel);
                //修改外来任务部分信息
                outTaskMapper.updateWLinfo(id,area,road,classify,qinkuang,address,type);
                //插入经纬度信息
                outTaskMapper.insertJW(id,lng,lat);
                obj.put("code",1);
                return obj;
            }else {
                List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("result");
                //完成图片的数量
                String numfinish = request.getParameter("xjnumfinish");
                String reporttime=request.getParameter("xjreporttime");
                String returnword=request.getParameter("xjreturnword");
                //权属划分
                String quanshu=request.getParameter("quanshu");
                MultipartFile file = null;
                for (int i = 0; i < files.size(); i++) {
                    file=files.get(i);
                    System.out.println("文件名称" + file.getOriginalFilename());
                    //上传文件名
                    String filename = file.getOriginalFilename();//上传文件的真实名称
                    File filepath = new File(outfinishpath, filename);
                    System.out.println("路径名："+filepath);
                    System.out.println("随机数文件名称" + filepath.getName());
                    //判断路径是否存在，没有就创建一个
                    if (!filepath.getParentFile().exists()) {
                        filepath.getParentFile().mkdirs();
                    }
                    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                    //file.transferTo(filepath);
                }

                outtaskService.finishoutTask(id,outfinishpath,numfinish,reporttime,belong,returnword);
                //修改外来任务部分信息
                outTaskMapper.updateWLinfo(id,area,road,classify,qinkuang,address,type);
                //插入权属划分信息
                outTaskMapper.insertQuanshu(id,quanshu);
                //插入经纬度信息
                outTaskMapper.insertJW(id,lng,lat);
                obj.put("code",1);
                return obj;
            }


        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

    //完成外来任务（养护人员）
    @RequestMapping(value = "/finishOutTaskYH" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject finishOutTaskYH(HttpServletRequest request)throws IOException {
        JSONObject obj=new JSONObject();

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("result");
        //获取一些参数
        String id= request.getParameter("id");
        String belong= request.getParameter("belong");

        //完成图片的数量
        String numfinish = request.getParameter("numfinish");
        String reporttime=request.getParameter("reporttime");
        MultipartFile file = null;
        try {
            for (int i = 0; i < files.size(); i++) {
                    file=files.get(i);
                    System.out.println("文件名称" + file.getOriginalFilename());
                    //上传文件名
                    String filename = file.getOriginalFilename();//上传文件的真实名称
                    File filepath = new File(outfinishpath, filename);
                    System.out.println("路径名："+filepath);
                    System.out.println("随机数文件名称" + filepath.getName());
                    //判断路径是否存在，没有就创建一个
                    if (!filepath.getParentFile().exists()) {
                        filepath.getParentFile().mkdirs();
                    }
                    Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
                    //file.transferTo(filepath);
                }
            //上传图片
            outTaskMapper.finishoutTaskYH(id,outfinishpath,numfinish,reporttime);
            //更改一些信息
            //先根据belong选择获取的参数
            if(belong.equals("1")){
                //如果属于管理处，获取养护内容
                String yhcontent= request.getParameter("yhcontent");
                String returnword=request.getParameter("returnword");
                //把养护内容放进去
                outTaskMapper.updateYHWLSY(id,yhcontent,returnword);
            }else {
                //不属于管理处,获取备注
                String returnword=request.getParameter("returnword");
                String quanshu="不属于市管排水井";
                //填写备注，并且更新权属划分，把belong设为3，表示由养护人员核实的不属于管理处
                outTaskMapper.updateYHWLBSY(id,returnword,quanshu);
            }
            obj.put("code",1);
            return obj;

        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

    //筛选所有巡检待审核(admin)
    @RequestMapping(value = "/showXunjianTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> showXunjianTask(@RequestBody String str){

        return taskService.XunjianDaishenhe();
    }

    //根据type和character查询任务(admin)
    @RequestMapping(value = "/showTaskbyBoth" , method = RequestMethod.POST)
    @ResponseBody
    public List<TASKINFO> showTaskbyBoth(@RequestBody String str){
        JSONObject json_parameter = JSON.parseObject(str);
        String type=json_parameter.getString("type");
        String character=json_parameter.getString("character");
        return taskService.selectBoth(type,character);
    }

    //任务批量通过(admin)
    @RequestMapping(value = "/passTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject passTask(@RequestBody HashMap<String, List<String>> ids)throws IOException {


        //System.out.println("ids.values():"+ids.values());
        String strl=ids.values().toString();

        String bbb=strl.substring(2,strl.length()-2);
        //System.out.println("bbb:"+bbb);
        //System.out.println("strl:"+strl);
        String[] aaa=bbb.split(", ");


        System.out.println("aaa.length:"+aaa.length);
        /*for (int i = 0; i <aaa.length ; i++) {
            System.out.println(aaa[i]);
        }*/

        JSONObject obj=new JSONObject();
        try {
            for(int i=0;i<aaa.length;i++){
                //System.out.println("id:"+aaa[i]);
                taskService.finishTask(aaa[i]);
            }
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //任务批量驳回(admin)
    @RequestMapping(value = "/nopassTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject nopassTask(@RequestBody HashMap<String, List<String>> ids)throws IOException {

        String strl=ids.values().toString();
        String bbb=strl.substring(2,strl.length()-2);
        String[] aaa=bbb.split(", ");

        JSONObject obj=new JSONObject();
        try {
            for(int i=0;i<aaa.length;i++){
                taskService.bohuiTask(aaa[i]);
            }
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //获取已接受的外来任务(user)
    @RequestMapping(value = "/getMyOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> getMyOutTask(@RequestBody String str)throws IOException {
        JSONObject json_parameter = JSON.parseObject(str);
            String people=json_parameter.getString("people");
            //根据用户名查询角色
            String role=userMapper.getRole(people);
            //如果是巡检人员
            if(role.equals("巡检人员")){
                return outtaskService.getMyouttask(people);
            }else {
                return outTaskMapper.getMyouttaskYH(people);
            }

    }

    //历史外来任务(user)
    @RequestMapping(value = "/historyOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public List<OUTTASK> gethistoryOutTask(@RequestBody String str)throws IOException {
        JSONObject json_parameter = JSON.parseObject(str);
        String people=json_parameter.getString("people");
        //根据用户名查询角色
        String role=userMapper.getRole(people);
        //如果是巡检人员接取
        if(role.equals("巡检人员")){
            return outtaskService.getHisouttask(people);
        }else {
            return outTaskMapper.getHisouttaskYH(people);
        }

    }


    //外来任务审核通过(admin)
    @RequestMapping(value = "/shenheoutTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject shenheoutTask(@RequestBody String str)throws IOException {

        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");

        JSONObject obj=new JSONObject();
        try {

            //根据id查询外来任务信息
            String dealafter=outTaskMapper.getdealafter(id);
            System.out.println("dealafter是0吗？："+dealafter.equals("0"));
            //如果该任务的dealafter=0，说明通过的是巡检任务
            if(dealafter.equals("0")){
                String belong=outTaskMapper.getbelongByid(id);
                System.out.println("belong是："+belong);
                //需要养护人员处理，belong1表示属于，2表示不属于
                if(belong.equals("1")){
                    //需要养护人员处理
                    outTaskMapper.SHtoYH(id);
                }else {
                    //不需要养护人员处理
                    outTaskMapper.shenheoutTask(id);
                }
            }else{
                //养护人员通过直接到已审核
                outTaskMapper.passYHoutTask(id);
            }
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }

    //管理人员直接结束属于但不要处理的外来任务(admin)
    @RequestMapping(value = "/JSoutTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject JSoutTask(@RequestBody String str)throws IOException {

        JSONObject json_parameter = JSON.parseObject(str);
        String id=json_parameter.getString("id");

        JSONObject obj=new JSONObject();
        try {
            //和通过外来养护任务一个接口
            outTaskMapper.passYHoutTask(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //删除外来任务(user)
    @RequestMapping(value = "/deleteOutTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject deleteOutTask(@RequestBody String str)throws IOException {
        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);
        try {
            String id=json_parameter.getString("id");
            //获取任务退回原因
            String reason= json_parameter.getString("reason");
            //获取用户名
            String people= json_parameter.getString("people");
            //根据用户名查询角色
            String role=userService.getRole(people);
            System.out.println("该用户的角色是："+role);
            if(role.equals("巡检人员")){
                outtaskService.deleteoutTask(id);
                //增加巡检退回原因
                outTaskMapper.insertXjth(id,reason);
                obj.put("code",1);
                return obj;
            }else {
                outTaskMapper.deleteoutTaskYH(id);
                //增加养护退回原因
                outTaskMapper.insertYhth(id,reason);
                obj.put("code",1);
                return obj;
            }


        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }
    }


    //根据养护id查询经纬度(user)
    @RequestMapping(value = "/getYHtask" , method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getYHtask(@RequestBody String str){

        JSONObject json_parameter = JSON.parseObject(str);

            String yanghuid=json_parameter.getString("yanghuid");

            return taskService.seleLocaByyanghuid(yanghuid);

    }


    //驳回养护任务(admin)
    @RequestMapping(value = "/nopassYHTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject nopassYHTask(@RequestBody String str){

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        System.out.println("驳回养护的参数："+str);
        String id=json_parameter.getString("id");

        try {
            //修改任务表
            taskService.bohuiTask(id);
            //修改养护信息表
            taskService.bohuiYanghuTask(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //管理员删除外来任务(admin)
    @RequestMapping(value = "/delouttask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delouttask(@RequestBody String str){

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        String id=json_parameter.getString("id");

        try {
            outtaskService.deleteouttaskById(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //管理员删除巡检任务(admin)
    @RequestMapping(value = "/delxunjiantask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject delxunjiantask(@RequestBody String str){

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        String id=json_parameter.getString("id");

        try {
            //根据id从数据库中删除
            taskService.deleteyhtask(id);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //驳回外来任务(admin)
    @RequestMapping(value = "/nopassWLTask" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject nopassWLTask(@RequestBody String str){

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        //获取外来任务id
        String id=json_parameter.getString("id");
        //获取驳回的原因
        String bh=json_parameter.getString("bh");
        //System.out.println("获取到的任务id："+id);

        try {
            //根据id查询用户名
            String yhName=outTaskMapper.getYHuserByid(id);
            String xjName=outTaskMapper.getuserByid(id);
            //根据id查询外来任务信息
            String dealafter=outTaskMapper.getdealafter(id);
            //如果该任务的dealafter=0，说明驳回的是巡检任务
            if(dealafter.equals("0")){
                //驳回巡检人员提交的外来任务
                outTaskMapper.BHXJouttask(id,bh);
                String msgid=UUID.randomUUID().toString();
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String now=df.format(new Date());
                //给处理人发消息
                messageService.saveNewmessage(msgid,"您处理的外来任务已被驳回！单号是:"+id+",请迅速处理！驳回的原因是："+bh,xjName,"巡检人员",now);
            }else {
                //驳回养护人员提交的外来任务
                outTaskMapper.BHYHouttask(id,bh);
                String msgid=UUID.randomUUID().toString();
                //获取当前时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String now=df.format(new Date());
                //给处理人发消息
                messageService.saveNewmessage(msgid,"您处理的外来任务已被驳回！单号是:"+id+",请迅速处理！驳回的原因是："+bh,yhName,"养护人员",now);
            }


            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }


    //养护已审核管理员填写处理结果
    @RequestMapping(value = "/CLJG" , method = RequestMethod.POST)
    @ResponseBody
    public JSONObject CLJG(@RequestBody String str){

        JSONObject obj=new JSONObject();
        JSONObject json_parameter = JSON.parseObject(str);

        //获取处理结果的内容
        String id=json_parameter.getString("id");
        String cljg=json_parameter.getString("cljg");

        try {
            outTaskMapper.cjlg(id,cljg);
            obj.put("code",1);
            return obj;
        }catch (Exception e) {
            e.printStackTrace();
            obj.put("code",0);
            return obj;
        }

    }

//    //巡检人员直接上报外来任务(巡检人员)(还没写好)
//    @RequestMapping(value = "/reportWLTask" , method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject reportWLTask(HttpServletRequest request){
//        JSONObject obj=new JSONObject();
//
//        //1.下发一个外来任务
//        String id= UUID.randomUUID().toString();
//        String admin= request.getParameter("people");//上报人
//        String time= request.getParameter("time");
//        String etime=request.getParameter("etime");
//        String origin="内部人员上报";
//        String qinkuang=request.getParameter("qinkuang");
//        String address=request.getParameter("address");
//        String road=request.getParameter("road");
//        String dangerlevel=request.getParameter("dangerlevel");
//        String area=request.getParameter("area");
//        String classify=request.getParameter("classify");
//
//
//        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("pictures");
//        try {
//            //上传情况描述图片
//            MultipartFile file = null;
//            for (int i = 0; i < files.size(); i++) {
//                file=files.get(i);
//                System.out.println("情况描述图片名称：" + file.getOriginalFilename());
//                String filename=id+i+".jpg";
//                File filepath = new File(outadminpath, filename);
//                System.out.println("路径名："+filepath);
//                //判断路径是否存在，没有就创建一个
//                if (!filepath.getParentFile().exists()) {
//                    filepath.getParentFile().mkdirs();
//                }
//                Thumbnails.of(file.getInputStream()).scale(0.8f).outputFormat("jpg").outputQuality(0.5).toFile(filepath);
//            }
//            String num= Integer.toString(files.size());
//
//            outTaskMapper.saveOuttask(id,admin,outadminpath,time,etime,origin,qinkuang,address,road,num,dangerlevel,area,classify);
//            //2.巡检人员接取任务
//            //根据巡检用户名查询电话号码
//            String tel=userMapper.getTel(admin);
//            outTaskMapper.receiveTask(id,admin,tel);
//            //3.上报问题，这里不用拍照片,但肯定是管理处
//            String dangerlevel=request.getParameter("dangerlevel");
//            String belong="1";
//            outTaskMapper.finishoutTaskdealafter(id,belong,"需要养护班组处理",dangerlevel);
//            obj.put("code",1);
//            return obj;
//        } catch (Exception e) {
//            e.printStackTrace();
//            obj.put("code",0);
//            return obj;
//        }
//
//
//    }


}
