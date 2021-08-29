package com.join.web_server.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DruidUtil {
    public static DruidDataSource dataSource;

    static {
        Properties prop = new Properties();
        InputStream is = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            prop.load(is);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //工具类私有构造方法
    private DruidUtil() {}

    //从连接池中获取Connection
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("获取连接池Connection对象失败");
        }
        return conn;
    }

    //获取连接池Statement
    public static Statement getStatement(Connection conn) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("获取连接池Statment对象失败");
        }
        return stmt;
    }
    //获取连接池PreparedStatement对象
    public static PreparedStatement getPreparedStatement(Connection conn, String sql) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("获取连接池PreparedStatment对象失败");
        }
        return ps;
    }

    //释放连接回连接池
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        close(conn, stmt);
    }
    public static void release(Connection conn, Statement stmt) {

        close(conn, stmt);
    }

    private static void close(Connection conn, Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //封装DML
    public static int executeDML(String sql, Object... objs) {
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = DruidUtil.getPreparedStatement(conn, sql);
        try {
            conn.setAutoCommit(false);
            for (int i = 0; i < objs.length; i++) {
                ps.setObject(i + 1, objs[i]);
                System.out.println(i+":"+objs[i]);
            }
            int count = ps.executeUpdate();
            if (count == 1) { conn.commit(); }
            return count;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
                System.out.println("rollback失败");
            }
            e.printStackTrace();
            System.out.println("DML执行失败");
            return -1;
        } finally {
            DruidUtil.release(conn, ps, null);
        }
    }
}
