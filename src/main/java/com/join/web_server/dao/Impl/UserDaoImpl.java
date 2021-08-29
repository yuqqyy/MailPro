package com.join.web_server.dao.Impl;

import com.join.web_server.dao.UserDao;
import com.join.web_server.entity.Drink;
import com.join.web_server.entity.Page;
import com.join.web_server.entity.User;
import com.join.web_server.sql.Sql;
import com.join.web_server.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    //用户注册
    public int sign(User user) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;
        //判断用户名唯一
        ps = conn.prepareStatement(Sql.selectByName);
        ps.setString(1,user.getUsername());
        ps.setString(2,user.getRole());
        ResultSet rs = ps.executeQuery();
        List<User> list=new ArrayList<User>();
        while(rs.next()){
            user = new User();
            list.add(user);
        }
        if(list.size()>0){//用户名已被使用
            return 2;
        }else {
            ps = conn.prepareStatement(Sql.sign);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUserPwd());
            ps.setString(3, user.getAds());
            ps.setString(4, user.getPhone());
            ps.setString(5,user.getRole());
            int count = ps.executeUpdate();
            System.out.println(count);
            DruidUtil.release(conn,ps);
            return count;
        }
    }

    @Override//用户登录
    public User login(String username,String role) throws SQLException {
        User user = getUser(username, role);
        return user;
    }

    private User getUser(String username, String role) throws SQLException {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = conn.prepareStatement(Sql.selectByName);
        ps.setString(1,username);
        ps.setString(2,role);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        while(rs.next()){
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setUserPwd(rs.getString("userPwd"));
            user.setAds(rs.getString("ads"));
            user.setPhone(rs.getString("phone"));
            user.setRole(rs.getString("role"));
        }
        DruidUtil.release(conn,ps);
        return user;
    }

    @Override
    public User showUser(String username, String role) throws SQLException {
        User user = getUser(username, role);
        return user;
    }


//    //分页
//    public List<Drink> findPage(Page page) throws SQLException {
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement ps = conn.prepareStatement(Sql.select_Page);
//        ps.setInt(1,page.getStartIndex());
//        ps.setInt(2,page.getPageSize());
//        ResultSet rs = ps.executeQuery();
//        List<Drink> drinks = new ArrayList<Drink>();
//        while(rs.next()){
//            Drink drink = new Drink();
//            drink.setName(rs.getString("name"));
//            drink.setDescription(rs.getString("description"));
//            drink.setPrice(rs.getDouble("price"));
//            drink.setInventory(rs.getInt("inventory"));
//            drinks.add(drink);
//        }
//        DruidUtil.release(conn,ps);
//        return drinks;
//
//    }
//
//    //获得商品总数
//    public int getCount() throws SQLException {
//        Connection conn = DruidUtil.getConnection();
//        PreparedStatement ps = conn.prepareStatement(Sql.countAll);
//        ResultSet resultSet = ps.executeQuery();
//        int totalRecord = 0;
//        while(resultSet.next()){
//            totalRecord = resultSet.getInt(1);
//        }
//        return totalRecord;
//    }

}
