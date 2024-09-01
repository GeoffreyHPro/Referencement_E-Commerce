package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderTest {
    static Order order;
    static OrderLine orderLine;
    static Imperishable imperishable;
    static List<OrderLine> listOrderLine;

    @Before
    public void init() {

        order = new Order("2023-12-31");
        imperishable = new Imperishable("chemise", 21.0, "img.png", 24);
        orderLine = new OrderLine(imperishable);
        listOrderLine = new ArrayList<>();
        order.setLines(listOrderLine);
        listOrderLine.add(orderLine);
    }

    @Test
    public void OrderConstructorTest() {
        assertEquals("2023-12-31", order.getDeliveryDate());
        Assertions.assertEquals(StatusCommand.NOT_BUYED, order.getStatus());
        assertEquals(1, order.getLines().size());
        Assertions.assertEquals(orderLine, order.getLines().get(0));
    }
}
