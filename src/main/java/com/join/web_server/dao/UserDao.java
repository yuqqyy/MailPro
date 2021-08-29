package com.join.web_server.dao;

import com.join.web_server.entity.Drink;
import com.join.web_server.entity.Page;
import com.join.web_server.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    int sign(User user) throws SQLException;
    User login(String username,String role) throws SQLException;
    User showUser(String username,String role) throws SQLException;
}
