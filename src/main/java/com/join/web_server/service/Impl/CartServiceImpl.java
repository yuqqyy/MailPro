package com.join.web_server.service.Impl;

import com.join.web_server.dao.CartDao;
import com.join.web_server.dao.Impl.CartDaoImpl;
import com.join.web_server.entity.Cart;
import com.join.web_server.service.CartService;

import java.sql.SQLException;
import java.util.List;

public class CartServiceImpl implements CartService {

    CartDao cartDao = new CartDaoImpl();
    @Override
    public int addCart(Cart cart) throws SQLException {
        return cartDao.addCart(cart);
    }

    @Override
    public int deleteCart(Cart cart) throws SQLException {
        return cartDao.deleteDrink(cart);
    }

    @Override
    public List<Cart> showCart(String username) throws SQLException {
        return cartDao.showCart(username);
    }

}
