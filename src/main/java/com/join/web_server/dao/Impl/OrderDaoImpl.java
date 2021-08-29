package com.join.web_server.dao.Impl;

import com.join.web_server.dao.OrderDao;
import com.join.web_server.entity.Cart;
import com.join.web_server.entity.Drink;
import com.join.web_server.entity.Order;
import com.join.web_server.entity.OrderItem;
import com.join.web_server.sql.Sql;
import com.join.web_server.util.DruidUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    @Override
    public int addOrder(Order order) throws Exception {

        int count = DruidUtil.executeDML(Sql.addOrder,order.getOrder_uuid(),order.getUser().getUsername(),order.getState(),order.getCreateTime());
        System.out.println("order"+count);
        return count;
    }
//    public static String addOrder = "insert into order(order_uuid,username,state,createTime)" +
//            "values(?,?,?,?);";
//
//    public static String addOrderItem = "insert into order_item(order_uuid,pro_id,quantity,totalPrice)" +
//            "values(?,?,?,?);";
    @Override
    public int  addOrderItem(OrderItem item) throws Exception {
        int count = DruidUtil.executeDML(Sql.addOrderItem,item.getOrder_uuid(),item.getDrink().getId(), item.getQuantity(),item.getTotalPrice());
        System.out.println("oderitem"+count);
        return count;
    }

    @Override
    public Cart sOrderCart(int id) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.sOrderCart);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        Cart cart = new Cart();
        while(rs.next()) {
            cart.setId(rs.getInt("id"));
            cart.setUsername(rs.getString("username"));
            cart.setQuantity(rs.getInt("quantity"));
            cart.setPro_id(rs.getInt("pro_id"));
            Drink drink = new Drink();
            drink.setId(rs.getInt("pro_id"));
            drink.setName(rs.getString("name"));
            drink.setDescription(rs.getString("description"));
            drink.setType(rs.getString("type"));
            drink.setPrice(rs.getInt("price"));
            drink.setInventory(rs.getInt("inventory"));
            drink.setPicture(rs.getString("picture"));
            drink.setSellerName(rs.getString("sellerName"));
            cart.setDrink(drink);
            cart.setTotalPrice(cart.getTotalPrice());
        }
        return cart;
    }

    @Override
    public int updateSend(int state,Timestamp sendTime,String order_uuid) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.updateSend);
        ps.setInt(1,state);
        ps.setTimestamp(2,sendTime);
        ps.setString(3,order_uuid);
        int count = ps.executeUpdate();
        return count;
    }

    @Override
    public int updateArrive(int state,Timestamp arriveTime,String order_uuid) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.updateArrive);
        ps.setInt(1,state);
        ps.setTimestamp(2,arriveTime);
        ps.setString(3,order_uuid);
        int count = ps.executeUpdate();
        return count;
    }

    @Override
    public List<OrderItem> showOrder(int state,String username) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.showOrder);
        return getOrderItems(state, username, ps);
    }

    @Override
    public List<OrderItem> showOrderS(int state, String username) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.showOrderS);
        return getOrderItems(state, username, ps);
    }

    private List<OrderItem> getOrderItems(int state, String username, PreparedStatement ps) throws SQLException {
        ps.setInt(1,state);
        ps.setString(2,username);
        ResultSet rs = ps.executeQuery();
        List<OrderItem> itemList = new ArrayList<>();
        while(rs.next()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder_uuid(rs.getString("order_uuid"));
            orderItem.setUsername(rs.getString("username"));
            orderItem.setQuantity(rs.getInt("quantity"));
            orderItem.setCreateTime(rs.getTimestamp("createTime"));
            Drink drink = new Drink();
            drink.setName(rs.getString("name"));
            drink.setPrice(rs.getInt("price"));
            drink.setSellerName(rs.getString("sellerName"));
            orderItem.setDrink(drink);
            orderItem.setTotalPrice(rs.getInt("totalPrice"));
            itemList.add(orderItem);
        }
        return itemList;
    }
}
