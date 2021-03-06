package com.join.web_server.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.join.web_server.entity.Cart;
import com.join.web_server.entity.Order;
import com.join.web_server.entity.OrderItem;
import com.join.web_server.entity.User;
import com.join.web_server.service.CartService;
import com.join.web_server.service.Impl.CartServiceImpl;
import com.join.web_server.service.Impl.OrderServiceImpl;
import com.join.web_server.service.OrderService;
import com.join.web_server.util.JSONUtil;
import net.sf.json.JSONArray;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderController extends HttpServlet {

    OrderService orderService = new OrderServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        switch (uri){
            case "/order/addOrder":
                try {
                    addOrder(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/order/seller/send":
                try {
                    updateSend(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/order/arrive":
                try {
                    updateArrive(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/order/showOrder":
                try {
                    showOrder(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/order/seller/showOrderS":
                try {
                    showOrderS(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

        //????????????
        private void addOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        String jsonString = JSONUtil.getJson(req);
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        for(int i=0;i<jsonArray.size();i++){
            Cart cart = (Cart) jsonArray.get(i);
        }

//        Gson gson = new Gson();
//        Cart cart1 = gson.fromJson(jsonString,Cart.class);
        int id = cart1.getId();
        Cart cart = orderService.sOrderCart(id);
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Order order = new Order();
        //????????????id??????uuid??????????????????
        String user_id = String.valueOf(user.getId());
        String order_uuid = UUID.randomUUID() + user_id;
        order.setOrder_uuid(order_uuid);

        order.setUser(user);
        //?????????????????????1???????????????
        order.setState(1);
        //????????????????????????
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp createTime = Timestamp.valueOf(nowTime);
        order.setCreateTime(createTime);

        //???????????????,??????????????????
        OrderItem orderItem=new OrderItem();
        orderItem.setOrder_uuid(order_uuid);
        orderItem.setQuantity(cart.getQuantity());
        orderItem.setTotalPrice(cart.getTotalPrice());
        order.setCreateTime(createTime);
        orderItem.setDrink(cart.getDrink());
        //??????????????????????????????????????????:???????????????????????????????????????????????????
        order.setOrderItem(orderItem);
//        order.getList().add(orderItem);

        //?????????????????????:????????????
        int count = orderService.addOrder(order);
        System.out.println("con--"+count);
        Map map = new HashMap();
        if(count != 0){
            //???????????????????????????
            CartService cartService = new CartServiceImpl();
            cartService.deleteCart(cart);
            map.put("code",1);
            map.put("msg","??????????????????");
            map.put("orderItem",orderItem);
        }else {
            map.put("code",2);
            map.put("msg","??????????????????");
        }

        gson = new Gson();
        PrintWriter out = resp.getWriter();
        jsonString = gson.toJson(map);
        out.write(jsonString);
        out.flush();
    }
    //??????????????????
    private void updateSend(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        OrderItem orderItem = gson.fromJson(jsonString,OrderItem.class);
        String order_uuid = orderItem.getOrder_uuid();
        //????????????
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp sendTime = Timestamp.valueOf(nowTime);
        //?????????????????????2???????????????????????????
        int state = 2;
        int flag = orderService.updateSend(state,sendTime,order_uuid);
        Map map = new HashMap();
        if(flag != 0){
            map.put("code",1);
            map.put("msg","??????????????????");
        }else {
            map.put("code",2);
            map.put("msg","??????????????????");
        }

        gson = new Gson();
        PrintWriter out = resp.getWriter();
        jsonString = gson.toJson(map);
        out.write(jsonString);
        out.flush();
    }
    //???????????????????????????
    private void updateArrive(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        OrderItem orderItem = gson.fromJson(jsonString,OrderItem.class);
        String order_uuid = orderItem.getOrder_uuid();
        //??????????????????
        Date date = new Date();
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        Timestamp arriveTime = Timestamp.valueOf(nowTime);
        //?????????????????????3???????????????
        int state = 3;
        int flag = orderService.updateArrive(state,arriveTime,order_uuid);
        Map map = new HashMap();
        if(flag != 0){
            map.put("code",1);
            map.put("msg","??????????????????");
        }else {
            map.put("code",2);
            map.put("msg","??????????????????");
        }
        gson = new Gson();
        PrintWriter out = resp.getWriter();
        jsonString = gson.toJson(map);
        out.write(jsonString);
        out.flush();
    }
    //??????????????????
    private void showOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        Order order = gson.fromJson(jsonString,Order.class);
        //??????????????????
        int state = order.getState();
        User user = (User) req.getSession().getAttribute("user");
        String username = user.getUsername();
        Map map = new HashMap();
        List<OrderItem> list = orderService.showOrder(state,username);
        showMap(resp, map, list);
    }
    //??????????????????
    private void showOrderS(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        Order order = gson.fromJson(jsonString,Order.class);
        //??????????????????
        int state = order.getState();
        User user = (User) req.getSession().getAttribute("user");
        String username = user.getUsername();
        Map map = new HashMap();
        List<OrderItem> list = orderService.showOrderS(state,username);
        showMap(resp, map, list);
    }
    //?????????????????????
    private void showMap(HttpServletResponse resp, Map map, List<OrderItem> list) throws IOException {
        Gson gson;
        String jsonString;
        if (list.size() != 0) {
            map.put("code", 1);
            map.put("msg", "??????????????????");
            map.put("list",list);
        } else {
            map.put("code", 0);
            map.put("msg", "??????????????????");
        }
        gson = new Gson();
        PrintWriter out = resp.getWriter();
        jsonString = gson.toJson(map);
        out.print(jsonString);
        out.close();
    }


}
