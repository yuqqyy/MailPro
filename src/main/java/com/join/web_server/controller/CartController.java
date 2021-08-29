package com.join.web_server.controller;

import com.google.gson.Gson;
import com.join.web_server.entity.Cart;
import com.join.web_server.service.CartService;
import com.join.web_server.service.Impl.CartServiceImpl;
import com.join.web_server.util.JSONUtil;
import net.sf.json.JSONArray;


import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartController extends HttpServlet {
    CartService cartService = new CartServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        switch (uri){
            case "/cart/add":
                try {
                    addCart(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/cart/deletePro":
                try {
                    deleteCart(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/cart/showCart":
                try {
                    showCart(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }
    //添加商品到购物车
    private void addCart(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        //使用Gson将它序列化
        Gson gson = new Gson();
        Cart cart = gson.fromJson(jsonString, Cart.class);
        String username = (String) req.getSession(false).getAttribute("username");
        cart.setUsername(username);
        int flag = cartService.addCart(cart);
        Map map = new HashMap();
        if (flag > 0) {
            map.put("code", 1);
            map.put("msg", "加入成功");
            map.put("cart", cart);
        } else if(flag == -1){
            //返回值构建
            map.put("code", 2);
            map.put("msg", "商品库存不够");
        }else{
            map.put("code", 3);
            map.put("msg", "加入失败");
        }
        //将map放在gson中
        jsonString = gson.toJson(map);
        //最后输出
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
    //从购物车中删去
    private void deleteCart(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        //使用Gson将它序列化
        Gson gson = new Gson();
        Cart cart = gson.fromJson(jsonString, Cart.class);
        int flag = cartService.deleteCart(cart);
        Map map = new HashMap();
        if (flag != 0) {
            map.put("code", 1);
            map.put("msg","删除成功");
        } else {
            map.put("code", 2);
            map.put("msg", "删除失败");
        }
        jsonString = gson.toJson(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.close();
    }

    //购物车展示
    private void showCart(HttpServletRequest req,HttpServletResponse resp) throws SQLException, IOException {

        HttpSession session = req.getSession();
        System.out.println("sessionI"+session.getId());
        String username = (String) session.getAttribute("username");
        System.out.println("username"+username);

        List<Cart> list = cartService.showCart(username);
        Map map = new HashMap();
        System.out.println("listSize"+list.size());
        for(int i = 0; i<list.size(); i++){
            System.out.println("list"+i+list.get(i));
        }
        if (list.size() != 0) {
            map.put("code", 1);
            map.put("msg", "successful");
            map.put("list",list);
        } else {
            map.put("code", 2);
            map.put("msg", "购物车空的");
        }
//        JSONArray jsonString = JSONArray.fromObject(map);
//        System.out.println(jsonString.toString());

        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        String  jsonString = gson.toJson(map);
        out.println(jsonString);
//        out.flush();//{}问题
        out.close();
    }

}
