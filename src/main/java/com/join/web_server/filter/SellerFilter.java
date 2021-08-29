package com.join.web_server.filter;

import com.google.gson.Gson;
import com.join.web_server.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class SellerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        Map map = new HashMap();
        if(role.equals("buyer")){
            map.put("code",-1);
            map.put("msg","您不是卖家");
            Gson gson = new Gson();
            PrintWriter out = servletResponse.getWriter();
            String jsonString = gson.toJson(map);
            out.println(jsonString);
            out.close();
        }
        else {
            filterChain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
