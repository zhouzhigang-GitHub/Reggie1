package com.zhou.controller;

import com.zhou.common.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author 周志刚
 * @Date 2022/8/14 17:17
 * @PackageName: com.zhou.controller
 * @ClassName: FileController
 * @Description: TODO
 */
@RestController
@RequestMapping("/common")
public class FileController {

    @Value("${zhou.path}")
    private String filePath;

    @PostMapping("/upload")
    public R<String> upload(@RequestParam(value = "file") MultipartFile file){
        //获取文件原始名字
        String originalFilename = file.getOriginalFilename();
        //截取后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID() + suffix;
        //判断文件是否创建
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdirs();
        }
        //文件另存为
        try {
            file.transferTo(new File(filePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(@RequestParam(value = "name") String name , HttpServletResponse response){
        try {
            FileInputStream inputStream = new FileInputStream(new File(filePath + name));
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
