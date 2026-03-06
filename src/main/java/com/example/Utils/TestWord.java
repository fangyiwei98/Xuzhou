package com.example.Utils;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureRenderData;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestWord {
    public static void main(String[] args) throws IOException {
        //这是生成市井盖办单表的
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            // 文本
            data.put("id", "这是工单id");
            data.put("origin","市井盖办");
            data.put("road","中山南路");
            String belong="1";
            if (belong.equals("1")) {
                data.put("quanshu","属于市管排水井");
            }else if (belong.equals("3")) {
                data.put("quanshu","不属于市管排水井");
            }else {
                data.put("quanshu","quanshu字段");//放quanshu字段里的内容
            }

            data.put("time", "下发时间");
            data.put("etime","截止时间");

            data.put("admin", "下发人");
            data.put("poeple","巡检人");
            data.put("yhpeople","养护人");
            data.put("classify","问题分类");
            String dangerlevel="1";//1：一般，2。较大3.重大4.特派
            if (dangerlevel.equals("1")) {
                data.put("dangerlevel","一般");
            }else if (dangerlevel.equals("2")) {
                data.put("dangerlevel","较大");
            }else if (dangerlevel.equals("3")) {
                data.put("dangerlevel","重大");
            }else{
                data.put("dangerlevel","特派");
            }

            data.put("type","窨井类型");
            data.put("address","地址描述");
            data.put("qinkuang","情况描述");
            data.put("xjreporttime","2023-07-29 19:47:13");
            data.put("xjreturnword","巡检返回结果");
            data.put("reporttime","2023-07-29 19:47:13");
            data.put("returnword","养护返回结果");

            //图片
            data.put("xjresult", new PictureRenderData(127, 185, "C:\\Users\\房艺伟\\Desktop\\111.png"));
            data.put("yhresult", new PictureRenderData(127, 185, "C:\\Users\\房艺伟\\Desktop\\222.png"));
            // 写入word输出
            System.out.println("C:\\Users\\房艺伟\\Desktop\\GIS\\test"+"\\wordTest.docx");
            Resource resource = new ClassPathResource("templates/sjgbdb.docx");
            File file1 = resource.getFile();
            //填充word
            XWPFTemplate xwpfTemplate = XWPFTemplate.compile(file1).render(data);
            String docName = "C:\\Users\\房艺伟\\Desktop\\GIS\\test\\"+data.get("id") + ".docx";   //生成word路径
            File targetFile = new File(docName);                          //新建File文件对象
            FileOutputStream out = new FileOutputStream(targetFile);     //FileOutputStream文件字节输出流(这里会自动覆盖之前的)
            xwpfTemplate.write(out);
            out.flush();          //刷新
            out.close();         //关流
            xwpfTemplate.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
