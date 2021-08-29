package com.join.web_server.service.Impl;

import com.join.web_server.dao.DrinkDao;
import com.join.web_server.dao.Impl.DrinkDaoImpl;
import com.join.web_server.entity.Drink;
import com.join.web_server.service.DrinkService;

import java.sql.SQLException;
import java.util.List;

public class DrinkServiceImpl implements DrinkService {
    DrinkDao drinkDao = new DrinkDaoImpl();

    @Override
    public int add(Drink drink) throws SQLException {
        return drinkDao.add(drink);
    }

    @Override
    public int deletePro(int id, String sellerName) throws SQLException {
        return drinkDao.deletePro(id,sellerName);
    }

    @Override
    public int updateInventory(int inventory, int id, String sellerName) throws SQLException {
        return drinkDao.updateInventory(inventory,id, sellerName);
    }

    @Override
    public int updatePrice(int price, int id, String sellerName) throws SQLException {
        return drinkDao.updatePrice(price,id,sellerName);
    }

    @Override
    public List<Drink> showAll() throws SQLException {
        return drinkDao.showAll();
    }

    @Override
    public List<Drink> showType(String type) throws SQLException {
        return drinkDao.showType(type);
    }

    @Override
    public List<Drink> showSingle(int id) throws SQLException {
        return drinkDao.showSingle(id);
    }

    @Override
    public List<Drink> showShop(String sellerName) {
        return drinkDao.showShop(sellerName);
    }



//    public int count() throws SQLException {
//        return userDao.getCount();
//    }
//    public List<Drink> show(Page page) throws SQLException {
//        return userDao.findPage(page);
//
//    }
}
