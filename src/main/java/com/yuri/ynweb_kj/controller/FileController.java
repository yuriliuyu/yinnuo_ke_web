package com.yuri.ynweb_kj.controller;

import com.yuri.ynweb_kj.pojo.KeSchool;
import com.yuri.ynweb_kj.pojo.KeUser;
import com.yuri.ynweb_kj.service.UserService;
import com.yuri.ynweb_kj.utils.EnumGender;
import com.yuri.ynweb_kj.utils.EnumResCode;
import com.yuri.ynweb_kj.utils.FileUtils;
import com.yuri.ynweb_kj.vo.BaseJsonResultVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = {"/fileapi"})
public class FileController {

    @Autowired
    UserService userService;
    @Value("${web.upload-path}")
    private String uploadPath;
    @Value("${web.server-url}")
    private String webServerUrl;

    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    @RequestMapping(value = "/generuser", method = RequestMethod.POST)
    public BaseJsonResultVO contentListCount() {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        FileController excelUtil = new FileController();
        File excelFile = new File("/Users/huangxiaoqi/Desktop/user.xlsx"); // 创建文件对象
        List<KeUser> userList = excelUtil.GenerateUser(excelFile, userService);
        for(KeUser user: userList){
            KeUser u = userService.findUserByName(user.getName());
            if(u != null){
                System.out.println(u.getName() + " 已经有同名用户，请更换姓名后再重新插入");
            }else{
                userService.insertUser(user);
            }
        }
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        return vo;
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public BaseJsonResultVO uploadFile(@RequestParam MultipartFile file, HttpSession session, HttpServletRequest request, Map<Object, Object> map) {
        BaseJsonResultVO vo = new BaseJsonResultVO();
        map.put("webServer", webServerUrl);
        //String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
//        System.out.println("fileName-->" + fileName);
//        System.out.println("getContentType-->" + contentType);
        String result = null;
        try {
            result = FileUtils.uploadFile(file.getBytes(), uploadPath, webServerUrl, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        vo.setCode(EnumResCode.SUCCESSFUL.value());
        vo.setMessage("ok");
        vo.setData(result);
        return vo;
    }




        /**
         * 判断Excel的版本,获取Workbook
         *
         * @param in
         * @param file
         * @return
         * @throws IOException
         */
    public Workbook getWorkbok(InputStream in, File file) throws IOException {
        Workbook wb = null;
        if (file.getName().endsWith(EXCEL_XLS)) {  //Excel 2003
            wb = new HSSFWorkbook(in);
        } else if (file.getName().endsWith(EXCEL_XLSX)) {  // Excel 2007/2010
            wb = new XSSFWorkbook(in);
        }
        return wb;
    }

    /**
     * 判断文件是否是excel
     *
     * @throws Exception
     */
    public void checkExcelVaild(File file) throws Exception {
        if (!file.exists()) {
            throw new Exception("文件不存在");
        }
        if (!(file.isFile() && (file.getName().endsWith(EXCEL_XLS) || file.getName().endsWith(EXCEL_XLSX)))) {
            throw new Exception("文件不是Excel");
        }
    }


    private Object getValue(Cell cell) {
        Object obj = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                obj = String.valueOf(cell.getBooleanCellValue());
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case NUMERIC:
                obj = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    private List<KeUser> GenerateUser(File file, UserService userService){
        List<KeUser> keUserList = new ArrayList<>();
        try {
            // 同时支持Excel 2003、2007
            FileInputStream in = new FileInputStream(file); // 文件流
            checkExcelVaild(file);
            Workbook workbook = getWorkbok(in, file);
            //Workbook workbook = WorkbookFactory.create(is); // 这种方式 Excel2003/2007/2010都是可以处理的

            int sheetCount = workbook.getNumberOfSheets(); // Sheet的数量
            /**
             * 设置当前excel中sheet的下标：0开始
             */
//            Sheet sheet = workbook.getSheetAt(0);   // 遍历第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);   // 遍历第三个Sheet

            // 为跳过第一行目录设置count
            int count = 0;
            for (Row row : sheet) {
                KeUser keUser = new KeUser();
                try {
                    // 跳过第一行的目录
                    if (count < 1) {
                        count++;
                        continue;
                    }

                    //如果当前行没有数据，跳出循环
                    if (row.getCell(0).toString().equals("")) {
                        return null;
                    }

                    //获取总列数(空格的不计算)
                    int columnTotalNum = row.getPhysicalNumberOfCells();
                    //System.out.println("总列数：" + columnTotalNum);

                    //System.out.println("最大列数：" + row.getLastCellNum());

                    //for循环的，不扫描空格的列
//                    for (Cell cell : row) {
//                    	System.out.println(cell);
//                    }
                    int end = row.getLastCellNum();
                    for (int i = 0; i < end; i++) {
                        Cell cell = row.getCell(i);
                        if (cell == null) {
                            //System.out.print("null" + "\t");
                            continue;
                        }

                        Object obj = getValue(cell);
                        if (i == 0) {
                            keUser.setName(String.valueOf(obj));
                        } else if (i == 1) {
                            keUser.setPassword(String.valueOf(Float.valueOf(String.valueOf(obj)).intValue()));
                        } else if (i == 2) {
                            keUser.setSchoolId(Float.valueOf(String.valueOf(obj)).intValue());

                            KeSchool keSchool = userService.getSchoolById(Float.valueOf(String.valueOf(obj)).intValue());
                            keUser.setSchool(keSchool.getName());

                        } else if (i == 3) {
                            if (String.valueOf(obj).trim().equals("男")) {
                                keUser.setGender(EnumGender.MALE.value());
                            } else {
                                keUser.setGender(EnumGender.FEMALE.value());
                            }
                        } else if (i == 4) {
                            keUser.setEmail(String.valueOf(obj));
                        } else if (i == 5) {
                            keUser.setMajor(String.valueOf(obj));
                        } else if (i == 6) {
                            if (String.valueOf(obj).trim().equals("学生")) {
                                keUser.setType(1);
                            } else {
                                keUser.setType(2);
                            }
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                keUser.setCredit(0);
                keUser.setSigninNum(0);
                keUser.setMessage(0);
                keUserList.add(keUser);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keUserList;
    }


}
