package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LotTest {
    static Lot lot;
    static Perishable perishable;

    @Before
    public void init() {
        try {
            lot = new Lot(50, "2023-10-30");
            perishable = new Perishable("fraise", 5.9, "image.jpg");
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lotConstructorTest() {
        assertEquals(50, lot.getQuantity());
        assertEquals("2023-10-30", lot.getExpirationDateFormatted());
    }

    @Test
    public void lotIsPerishableIntTest() {
        lot.setExpirationDate(LocalDate.now().minusDays(1).atStartOfDay());
        assertEquals(2, lot.isPerishableInt());

        lot.setExpirationDate(LocalDate.now().plusDays(3).atStartOfDay());
        assertEquals(1, lot.isPerishableInt());

        lot.setExpirationDate(LocalDate.now().plusDays(10).atStartOfDay());
        assertEquals(0, lot.isPerishableInt());
    }

    @Test
    public void LotChangeQuantityTest() {
        lot.setQuantity(20);
        Assertions.assertEquals(20, lot.getQuantity());
    }

    @Test
    public void AssociateLotWithPerishableTest(){
        lot.setPerishable(perishable);;
        Assertions.assertEquals(perishable,lot.getPerishable());
    }
}
