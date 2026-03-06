package com.example.Utils;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;

public class TestExcel {
    public static void main(String[] args) throws IOException {
        //File file = new File("C:\\Users\\房艺伟\\Desktop\\GIS\\表模板\\市井盖办.xls");
        //1.获取模板
//        Resource resource = new ClassPathResource("templates/sjgb.xls");
//        File file1 = resource.getFile();
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/sjgb.xls");
        File targetFile = new File("C:\\Users\\房艺伟\\Desktop\\GIS\\test\\SJGBtest.xls");
        try (InputStream fi = inputStream;
             FileOutputStream fo = new FileOutputStream(targetFile);
             HSSFWorkbook hw = new HSSFWorkbook(fi);) {

            ArrayList<String[]> arrayList = new ArrayList<String[]>();
            //模拟要插入的数据
            //1.遍历行，这里的4指的是行数
            for(int i=1;i<4;i++) {
                String[] strarr = new String[10];

                for(int j=0;j<strarr.length;j++) {
                    if (j == 0) {
                        strarr[j]=i+"";
                    }else if(j==1){
                        strarr[j]="排查日期";
                    }else if(j==2){
                        strarr[j]="路段";
                    }else if(j==3){
                        String belong="2";
                        if (belong.equals("1")) {
                            strarr[j]="属于市管排水井";
                        }else if (belong.equals("3")) {
                            strarr[j]="不属于市管排水井";
                        }else {
                            strarr[j]="quanshu字段里的内容";//放quanshu字段里的内容
                        }
                    }else if (j == 4) {
                        strarr[j]="具体位置";
                    }else if (j == 5) {
                        strarr[j]="问题分类";//放classify里的内容
                    }else if (j == 6) {
                        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        BufferedImage bufferImg = null;
                        try{
                            //第一种方式: url地址
                            File file = new File("C:\\Users\\房艺伟\\Desktop\\111.png");
                            bufferImg = ImageIO.read(file);
                            // 将图片写入流中
                            ImageIO.write(bufferImg, "png", byteArrayOut);
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

                    }else if (j == 7) {
                        // 先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        BufferedImage bufferImg = null;
                        try{
                            //第一种方式: url地址
                            File file = new File("C:\\Users\\房艺伟\\Desktop\\111.png");
                            bufferImg = ImageIO.read(file);
                            // 将图片写入流中
                            ImageIO.write(bufferImg, "png", byteArrayOut);
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
                    }else if (j == 8) {
                        strarr[j]="单号";
                    }else {
                        strarr[j]="";
                    }


                }
                arrayList.add(strarr);
            }
            // 在指定地方插入指定行数据
            insertRows(hw, 1, arrayList);


            hw.write(fo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 在指定地方插入指定行数据
     * @param hw  EXCEL文件
     * @param starRow 插入数据开始行
     * @param arrayList  插入的数据
     */
    private static void insertRows(HSSFWorkbook hw, int starRow, ArrayList<String[]> arrayList) {
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
//        //合并一下单元格
//        for (int i = 0; i < arrayList.size(); i++) {
//            CellRangeAddress region = new CellRangeAddress(i+2, i+2, 2, 3);
//            CellRangeAddress region1 = new CellRangeAddress(i+2, i+2, 4, 5);
//            sheet.addMergedRegion(region);
//            sheet.addMergedRegion(region1);
//        }




    }


}


