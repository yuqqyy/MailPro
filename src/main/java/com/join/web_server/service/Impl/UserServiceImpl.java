package com.join.web_server.service.Impl;

import com.join.web_server.dao.UserDao;
import com.join.web_server.dao.Impl.UserDaoImpl;
import com.join.web_server.entity.User;
import com.join.web_server.service.UserService;

import java.sql.SQLException;
import java.util.Objects;

public class UserServiceImpl implements UserService {
    UserDao userDao =new UserDaoImpl();


//    public int count() throws SQLException {
//        return userDao.getCount();
//    }
//    public List<Drink> show(Page page) throws SQLException {
//        return userDao.findPage(page);
//
//    }

    @Override
    public int sign(User user) throws SQLException {
        return userDao.sign(user);
    }

    @Override
    public User login(User user) throws SQLException {
        User user1 = userDao.login(user.getUsername(),user.getRole());
        if(Objects.isNull(user)) return null;
        if(user.getUserPwd().equals(user1.getUserPwd())){
            System.out.println("登录success");
            return user1;
        }
        return null;
    }

    @Override
    public User showUser(String username, String role) throws SQLException {
        return userDao.showUser(username,role);
    }


}
