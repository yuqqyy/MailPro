package com.join.web_server.service;

import com.join.web_server.entity.Drink;
import com.join.web_server.entity.Page;
import com.join.web_server.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
//    int add(Drink drink) throws SQLException;
//    int count() throws SQLException;
//    List<Drink> show(Page page) throws SQLException;
    int sign(User user) throws SQLException;
    User login(User user) throws SQLException;
    User showUser(String username,String role) throws SQLException;
}
