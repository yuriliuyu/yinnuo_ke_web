package com.yuri.ynweb_kj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    public static String uploadFile(byte[] file, String filePath, String urlPath, String fileName) throws Exception {
        Date date = new Date();
        String dataForm = new SimpleDateFormat("yyyyMMdd").format(date);
        filePath = filePath + "/" + dataForm + "/";
        urlPath = urlPath + "/" + dataForm + "/";
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
        return urlPath + fileName;
    }
}
