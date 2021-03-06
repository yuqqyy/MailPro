package com.join.web_server.controller;


import com.google.gson.Gson;
import com.join.web_server.entity.Drink;
import com.join.web_server.service.DrinkService;
import com.join.web_server.service.Impl.DrinkServiceImpl;
import com.join.web_server.util.JSONUtil;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig
public class DrinkController extends HttpServlet {

    DrinkService drinkService = new DrinkServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String uri = req.getRequestURI();
        switch (uri){
            case "/drink/showSingle" :
                try {
                    showSingle(req,resp);
                } catch (SQLException|IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/drink/showAll" :
                try {
                    showAll(req,resp);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/drink/showType":
                try {
                    showType(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "/seller/add" :
                try {
                    add(req,resp);
                } catch (SQLException | ServletException e) {
                    e.printStackTrace();
                }
                break;
            case "/seller/deletePro" :
                try {
                    deletePro(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/seller/showShop":
                try {
                    showShop(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/seller/updateInventory":
                try {
                    updateInventory(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
            case "/seller/updatePrice":
                try {
                    updatePrice(req,resp);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    //????????????????????????
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ServletException {
        //????????????????????????????????????
        String jsonString = JSONUtil.getJson(req);
        //??????Gson???????????????
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        //session????????????username
        String sellerName = (String) req.getSession(false).getAttribute("username");
        drink.setSellerName(sellerName);

        int flag = drinkService.add(drink);
        gson = new Gson();
        Map map = new HashMap();
        if (flag != 0) {
            map.put("code", 1);
            map.put("msg", "????????????");
            map.put("drink", drink);
        } else {
            //???????????????
            map.put("code", 0);
            map.put("msg", "????????????");
        }
        //???map??????gson???
        jsonString = gson.toJson(map);
        //????????????
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }

    //????????????
    private  void deletePro(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        //??????Gson???????????????
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        int id = drink.getId();
        //session????????????id
        String  sellerName = (String) req.getSession(false).getAttribute("username");
        int flag = drinkService.deletePro(id,sellerName);
        Map map = new HashMap();
        if (flag != 0) {
            map.put("code", 1);
            map.put("msg", "????????????");
        } else {
            //???????????????
            map.put("code", 0);
            map.put("msg", "????????????");
        }
        jsonString = gson.toJson(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.close();
    }
    private  void updateInventory(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        int inventory = drink.getInventory();
        int id = drink.getId();
        String sellerName = (String) req.getSession(false).getAttribute("username");
        int flag = drinkService.updateInventory(inventory,id,sellerName);
        Map map = new HashMap();
        if (flag != 0) {
            map.put("code", 1);
            map.put("msg", "??????????????????");
        } else {
            //???????????????
            map.put("code", 0);
            map.put("msg", "??????????????????");
        }
        jsonString = gson.toJson(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
    //??????????????????
    private  void updatePrice(HttpServletRequest req,HttpServletResponse resp) throws IOException, SQLException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        int price = drink.getPrice();
        System.out.println(price+"updatePrice");
        int id = drink.getId();
        String sellerName = (String) req.getSession(false).getAttribute("username");
        int flag = drinkService.updatePrice(price,id,sellerName);
        Map map = new HashMap();
        if (flag != 0) {
            map.put("code", 1);
            map.put("msg", "??????????????????");
        } else {
            //???????????????
            map.put("code", 0);
            map.put("msg", "??????????????????");
        }
        jsonString = gson.toJson(map);
        PrintWriter out = resp.getWriter();
        out.print(jsonString);
        out.flush();
    }
    //???????????????????????????
    private void showAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {
        List<Drink> list = drinkService.showAll();
        show(resp, list);
    }
    //???????????????????????????
    private void showType(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String jsonString = JSONUtil.getJson(req);
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        String type = drink.getType();
        System.out.println(type);
        List<Drink> list = drinkService.showType(type);
        show(resp, list);
    }
    //??????????????????
    private void showSingle(HttpServletRequest req,HttpServletResponse resp) throws SQLException, IOException {
        String jsonString = JSONUtil.getJson(req);
        //??????Gson???????????????
        Gson gson = new Gson();
        Drink drink = gson.fromJson(jsonString, Drink.class);
        int id = drink.getId();
        List<Drink> list = drinkService.showSingle(id);
        show(resp,list);
    }

    private void showShop(HttpServletRequest req,HttpServletResponse resp) throws SQLException, IOException{
        String sellerName = (String) req.getSession().getAttribute("username");
        System.out.println("seller"+sellerName);
        List<Drink> list = drinkService.showShop(sellerName);
        show(resp,list);
        System.out.println("listsize--"+list.size());
    }

    private void show(HttpServletResponse resp, List<Drink> list) throws IOException {
        Map map = new HashMap();
        if (list.size() != 0) {
            map.put("code", 1);
            map.put("msg", "successful");
            map.put("list",list);
        } else {
            map.put("code", 0);
            map.put("msg", "fail");
        }
        Gson gson = new Gson();
        PrintWriter out = resp.getWriter();
        String jsonString = gson.toJson(map);
        out.print(jsonString);
        out.close();
    }

    //????????????
//    private void showA(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
//        int pageNum = Integer.decode(req.getParameter("pageNum"));
//        int pageSize = Integer.decode(req.getParameter("pageSize"));
//        int totalRecord = Integer.decode(req.getParameter("totalRecord"));
//        Page page = new Page(pageNum,pageSize,totalRecord);
//        List<Drink> list = userService.show(page);
//        Map map = new HashMap();
//        if (list.size() != 0) {
//            map.put("code", 1);
//            map.put("msg", "successful");
//            map.put("page", page);
//        } else {
//            map.put("code", 0);
//            map.put("msg", "fail");
//        }
//        Gson gson = new Gson();
//        PrintWriter out = resp.getWriter();
//        String jsonString = gson.toJson(map);
//        out.print(jsonString);
//        out.flush();
//    }

}

