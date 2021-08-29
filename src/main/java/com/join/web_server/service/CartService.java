package com.join.web_server.service;

import com.join.web_server.entity.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartService {
     int addCart(Cart cart) throws SQLException;
     int deleteCart(Cart cart) throws SQLException;
     List<Cart> showCart(String username) throws SQLException;

}
