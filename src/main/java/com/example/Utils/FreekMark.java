package com.example.Utils;

import com.deepoove.poi.XWPFTemplate;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import org.springframework.stereotype.Component;
import java.io.*;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipOutputStream;

/**
 * pdf 导出工具类
 */
@Component
@Slf4j
public final class FreekMark {
    /**
     * 根据docx模板填充数据  并生成pdf文件
     *
     * @param dataMap      数据源
     * @param docxFile     docx模板的文件名
     * @return 生成的文件路径
     */
    public static byte[] createDocx2Pdf(Map<String, Object> dataMap, String docxFile) {
        //输出word文件路径和名称 (临时文件名，本次为测试，最好使用雪花算法生成，或者用uuid)
        String fileName = UUID.randomUUID().toString() + ".docx";

        // word 数据填充
        // 生成docx临时文件
        final File tempPath = new File(fileName);
        final File docxTempFile = getTempFile(docxFile);
        XWPFTemplate template = XWPFTemplate.compile(docxTempFile).render(dataMap);
        try {
            template.write(new FileOutputStream(tempPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

//        // word转pdf
//        final String pdfFile = convertDocx2Pdf(fileName);
//        return getFileOutputStream(new File(pdfFile)).toByteArray();
        return getFileOutputStream(tempPath).toByteArray();

    }



    /**
     * 文件转字节输出流
     *
     * @param outFile 文件
     * @return
     */
    public static ByteArrayOutputStream getFileOutputStream(File outFile) {
        // 获取生成临时文件的输出流
        InputStream input = null;
        ByteArrayOutputStream bytestream = null;
        try {
            input = new FileInputStream(outFile);
            bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = input.read()) != -1) {
                bytestream.write(ch);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bytestream.close();
                input.close();
                log.info("删除临时文件");
                if (outFile.exists()) {
                    outFile.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytestream;
    }

    /**
     * 获取资源文件的临时文件
     * 资源文件打jar包后，不能直接获取，需要通过流获取生成临时文件
     *
     * @param fileName 文件路径 templates/xxx.docx
     * @return
     */
    public static File getTempFile(String fileName) {
        final File tempFile = new File(fileName);
        InputStream fontTempStream = null;
        try {
            fontTempStream = FreekMark.class.getClassLoader().getResourceAsStream(fileName);
            FileUtils.copyInputStreamToFile(fontTempStream, tempFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fontTempStream != null) {
                    fontTempStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tempFile;
    }



}




