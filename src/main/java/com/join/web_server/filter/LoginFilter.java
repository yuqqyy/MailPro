package com.join.web_server.filter;

import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession httpSession = request.getSession();
        Map map = new HashMap();
        if(httpSession != null){
            Object username = httpSession.getAttribute("username");
            if(username != null){
                System.out.println("filter"+httpSession.getId());
                filterChain.doFilter(request,response);
            }else {
                map.put("code", -1);
                map.put("msg", "用户未登录！");
            }
        }else {
            map.put("code", -1);
            map.put("msg", "用户未登录！");
        }
        Gson gson = new Gson();
        PrintWriter out = servletResponse.getWriter();
        String jsonString = gson.toJson(map);
        out.println(jsonString);
        out.flush();
    }

    @Override
    public void destroy() {

    }
}
