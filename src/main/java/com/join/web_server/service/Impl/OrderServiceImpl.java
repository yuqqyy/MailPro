package com.join.web_server.service.Impl;

import com.join.web_server.dao.Impl.OrderDaoImpl;
import com.join.web_server.dao.OrderDao;
import com.join.web_server.entity.Cart;
import com.join.web_server.entity.Order;
import com.join.web_server.entity.OrderItem;
import com.join.web_server.service.OrderService;
import com.join.web_server.util.DruidUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    @Override
    public int addOrder(Order order) throws SQLException {

        Connection conn=null;
        int count = 0;
        try {
            //获取连接
            conn= DruidUtil.getConnection();
            conn.setAutoCommit(false);
            //保存订单
            count = orderDao.addOrder(order);
            //保存订单项
//            for (OrderItem item : order.getList()) {
                orderDao.addOrderItem(order.getOrderItem());
//            }
            conn.commit();
        } catch (Exception e) {
            //回滚
            conn.rollback();
        }
        return count;
    }

    @Override
    public Cart sOrderCart(int id) throws SQLException {
        return orderDao.sOrderCart(id);
    }

    @Override
    public int updateSend(int state, Timestamp sendTime, String order_uuid) throws SQLException {
        return orderDao.updateSend(state,sendTime,order_uuid);
    }

    @Override
    public int updateArrive(int state,Timestamp arriveTime,String order_uuid) throws SQLException {
        return orderDao.updateArrive(state,arriveTime,order_uuid);
    }

    @Override
    public List<OrderItem> showOrder(int state,String username) throws SQLException {
        return orderDao.showOrder(state,username);
    }

    @Override
    public List<OrderItem> showOrderS(int state, String username) throws SQLException {
        return orderDao.showOrderS(state,username);
    }
}
