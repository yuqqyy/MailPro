package com.join.web_server.dao;

import com.join.web_server.entity.Drink;

import java.sql.SQLException;
import java.util.List;

public interface DrinkDao {
    List<Drink> showAll();
    List<Drink> showType(String type);
    List<Drink> showSingle(int id);
    List<Drink> showShop(String sellerName);
    //上新
    int add(Drink drink) throws SQLException;
    //下架
    int deletePro(int id, String sellerID) throws SQLException;
    int updateInventory(int inventory,int id,String sellerName) throws SQLException;
    int updatePrice(int price,int id, String sellerName) throws SQLException;

}
