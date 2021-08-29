package com.join.web_server.dao.Impl;

import com.join.web_server.dao.DrinkDao;
import com.join.web_server.entity.Drink;
import com.join.web_server.sql.Sql;
import com.join.web_server.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DrinkDaoImpl implements DrinkDao {

    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Drink> drinkList = null;

    @Override
    public List<Drink> showAll() {
        Connection conn = DruidUtil.getConnection();
        try {
            ps = conn.prepareStatement(Sql.selectAll);
            getDrink();
        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            DruidUtil.release(conn,ps,rs);
        }
        return drinkList;
    }

    @Override
    public List<Drink> showType(String type) {
        Connection conn = DruidUtil.getConnection();
        try {
            ps = conn.prepareStatement(Sql.selectByType);
            ps.setString(1, type);
            System.out.println("q"+type);
            getDrink();
        } catch (Exception e) {
            e.getStackTrace();
        }finally {
            DruidUtil.release(conn,ps,rs);
        }
        return drinkList;
    }

    @Override
    public List<Drink> showSingle(int id) {
        Connection conn = DruidUtil.getConnection();
        try {
            ps = conn.prepareStatement(Sql.selectSingle);
            ps.setInt(1,id);
            System.out.println(id);
            getDrink();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            DruidUtil.release(conn,ps,rs);
        }
        return drinkList;
    }

    @Override
    public List<Drink> showShop(String sellerName) {
        Connection conn = DruidUtil.getConnection();
        try {
            ps = conn.prepareStatement(Sql.selectShop);
            ps.setString(1,sellerName);
            System.out.println(sellerName);
            getDrink();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            DruidUtil.release(conn,ps,rs);
        }
        return drinkList;
    }

    private void getDrink() throws SQLException {
        drinkList = new ArrayList<>();
//        System.out.println(drinkList.size());
        rs = ps.executeQuery();
        while (rs.next()) {
            Drink drink = new Drink();
            drink.setId(rs.getInt("id"));
            drink.setName(rs.getString("name"));
            drink.setDescription(rs.getString("description"));
            drink.setType(rs.getString("type"));
            drink.setPrice(rs.getInt("price"));
            drink.setPicture(rs.getString("picture"));
            drink.setInventory(rs.getInt("inventory"));
            drink.setSellerName(rs.getString("sellerName"));
            System.out.println(drink.getName());
            drinkList.add(drink);
        }
    }

    public int add(Drink drink) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        ps = conn.prepareStatement(Sql.insert);
        ps.setString(1, drink.getName());
        ps.setString(2, drink.getDescription());
        ps.setString(3,drink.getType());
        ps.setInt(4, drink.getPrice());
        ps.setInt(5, drink.getInventory());
        ps.setString(6, drink.getPicture());
        ps.setString(7,drink.getSellerName());
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn, ps);
        return count;
    }

    @Override
    public int deletePro(int id,String sellerName) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        ps = conn.prepareStatement(Sql.deletePro);
        ps.setInt(1,id);
        ps.setString(2,sellerName);
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn, ps);
        return count;
    }

    @Override
    public int updateInventory(int inventory,int id, String sellerName) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        ps = conn.prepareStatement(Sql.updateInventory);
        ps.setInt(1,inventory);
        ps.setString(2,sellerName);
        ps.setInt(3,id);
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn, ps);
        return count;
    }
    public int updatePrice(int price,int id, String sellerName) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        ps = conn.prepareStatement(Sql.updatePrice);
        ps.setInt(1,price);
        ps.setString(2,sellerName);
        ps.setInt(3,id);
        int count = ps.executeUpdate();
        System.out.println(count);
        DruidUtil.release(conn, ps);
        return count;
    }

}
