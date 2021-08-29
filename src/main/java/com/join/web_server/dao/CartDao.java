package com.join.web_server.dao;

import com.join.web_server.entity.Cart;

import java.sql.SQLException;
import java.util.List;

public interface CartDao {
    int addCart(Cart cart) throws SQLException;
    int deleteDrink(Cart cart) throws SQLException;
    List<Cart> showCart(String username) throws SQLException;
}
