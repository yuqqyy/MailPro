package com.join.web_server.service;

import com.join.web_server.entity.Drink;

import java.sql.SQLException;
import java.util.List;

public interface DrinkService {
    int add(Drink drink) throws SQLException;
    int deletePro(int id, String sellerName) throws SQLException;
    int updateInventory(int inventory,int id,String sellerName) throws SQLException;
    int updatePrice(int price,int id,String sellerName) throws SQLException;
    List<Drink> showAll() throws SQLException;
    List<Drink> showType(String type) throws SQLException;
    List<Drink> showSingle(int id) throws SQLException;
    List<Drink> showShop(String sellerName);
//    int count() throws SQLException;
}
