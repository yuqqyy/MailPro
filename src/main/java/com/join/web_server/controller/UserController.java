package com.join.web_server.controller;

import com.google.gson.Gson;
import com.join.web_server.entity.User;
import com.join.web_server.filter.LoginFilter;
import com.join.web_server.service.UserService;
import com.join.web_server.service.Impl.UserServiceImpl;
import com.join.web_server.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;

public class UserController extends HttpServlet {
    UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        switch (uri){
            case "/user/sign" :
                try {
                    sign(req,resp);
                } catch (SQLException |IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/user/login" :
                try {
                    login(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/user/showUser":
                try {
                    showUser(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    //用户注册
    private void sign(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        //将传过来的数据形成字符串
        String jsonString = JSONUtil.getJson(req);
        //使用Gson将它序列化
        Gson gson = new Gson();
        User user = gson.fromJson(jsonString, User.class);
        int flag = userService.sign(user);
        Map map = new HashMap();
        if (flag == 1) {
            map.put("code", 1);
            map.put("msg", "successful");
            map.put("user", user);
        } else {
            //返回值构建
            map.put("code", 2);
            map.put("msg", "用户名已被使用");
        }
        //将map放在gson中
        jsonString = gson.toJson(map);
        //最后输出
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }

    //用户登录
    private void login(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String jsonString = JSONUtil.getJson(req);
        //使用Gson将它序列化
        Gson gson = new Gson();
        User user1 = gson.fromJson(jsonString, User.class);
        User user = userService.login(user1);
        Map map = new HashMap();
        if(!Objects.isNull(user)) {
            HttpSession session = req.getSession();
            //设置session
            session.setAttribute("user", user);
            session.setAttribute("id",user.getId());
            session.setAttribute("username",user.getUsername());
            session.setMaxInactiveInterval(30*60);
            String sessionID = session.getId();
            System.out.println(sessionID);

            map.put("code", 1);
            map.put("msg", "登录成功");
            map.put("user", user);

        }else{
            map.put("code", 2);
            map.put("msg", "用户名或密码错误");
        }
        gson = new Gson();
        PrintWriter out = resp.getWriter();
        jsonString = gson.toJson(map);
        out.println(jsonString);
        out.flush();
    }

    //展示用户个人信息
    private void showUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{

        User user1 = (User) req.getSession().getAttribute("user");
        String username = user1.getUsername();
        System.out.println("username:--"+username);
        String role = user1.getRole();
        User user = userService.showUser(username,role);
        Map map = new HashMap();
        if(!Objects.isNull(user)) {
            map.put("code", 1);
            map.put("msg", "个人界面展示成功");
            map.put("user", user);
        }else{
            map.put("code", 2);
            map.put("msg", "用户未登录");
        }
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        String jsonString = gson.toJson(map);
        out.println(jsonString);
        out.flush();
    }

}
