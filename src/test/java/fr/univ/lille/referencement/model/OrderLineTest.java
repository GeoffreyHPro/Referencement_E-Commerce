package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class OrderLineTest {

    static OrderLine orderLine;
    static Imperishable imperishable;
    static Perishable perishable;
    static Lot lot;
    static Lot lot2;
    static List<Lot> listLot;

    @Before
    public void init(){
        imperishable = new Imperishable("chaise", 50.9,"default.png", 50);
        perishable = new Perishable("biscuits", 4.56, "biscuit.jpg");
        orderLine = new OrderLine(imperishable);
        lot = new Lot(2,"2025-01-01");
        lot2 = new Lot(5,"2025-01-01");
        listLot = new ArrayList<>();
        listLot.add(lot);
        perishable.setLot(listLot);
    }

    @Test
    public void orderLineContructorTest(){
        Assertions.assertEquals(0, orderLine.getQuantity());
        Assertions.assertEquals(imperishable, orderLine.getArticle());
    }

    @Test
    public void orderLineSetQuantityTest(){
        orderLine.setQuantity(10);
        Assertions.assertEquals(10, orderLine.getQuantity());
    }

    @Test
    public void orderLineChangeArticle(){
        Assertions.assertEquals(imperishable,orderLine.getArticle());
        orderLine.setArticle(perishable);
        Assertions.assertEquals(perishable,orderLine.getArticle());
    }

    @Test
    public void orderLineDeleteTest(){
        imperishable.delete(30);
        Assertions.assertEquals(20,imperishable.getQuantity());
        perishable.delete(1);
        Assertions.assertEquals(1,perishable.getQuantity());
        perishable.getLot().add(lot2);
        perishable.delete(3);
        Assertions.assertEquals(3,perishable.getQuantity());
    }
}
