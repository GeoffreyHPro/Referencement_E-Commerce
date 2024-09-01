package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;

public class UserTest {
    static User user;

    @Before
    public void init() {
        user = new User("test@example.com", "password123", Role.EMPLOYEE);
        List<Order> orders = new ArrayList<>();
        Order buyedOrder = new Order();
        Order notBuyedOrder = new Order();
        notBuyedOrder.setStatus(StatusCommand.NOT_BUYED);
        orders.add(buyedOrder);
        orders.add(notBuyedOrder);
        user.setOrder(orders);
    }

    @Test
    public void userConstructorTest() {
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals(Role.EMPLOYEE, user.getRole());
        assertEquals(2, user.getOrder().size());
    }

    @Test
    public void userGetNotBuyedCommandTest() {
        Order notBuyedCommand = user.getNotBuyedCommand();
        assertEquals(StatusCommand.NOT_BUYED, notBuyedCommand.getStatus());
    }

    @Test
    public void userToStringTest() {
        String expectedString = "User [role=EMPLOYEE, id=0, email=test@example.com, password=password123]";
        assertEquals(expectedString, user.toString());
    }
}
