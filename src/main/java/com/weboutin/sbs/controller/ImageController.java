package com.weboutin.sbs.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import com.weboutin.sbs.utils.Utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ImageController {

    @GetMapping("/v1/images/{imageName}")
    public void get(@PathVariable("imageName") String imageName,HttpServletResponse response) throws Exception {
        response.setContentType("image/png");
        String basePath = new ApplicationHome(this.getClass()).getSource().getPath() + "/";
        InputStream fielStream = new FileInputStream(new File(basePath + "uploads/" + imageName));
        int size = fielStream.available();
        byte data[] = new byte[size];
        fielStream.read(data);
        fielStream.close();
        OutputStream os = response.getOutputStream();
        os.write(data);
        os.flush();
        os.close();
    }

    @PostMapping("/v1/images")
    public Map upload(@RequestParam("images") MultipartFile file) throws Exception {
        byte[] bytes = file.getBytes();
        String basePath = new ApplicationHome(this.getClass()).getSource().getPath() + "/";
        Path path = Paths.get(basePath + "uploads/" + file.getOriginalFilename());
        Files.write(path, bytes);
        Map result = new HashMap();
        result.put("imageurl", "/v1/images/" + file.getOriginalFilename());
        return Utils.buildResponse(0, "上传成功", result);
    }




}
