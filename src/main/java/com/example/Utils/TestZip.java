package com.example.Utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class TestZip {
    public static void main(String[] args) {

        //TestZip.downLoadList1(response);

    }

    public static void downLoadList1(HttpServletResponse response) throws UnsupportedEncodingException {

        List<String> pathList=new ArrayList<>();
        pathList.add("C:\\Users\\房艺伟\\Desktop\\GIS\\test\\1.docx");
        pathList.add("C:\\Users\\房艺伟\\Desktop\\GIS\\test\\2.docx");
        pathList.add("C:\\Users\\房艺伟\\Desktop\\GIS\\test\\3.docx");
        pathList.add("C:\\Users\\房艺伟\\Desktop\\GIS\\test\\4.docx");
        //
        String uuid= UUID.randomUUID().toString();
        String zipfileName = uuid + ".zip";

        response.reset();
        response.setHeader("Content-Disposition","attachment; filename=" + zipfileName);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");

        try(ZipOutputStream zipOutputStream=new ZipOutputStream(new BufferedOutputStream(response.getOutputStream()))){
            for(String pathName:pathList){
                File file =new File(pathName);
                String fileName=file.getName();
                zipOutputStream.putNextEntry(new ZipEntry(fileName));
                try(BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file))){
                    byte[] bytes = new byte[1024];
                    int i=0;
                    while((i=bis.read(bytes))!=-1){
                        zipOutputStream.write(bytes,0,i);
                    }
                    zipOutputStream.closeEntry();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
