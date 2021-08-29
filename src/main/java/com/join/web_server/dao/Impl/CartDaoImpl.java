package com.join.web_server.dao.Impl;

import com.join.web_server.dao.CartDao;
import com.join.web_server.dao.DrinkDao;
import com.join.web_server.entity.Cart;
import com.join.web_server.entity.Drink;
import com.join.web_server.sql.Sql;
import com.join.web_server.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDao {

    @Override
    public int addCart(Cart cart) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;
        //从购物车中查询该商品
        ps = conn.prepareStatement(Sql.selectCart);
        ps.setString(1, cart.getUsername());
        ps.setInt(2,cart.getPro_id());
        System.out.println(cart.getUsername()+"xxx"+cart.getPro_id());
        int addQuantity = cart.getQuantity();
        int preQuantity = 0;
        int id = 0;
        ResultSet rs = ps.executeQuery();
        List<Cart> cartList = new ArrayList<>();
        while(rs.next()){
            Cart cart1 = new Cart();
            id = rs.getInt("id");//购物车中id
            cart1.setId(id);
            cart.setId(id);
            preQuantity = rs.getInt("quantity");//已在购物车中商品数量
            cart1.setQuantity(preQuantity);
            cartList.add(cart1);
        }
        DrinkDao drinkDao = new DrinkDaoImpl();
        List<Drink> list = drinkDao.showSingle(cart.getPro_id());
        Drink drink = list.get(0);
        //购物车中已存在该商品
        if(cartList.size() != 0){
            cart.setQuantity(addQuantity+preQuantity);
            //商品库存小于加入购物车商品数量
            if(drink.getInventory() < cart.getQuantity()){
                return -1;
            }else {//购物车中已存在该商品时，数量+1
                ps = conn.prepareStatement(Sql.updateCart);
                ps.setInt(1, cart.getQuantity());
                cart.setTotalPrice(cart.getQuantity()*drink.getPrice());
                ps.setInt(2,cart.getQuantity()*drink.getPrice() );
                ps.setInt(3, id);
            }
        }else{
            //商品库存小于加入购物车商品数量
            if(drink.getInventory() < cart.getQuantity()){
                return -1;
            }
            else {//购物车中还不存在该商品且数量不超过库存，加入购物车
                ps = conn.prepareStatement(Sql.addCart);
                ps.setString(1, cart.getUsername());
                ps.setInt(2, cart.getPro_id());
                ps.setInt(3, cart.getQuantity());
                cart.setTotalPrice(cart.getQuantity()*drink.getPrice());
                ps.setInt(4,cart.getQuantity()*drink.getPrice());
            }
        }
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn,ps);
        return count;
    }

    @Override
    public int deleteDrink(Cart cart) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.deleteCart);
        ps.setInt(1,cart.getId());
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn,ps);
        return count;
    }

    @Override
    public List<Cart> showCart(String username) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.showCart);
        ps.setString(1,username);
        ResultSet rs = ps.executeQuery();
        List<Cart> cartList = new ArrayList<>();
        while(rs.next()){
            Cart cart = new Cart();
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
            System.out.println(drink.getName());
            cartList.add(cart);
        }
        DruidUtil.release(conn,ps);
        return cartList;
    }

}
