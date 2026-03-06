package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.deepoove.poi.data.PictureRenderData;
import com.example.Dao.OutTaskMapper;

import com.example.POJO.OUTTASK;
import io.lettuce.core.ScriptOutputType;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import com.deepoove.poi.XWPFTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class wordController {


    @Autowired
    private OutTaskMapper outTaskMapper;

    @ResponseBody
    @RequestMapping(value="/createword",method = RequestMethod.POST)
    public JSONObject createword(HttpServletResponse response,@RequestBody Map aaa) throws Exception {
        //1.获取前端传的参数(是一个字符串数组)
        List<String> idlist= (List<String>) aaa.get("ids");
        String printtype= (String)aaa.get("type");

        System.out.println("获取到的id数组是："+idlist);
        System.out.println("数组的长度是："+idlist.size());
        System.out.println("获取到的type是："+printtype);
        JSONObject obj=new JSONObject();
        try {
            //printtype=1表示打印的是单表或者批量下载（压缩包）
            if (printtype.equals("1")) {
                //1.遍历单号数组,并生成word文档
                for (int i = 0; i < idlist.size(); i++) {
                    //System.out.println(idlist.get(i));
                    //2.判断该任务的来源
                    //2.1根据单号查询来源
                    String origin=outTaskMapper.getorigin(idlist.get(i));
                    //System.out.println("来源是:"+origin);
                    switch (origin) {
                        case "12345":
                            //生成12345的单表
                            try {
                                //System.out.println("进到第二次循环里了！");
                                Map<String, Object> data = new HashMap<String, Object>();
                                List<OUTTASK> outtasks=outTaskMapper.getallinfo(idlist.get(i));
                                //System.out.println("第二次查询到的外来任务是："+outtasks);
                                data.put("id",outtasks.get(0).getId());
                                data.put("qinkuang",outtasks.get(0).getQinkuang());
                                data.put("cljg",outtasks.get(0).getCljg());
                                data.put("result", new PictureRenderData(127, 185, "D://outfinish//"+outtasks.get(0).getId()+"finish0.jpg"));
                                //System.out.println("获取到的图片路径是："+"D://outfinish//"+outtasks.get(i).getId()+"finish0.jpg");
                                //模板类型
                                data.put("printtype","12345工单");
                                //System.out.println(data);
                                printword(data);
                                //跳出当前循环，执行下次循环。注意这里不能用break！
                                //continue;
                                //这边不用返回code，因为还需要执行下面的代码！
                            } catch (IOException e) {
                                obj.put("code", 0);
                                obj.put("result","生成12345单表失败！");
                                return obj;
                            }finally {
                                break;
                            }

                        case "数字化":
                            //打印数字化的单表
                            try {
                                Map<String, Object> data = new HashMap<String, Object>();
                                List<OUTTASK> outtasks=outTaskMapper.getallinfo(idlist.get(i));
                                data.put("id",outtasks.get(0).getId());
                                data.put("time",outtasks.get(0).getTime());
                                data.put("reporttime",outtasks.get(0).getReporttime());
                                data.put("etime",outtasks.get(0).getEtime());
                                //
                                data.put("xjresult", new PictureRenderData(127, 185, "D://outfinish//"+outtasks.get(0).getId()+"xjfinish0.jpg"));
                                data.put("yhresult", new PictureRenderData(127, 185, "D://outfinish//"+outtasks.get(0).getId()+"finish0.jpg"));
                                //模板类型
                                data.put("printtype","数字化单表");
                                printword(data);
                                //continue;

                            } catch (IOException e) {
                                obj.put("code", 0);
                                obj.put("result","生成数字化单表失败！");
                                return obj;
                            }finally {
                                //continue;
                                break;
                            }

                        case "市井盖办":
                            //打印市井盖办的单表
                            try {
                                Map<String, Object> data = new HashMap<String, Object>();
                                List<OUTTASK> outtasks=outTaskMapper.getallinfo(idlist.get(i));
                                data.put("id",outtasks.get(0).getId());
                                data.put("origin",outtasks.get(0).getOrigin());
                                data.put("road",outtasks.get(0).getRoad());
                                data.put("belong",outtasks.get(0).getBelong());
                                if (data.get("belong").equals("2")) {
                                    data.put("quanshu",outtasks.get(0).getQuanshu());
                                }

                                data.put("time",outtasks.get(0).getTime());
                                data.put("etime",outtasks.get(0).getEtime());
                                data.put("admin",outtasks.get(0).getAdmin());
                                data.put("people",outtasks.get(0).getPeople());
                                data.put("yhpeople",outtasks.get(0).getYhpeople());
                                data.put("classify",outtasks.get(0).getClassify());
                                data.put("dangerlevel",outtasks.get(0).getDangerlevel());
                                data.put("type",outtasks.get(0).getType());
                                data.put("address",outtasks.get(0).getAddress());
                                data.put("qinkuang",outtasks.get(0).getQinkuang());
                                data.put("xjreporttime",outtasks.get(0).getXjreporttime());
                                data.put("xjreturnword",outtasks.get(0).getXjreturnword());
                                data.put("reporttime",outtasks.get(0).getReporttime());
                                data.put("returnword",outtasks.get(0).getReturnword());
                                //
                                data.put("xjresult", new PictureRenderData(127, 185, "D://outfinish//"+outtasks.get(0).getId()+"xjfinish0.jpg"));
                                data.put("yhresult", new PictureRenderData(127, 185, "D://outfinish//"+outtasks.get(0).getId()+"finish0.jpg"));
                                //模板类型
                                data.put("printtype","sjgbdb");
                                printword(data);
                                //continue;

                            } catch (IOException e) {
                                obj.put("code", 0);
                                obj.put("result","生成市井盖办单表失败！");
                                return obj;
                            }finally {
                                break;
                            }

                            //continue;
//                        default:
//                            //否则打印失败
//
//                            obj.put("code", 0);
//                            obj.put("result","生成单表来源错误！");
//                            return obj;
                    }


                }
                //System.out.println("跳出循环了没？");
                //2.如果传的不止一个任务，需要批量下载
                if (idlist.size()>1) {
                    //2.1定义一个路径列表
                    List<String> pathList=new ArrayList<>();
                    //2.2遍历前端传的数组
                    for (int i = 0; i < idlist.size(); i++) {
                        //2.3将每个单号生成的word文档路径放入路径列表中
                        String docName = "D://outfinish//"+idlist.get(i) + ".docx";
                        pathList.add(docName);
                        //将下载状态改为已下载
                        outTaskMapper.downloadstatue(idlist.get(i));
                    }
                    //2.4将路径列表打包并下载
                    downLoadList(response,pathList);
                    obj.put("code",1);
                    return obj;
                }else {
                    //3.单个文件直接获取id并下载
                    String docName = "D://outfinish//"+idlist.get(0) + ".docx";
                    downLoad(docName,response);
                    //将下载状态改为已下载
                    outTaskMapper.downloadstatue(idlist.get(0));
                    obj.put("code",1);
                    return obj;
                }
            }else if(printtype.equals("2")){
                //printtype=2表示打印的是目录表

                //1.先定义3个来源任务的数组
                List<OUTTASK> ids12345=new ArrayList<>();
                List<OUTTASK> shuzihuaids=new ArrayList<>();
                List<OUTTASK> SJGBids=new ArrayList<>();
                //2.将前端传的数组分类好
                for (int i = 0; i < idlist.size(); i++) {
                    //1.根据id查询来源
                    String origin=outTaskMapper.getorigin(idlist.get(i));
                    switch (origin) {
                        case "12345":
                            //根据id查询任务所有信息并增添至列表中
                            ids12345.add(outTaskMapper.getallinfobyid(idlist.get(i)));
                            break;
                        case "数字化":
                            shuzihuaids.add(outTaskMapper.getallinfobyid(idlist.get(i)));
                            break;
                        case "市井盖办":
                            SJGBids.add(outTaskMapper.getallinfobyid(idlist.get(i)));
                            break;
                        default:
                            System.out.println("该任务的来源有问题!");
                            break;
                    }
                }
                //3.先判断这三个数组是否都是空，不为空打印目录(这边成功不用返回，只是中间过程)
                System.out.println("12345表单的个数："+ids12345.size());
                System.out.println("数字化表单的个数："+shuzihuaids.size());
                System.out.println("市井盖办表单的个数："+SJGBids.size());
                int count=0;
                //有12345的工单需要生成目录
                if (ids12345.size()>0) {
                    try {
                        printmulu(ids12345,"mulu12345");
                    }catch (Exception e){
                        obj.put("code",0);
                        obj.put("result","打印12345目录失败！");
                        return obj;
                    }
                    count=count+1;
                }
                //有数字化的工单需要生成目录
                if (shuzihuaids.size()>0) {
                    try {
                        printmulu(shuzihuaids,"szhmulu");
                    }catch (Exception e){
                        obj.put("code",0);
                        obj.put("result","打印数字化目录失败！");
                        return obj;
                    }
                    count=count+1;
                }
                //有市井盖办的工单需要生成目录
                if (SJGBids.size()>0){
                    try {
                        printsjgbmulu(SJGBids);
                    }catch (Exception e){
                        obj.put("code",0);
                        obj.put("result","打印市井盖办目录失败！");
                        return obj;
                    }
                    count=count+1;
                }
                System.out.println("count:"+count);
                //4.根据count判断是否需要打包
                if(count>1){
                    System.out.println("excel需要打包下载！");
                    //4.1需要打包并下载
                    //4.1.1定义一个路径列表
                    List<String> pathList=new ArrayList<>();
                    //4.1.2将生成的目录表插入路径列表中
                    if (ids12345.size()>0) {
                        //有12345的目录
                        String excelpath="D://outfinish//mulu12345.xls";
                        pathList.add(excelpath);
                    }
                    if (shuzihuaids.size()>0) {
                        //有数字化的目录
                        String excelpath="D://outfinish//szhmulu.xls";
                        pathList.add(excelpath);
                    }
                    if (SJGBids.size()>0) {
                        //有数字化的目录
                        String excelpath="D://outfinish//sjgb.xls";
                        pathList.add(excelpath);
                    }
                    //4.1.2将生成的路径打包并下载
                    downLoadList(response,pathList);
                    obj.put("code",1);
                    return obj;
                }else {
                    //不需要打包，直接下载单个目录
                    System.out.println("excel不需要打包下载！");
                    try {
                        if (ids12345.size()>0) {
                            //有12345的目录
                            String excelpath="D://outfinish//mulu12345.xls";
                            downLoad(excelpath,response);
                        }else if (shuzihuaids.size()>0) {
                            //有数字化的目录
                            String excelpath="D://outfinish//szhmulu.xls";
                            downLoad(excelpath,response);
                        }else if (SJGBids.size()>0) {
                            //有数字化的目录
                            String excelpath="D://outfinish//sjgb.xls";
                            downLoad(excelpath,response);
                        }
                        obj.put("code",1);
                        return obj;
                    }catch (Exception e){
                        obj.put("code",0);
                        obj.put("result","生成单个目录表失败！");
                        return obj;
                    }

                }

            }else {
                obj.put("code",0);
                obj.put("result","输入的type不是1或2！");
                return obj;
            }


        }catch (Exception e){
            obj.put("code",0);
            obj.put("result","最外面报异常了！");
            return obj;
        }
    }

    //打印word单表
    private static void printword(Map<String, Object> data) throws IOException {
        System.out.println("进到打印方法里来了！");


//        ApplicationHome ah = new ApplicationHome(wordController.class);
//        File file = ah.getSource();
//        String s = file.getParentFile().getPath() + "\\templates\\"+data.get("printtype")+".docx";

        //System.out.println("获取到的文件路径是:"+s);
        //File file = ResourceUtils.getFile("classpath:templates/"+data.get("printtype")+".docx");
//        Resource resource = new ClassPathResource("templates/"+data.get("printtype")+".docx");
//        System.out.println("这是单表的路径："+"templates/"+data.get("printtype")+".docx");
//        File file1 = resource.getFile();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/"+data.get("printtype")+".docx");

        //System.out.println("填充word之前！");
        //填充word
        XWPFTemplate xwpfTemplate = XWPFTemplate.compile(inputStream).render(data);
        //System.out.println("开始打印word了！");
        String docName = "D://outfinish//"+data.get("id") + ".docx";   //生成word路径
        File targetFile = new File(docName);                          //新建File文件对象
        FileOutputStream out = new FileOutputStream(targetFile);     //FileOutputStream文件字节输出流(这里会自动覆盖之前的)
        xwpfTemplate.write(out);
        out.flush();          //刷新
        out.close();         //关流
        xwpfTemplate.close();
        //System.out.println("生成word的方法执行完毕!");
    }

    //打印12345和数字化目录
    private static void printmulu(List<OUTTASK> ids,String printtype) throws IOException {
        //Resource resource = new ClassPathResource("templates//"+printtype+".xls");
        //File file1 = resource.getFile();
        System.out.println("进到打印12345或数字化目录的方法里来了！");
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/"+printtype+".xls");
        //System.out.println("没获取到参数？");
        File targetFile = new File("D:\\outfinish\\"+printtype+".xls");
        try (InputStream fi = inputStream;//file1
             FileOutputStream fo = new FileOutputStream(targetFile);
             HSSFWorkbook hw = new HSSFWorkbook(fi);) {

            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            //模拟要插入的数据
            //1.遍历行，这里的4指的是行数
            for(int i=1;i<ids.size()+1;i++) {
                String[] strarr = new String[8];

                for(int j=0;j<strarr.length;j++) {
                    if (j == 0) {
                        strarr[j]=i+"";
                    }else if(j==1){
                        strarr[j]=ids.get(i-1).getId();
                        //strarr[j]="单号";
                    }else if(j==2){
                        String belong=ids.get(i-1).getBelong();
                        if (belong.equals("1")) {
                            strarr[j]="属于市管排水井";
                        }else if (belong.equals("3")) {
                            strarr[j]="不属于市管排水井";
                        }else {
                            strarr[j]=ids.get(i-1).getQuanshu();//放quanshu字段里的内容
                        }

                    }else if(j==4){
                        strarr[j]=ids.get(i-1).getCljg();//"处理结果"
                    }else {
                        strarr[j]="";
                    }


                }
                arrayList.add(strarr);
            }
            System.out.println("12345或数字化填写内容内容："+arrayList);
            // 在指定地方插入指定行数据
            insertRows(hw, 1, arrayList,Boolean.TRUE);
            hw.write(fo);
            //System.out.println("生成excel表了！");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //打印市井盖办目录
    private static void printsjgbmulu(List<OUTTASK> ids) throws IOException {
        //1.获取模板
        //Resource resource = new ClassPathResource("templates/sjgb.xls");
        //File file1 = resource.getFile();
        System.out.println("传的id数组是："+ids);
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/sjgb.xls");
        File targetFile = new File("D:\\outfinish\\sjgb.xls");
        try (InputStream fi = inputStream;//file1
             FileOutputStream fo = new FileOutputStream(targetFile);
             HSSFWorkbook hw = new HSSFWorkbook(fi);) {

            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            //模拟要插入的数据
            //1.遍历行，这里的4指的是行数
            for(int i=1;i<ids.size()+1;i++) {
                String[] strarr = new String[10];

                for(int j=0;j<strarr.length;j++) {
                    if (j == 0) {
                        strarr[j]=i+"";
                    }else if(j==1){
                        strarr[j]=ids.get(i-1).getReporttime();
                        //System.out.println(strarr[j]);
                    }else if(j==2){
                        strarr[j]=ids.get(i-1).getRoad();
                        //System.out.println(strarr[j]);
                    }else if(j==3){
                        String belong=ids.get(i-1).getBelong();
                        if (belong.equals("1")) {
                            strarr[j]="属于市管排水井";
                        }else if (belong.equals("3")) {
                            strarr[j]="不属于市管排水井";
                        }else {
                            strarr[j]=ids.get(i-1).getQuanshu();//放quanshu字段里的内容
                        }
                    }else if (j == 4) {
                        strarr[j]=ids.get(i-1).getAddress();
                        //System.out.println(strarr[j]);
                    }else if (j == 5) {
                        strarr[j]=ids.get(i-1).getClassify();//放classify里的内容
                        //System.out.println(strarr[j]);
                    }else if (j == 6) {
                        //插入问题图片
                        String xjurl="D:\\outfinish\\"+ids.get(i-1).getId()+"xjfinish0.jpg";
                        insertPic(hw,i,j,xjurl);
                    }else if (j == 7) {
                        //插入维护后图片
                        String yhurl="D:\\outfinish\\"+ids.get(i-1).getId()+"finish0.jpg";
                        insertPic(hw,i,j,yhurl);
                    }else if (j == 8) {
                        strarr[j]=ids.get(i-1).getId();
                    }else {
                        strarr[j]="";
                    }

                }
                arrayList.add(strarr);
            }
            //System.out.println(arrayList);
            // 在指定地方插入指定行数据
            //System.out.println("插入数据之前");
            insertRows(hw, 1, arrayList,Boolean.FALSE);
            //System.out.println("插入数据之后");
            hw.write(fo);
            //System.out.println("生成excel表执行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //往excel表里插入图片
    private static void insertPic(HSSFWorkbook hw,int i,int j,String url){
        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        BufferedImage bufferImg = null;
        try{
            //System.out.println("获取的图片路径是："+url);
            //第一种方式: url地址
            File file = new File(url);
            if(!file.exists() || file.length() == 0) {
                //System.out.println("文件为空！");
                return;
            }
            bufferImg = ImageIO.read(file);
            // 将图片写入流中
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            // 利用HSSFPatriarch将图片写入EXCEL
            HSSFSheet hssfSheet = hw.getSheetAt(0);
            HSSFPatriarch patriarch = hssfSheet.createDrawingPatriarch();
            //图片一导出到单元格中
            HSSFClientAnchor anchor = new HSSFClientAnchor(50, 50, 0, 0,
                    (short) j, i+1, (short) (j+1), i+2);
            // 插入图片
            patriarch.createPicture(anchor, hw.addPicture(byteArrayOut
                    .toByteArray(), HSSFWorkbook.PICTURE_TYPE_PNG));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 在指定地方插入指定行数据
     * @param hw  EXCEL文件
     * @param starRow 插入数据开始行
     * @param arrayList  插入的数据
     */
    private static void insertRows(HSSFWorkbook hw, int starRow, ArrayList<String[]> arrayList,Boolean isMerge) {
        HSSFSheet sheet = hw.getSheetAt(0);
        sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), arrayList.size(), true, false);
        for (int i = 0; i < arrayList.size(); i++) {
            HSSFRow sourceRow = null;
            HSSFRow targetRow = null;
            HSSFCell sourceCell = null;
            HSSFCell targetCell = null;

            sourceRow = sheet.getRow(starRow + i);
            targetRow = sheet.createRow(starRow + 1 + i);
            targetRow.setHeight(sourceRow.getHeight());

            String[] strings = arrayList.get(i);
            for (int m = 0; m < strings.length; m++) {
                sourceCell = sourceRow.getCell(m);
                targetCell = targetRow.createCell(m);
                targetCell.setCellStyle(sourceCell.getCellStyle());
                targetCell.setCellValue(strings[m]);
            }
        }
        //System.out.println("合并单元格之前！");
        if (isMerge) {
            //合并一下单元格
            for (int i = 0; i < arrayList.size(); i++) {
                CellRangeAddress region = new CellRangeAddress(i+2, i+2, 2, 3);
                CellRangeAddress region1 = new CellRangeAddress(i+2, i+2, 4, 5);
                sheet.addMergedRegion(region);
                sheet.addMergedRegion(region1);
            }
        }

        //System.out.println("插入数据完毕！");


    }

    //单文件下载
    public static void downLoad(String path, HttpServletResponse response) throws UnsupportedEncodingException {

        File file=new File(path);
        String fileName= file.getName();

        response.reset();
        response.setHeader("Content-Disposition","attachment; filename=" + fileName);
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int)file.length());
        response.setContentType("application/octet-stream");
        System.out.println("filename:"+fileName);
        try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));OutputStream  outputStream = response.getOutputStream();)
        {

            byte[] bytes = new byte[1024];
            int i=0;
            while((i=bis.read(bytes))!=-1)
            {
                outputStream.write(bytes,0,i);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


    //压缩包下载
    public static void downLoadList(HttpServletResponse response,List<String> pathList) throws UnsupportedEncodingException {

        //System.out.println("进入到批量下载方法里了！");
        //定义压缩包的名字
        String uuid= UUID.randomUUID().toString();
        String zipfileName = uuid+".zip";

        response.reset();
        response.setHeader("Content-Disposition","attachment; filename=" + zipfileName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        //System.out.println("response执行完成！");
        try(ZipOutputStream zipOutputStream=new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()))){
            for(String pathName:pathList){
                File file =new File(pathName);
                String fileName=file.getName();
                //System.out.println("需要打包的文件名字是："+fileName);
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file))){
                    byte[] bytes = new byte[1024];
                    int i=0;
                    while((i=bis.read(bytes))!=-1){
                        zipOutputStream.write(bytes,0,i);
                    }
                    zipOutputStream.closeEntry();
                }catch (Exception e){
                    //System.out.println("应该没有异常吧？");
                    e.printStackTrace();
                }
            }
            System.out.println("打包下载完成！");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}


