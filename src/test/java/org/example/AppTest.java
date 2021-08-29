package org.example;

import static org.junit.Assert.assertTrue;

import com.join.web_server.controller.CartController;
import com.join.web_server.entity.Cart;
import com.join.web_server.service.CartService;
import com.join.web_server.service.Impl.CartServiceImpl;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );

    }
    @Test
    public void show() throws SQLException {
        CartController cartController = new CartController();
        CartService cartService = new CartServiceImpl();
        String username = "zz";
        List<Cart> list = cartService.showCart(username);
//        for(int i = 0; i<list.size(); i++){
//            System.out.println("list"+i+list.get(i).getId());
//        }
        System.out.println(list);
    }
}
