package com.springboot.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUpLoad {

    public static String uploadFile(MultipartFile file, String path){
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//        suffix = suffix.toLowerCase();
        String fileName=file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if(!targetFile.getParentFile().exists()){//注意，判断父级路径是否存在
            targetFile.getParentFile().mkdirs();
        }
        try {
            file.transferTo(targetFile);
            path=targetFile.getPath();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;

    }
}
