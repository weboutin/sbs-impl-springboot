package com.weboutin.sbs.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class ImageController {

    @GetMapping("/v1/images/{imageName}")
    public void get(@PathVariable("imageName") String imageName, HttpServletRequest request ,HttpServletResponse response) throws Exception {
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

}
