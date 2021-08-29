package com.join.web_server.service;

import com.join.web_server.entity.Cart;
import com.join.web_server.entity.Order;
import com.join.web_server.entity.OrderItem;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface OrderService {
    int addOrder(Order order) throws SQLException;
    Cart sOrderCart(int id) throws SQLException;
    int updateSend(int state,Timestamp sendTime,String order_uuid ) throws SQLException;
    int updateArrive(int state,Timestamp arriveTime,String order_uuid) throws SQLException;
    List<OrderItem> showOrder(int state,String username) throws SQLException;
    List<OrderItem> showOrderS(int state,String username) throws SQLException;
}
