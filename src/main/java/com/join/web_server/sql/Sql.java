package com.join.web_server.sql;

public class Sql {
    public static String selectByName = "select * from user where username = ? and role = ?;";

    public static String sign = "insert into user(username,userPwd,ads,phone,role) values(?,?,?,?,?);";

    public static String selectAll = "select * from drink;";

    public static String selectByType = "select * from drink where type = ?;";

    public static String selectSingle = "select * from drink where id = ?;";

    public static String selectShop = "select * from drink where sellerName = ?;";
    //商品描述模糊检索
    public static String selectByDescription = "select * from drink where description like %?%;";
    //上新
    public static String insert = "insert into drink(name,description,type,price,inventory,picture,sellerName) values(?,?,?,?,?,?,?);";
    //下架
    public static String deletePro = "delete from drink where id = ? and sellerName = ?;";

    public static String selectCart = "select * from cart where username = ? and pro_id = ?;";

    public static String updateCart = "update cart set quantity = ? ,totalPrice =? where id = ?;";

    //加入购物车
    public static String addCart = " insert into cart(username,pro_id,quantity,totalPrice) values(?,?,?,?);";
    //从购物车中删去
    public static String deleteCart = "delete from cart where id = ?;";
    //用户的购物车的所有商品展示
    public static String showCart = "select * from cart,drink where cart.pro_id = drink.id and username = ?; ";
    //商家修改商品库存
    public static String updateInventory = "update drink set inventory = ? where sellerName = ? and id = ?;";
    //商家修改商品单价(单价修改后用户购物车中总价修改)
    public static String updatePrice = "update drink set price = ?  where sellerName = ? and id = ?;";

    public static String addOrder = "insert into orders(order_uuid,username,state,createTime)" +
            "values(?,?,?,?);";

    public static String addOrderItem = "insert into order_item(order_uuid,pro_id,quantity,totalPrice)" +
            "values(?,?,?,?);";
    //获得购物车中数据和商品数据
    public static String sOrderCart = "select * from cart,drink where cart.pro_id = drink.id and cart.id = ?; ";

    public static String updateSend = "update orders set state = ? , sendTime = ? where order_uuid = ?;";

    public static String updateArrive = "update orders set state = ? , arriveTime = ? where order_uuid = ?;";

    public static String showOrder = "select *" +
            "from orders,order_item,drink " +
            "where orders.order_uuid = order_item.order_uuid " +
            "and order_item.pro_id = drink.id " +
            "and state = ? and username = ?;";
    public static String showOrderS = "select *" +
            "from orders,order_item,drink " +
            "where orders.order_uuid = order_item.order_uuid " +
            "and order_item.pro_id = drink.id " +
            "and state = ? and sellerName = ?;";
    //    public static String select_Page = "select * from drink order by id limit ? , ?;";
//    public static String countAll = "select count(*) from drink;";
}
