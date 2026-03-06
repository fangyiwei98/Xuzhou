package com.example.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.Dao.UserMapper;
import com.example.POJO.USERINFO;
import com.example.POJO.excelUser;
import com.example.Service.ServiceImpl.excelServiceImpl;
import com.example.Service.UserService;
import com.example.Service.excelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/myExcel")
@CrossOrigin(origins = "*", maxAge = 3600)
@MultipartConfig
public class excelController {
    @Autowired
    private excelService excelService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @ResponseBody
    @RequestMapping(value="/excelimport",method = RequestMethod.POST)
    public JSONObject importUser(@RequestParam(name="file") MultipartFile file) throws Exception {
        JSONObject obj=new JSONObject();
        try {
            //先把所有用户都删了
            userMapper.deleteAllUser();
            //新建超级管理员
            String SVIPid=UUID.randomUUID().toString();
            String SVIPpwd="hhu12345!";
            String md5Password = DigestUtils.md5DigestAsHex(SVIPpwd.getBytes());
            userMapper.insetSVIP(SVIPid,"superadmin","13045459867","超级管理员",md5Password);

            //先把道路表和link表里的东西全删了
            userService.deletefengong();
            userService.deletelink();
            //1.解析Excel
            //1.1.根据Excel文件创建工作簿
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            //1.2.获取Sheet
            Sheet sheet = wb.getSheetAt(0);//参数：索引
            //1.3.获取Sheet中的每一行，和每一个单元格
            //2.获取用户数据列表
            List<excelUser> list = new ArrayList<>();
            System.out.println(sheet.getLastRowNum());
            for (int rowNum = 1; rowNum<= sheet.getLastRowNum() ;rowNum ++) {
                //System.out.println("开始遍历行了！");
                Row row = sheet.getRow(rowNum);//根据索引获取每一个行
                String [] values = new String[row.getLastCellNum()];
                for(int cellNum=1;cellNum< row.getLastCellNum(); cellNum ++) {
                    //System.out.println("开始遍历列了！");
                    String value="";
                    //电话号码
                    if(cellNum==2){
                        /*Cell cell=row.getCell(cellNum);
                        //获取到的数据转字符串
                        String str1=row.getCell(cellNum).toString();
                        System.out.println("获取到的电话号码是："+str1);

                        String str2= str1.replaceAll("\\.","");
                        value =str2.substring(0,11);
                        System.out.println("存到数据库里的电话号码是："+value);*/
                        //value= String.valueOf(row.getCell(cellNum).getNumericCellValue());

                        Cell cell=row.getCell(cellNum);
                        if(cell != null){
                            //cell.setCellType(Cell.CELL_TYPE_STRING);之前版本
                            cell.setCellType(CellType.STRING);
                        }
//                        DataFormatter formatter = new DataFormatter();
//                        String r = formatter.formatCellValue(rowValue.getCell(0));
                        String r=cell.getStringCellValue();
                        Double t = Double.valueOf(r);
                        BigDecimal phone = new BigDecimal(t);
                        value=phone.toString().replace(" ","");
                        //System.out.println("处理后的电话号码是："+phone);

                    }else {
                        //Cell cell=row.getCell(cellNum);
                        //System.out.println("cell的类型111是："+cell.getCellType());
                        //value = row.getCell(cellNum).toString();
                        Cell cell=row.getCell(cellNum);
                        if(cell != null){
                            //cell.setCellType(Cell.CELL_TYPE_STRING);之前版本
                            cell.setCellType(CellType.STRING);
                        }
                        String r=cell.getStringCellValue();
                        //把所有空格去掉（防止前端输入没注意）
                        value=r.replace(" ","");
                    }
                    //System.out.println("value是："+value);
                    //Object value = getCellValue(cell);
                    values[cellNum] = value;
                    //System.out.println(values[cellNum]);
                }
                //需要判断一下人员类型
                if(values[3].equals("巡检人员")){
                    //System.out.println("qqqqq");
                    excelUser user = new excelUser();
                    System.out.println(values[1]+values[2]+values[3]+values[4]+values[5]);
                    //此处的id没有用
                    user.setId("11111");
                    user.setName(values[1]);
                    user.setTel(values[2]);
                    user.setRole(values[3]);
                    user.setRoad(values[4]);
                    user.setQizhi(values[5]);
                    System.out.println(user);
                    list.add(user);

                }else {
                    //是养护人员或管理人员
                    String id= UUID.randomUUID().toString();
                    //若数据库里没有该用户，直接插入数据库
                    if(userService.getInfoByname(values[1])==null){
                        userService.registerUser(id,values[1],DigestUtils.md5DigestAsHex("123456".getBytes()),values[3],values[2] );
                    }else {
                        //上传excel存在用户不更新密码
                        userService.uploaduser(values[1],values[2],values[3] );
                    }
                }

            }
            //3.批量保存用户
            excelService.save(list);
            //4.在所有操作结束后加一个不明确路段的选项，并和所有人员关联起来
            String specialid="unknownroadId";
            String speciallinkid=UUID.randomUUID().toString();
            userMapper.insertfengong(specialid,"不明确路段","不明确路段起止点");
            //查询所有巡检人员的名字并和link表关联起来
            List<String> namelist=userMapper.getnamebyrole("巡检人员");
            for (int i = 0; i < namelist.size(); i++) {
                userMapper.insertLink(speciallinkid,namelist.get(i),specialid);
            }

            obj.put("code",1);
            return obj;
        }catch (Exception e){
            obj.put("code",0);
            return obj;
        }
    }

    /*//格式装换
    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        Object cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }*/
}
