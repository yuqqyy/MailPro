package com.join.web_server.controller;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@MultipartConfig
public class FileController extends HttpServlet{

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        switch (uri) {
            case "/file/pic":
                picsFile(req, resp);
                break;
        }
    }
    //图片上传
    private void picsFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("picture");
        //获取上传的文件名扩展名
        String fileName = part.getSubmittedFileName();
        Gson gson = new Gson();
        Map map = new HashMap();
        //判断该文件是不是图片格式
        String fileType = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!("jpg".equalsIgnoreCase(fileType) || "jpeg".equalsIgnoreCase(fileType) || "png".equalsIgnoreCase(fileType))) {
            //不是图片格式，返回空字串
            map.put("code",2);
            map.put("msg","图片格式不正确");
        }else {
            String suffix = fileName.substring(fileName.lastIndexOf("."));
            //随机的生成uuid
            String realFilename = UUID.randomUUID() + suffix;
            //存储图片的绝对路径
            String serverPath = req.getServletContext().getRealPath("pictures");
            System.out.println("serverPath:---"+serverPath);
            //不存在文件夹则新建一个
            File fileDisk = new File(serverPath);
            if (!fileDisk.exists()) {
                fileDisk.mkdir();
            }
            String filePath = serverPath + "/" + realFilename;
            System.out.println("filePath:--" + filePath);
            part.write(filePath);
            System.out.println("getContext:"+req.getContextPath());
            String projectServerPath = req.getScheme() + "://yyqyy.natapp1.cc" + req.getContextPath() + "/pictures/" + realFilename;
            System.out.println("projectSeverPath" + projectServerPath);
            map.put("code", 1);
            //将图片路径传给前端
            map.put("picture", projectServerPath);
        }
        PrintWriter out = resp.getWriter();
        String jsonString = gson.toJson(map);
        out.print(jsonString);
        out.flush();
    }
}
