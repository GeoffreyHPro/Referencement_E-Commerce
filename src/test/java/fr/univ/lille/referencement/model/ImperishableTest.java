package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;

import java.util.List;

public class ImperishableTest {

    static Imperishable imperishable;
    static Category category;
    static List<Category> listeCategories;

    @Before
    public void init(){
       imperishable = new Imperishable("chaise", 50.9,"default.png", 50); 
       category = new Category("electromenager", new ArrayList<>()); 
       listeCategories = new ArrayList<>();
    }
    
   @Test
   public void ImperishableConstructorTest() {
      assertEquals("chaise", imperishable.getName());
      assertEquals(50.9, imperishable.getPrice());
      assertEquals("default.png", imperishable.getImg());
      assertEquals(50, imperishable.getQuantity());
      assertEquals(new ArrayList<>(), imperishable.getCategories());
   }

    @Test
    public void InsertCategoriesInImperishableTest(){
        assertEquals(new ArrayList<>(), imperishable.getCategories());
        listeCategories.add(category);
        imperishable.setCategories(listeCategories);
        assertEquals(1,imperishable.getCategories().size());
        assertEquals("electromenager", imperishable.getCategories().get(0).getName());
    }

    @Test
    public void ImperishableChangeNameTest(){
        imperishable.setName("velo");
        assertEquals("velo",imperishable.getName() );
    }

    @Test
    public void ImperishableChangePriceTest(){
       imperishable.setPrice(20.0);
       assertEquals(20.0, imperishable.getPrice()); 
    }

    @Test 
    public void ImperishableChangeQuantityTest(){
       imperishable.setQuantity(40);
       assertEquals(40, imperishable.getQuantity()); 
    }

    @Test 
    public void ImperishableChangeImagePathTest(){
       imperishable.setImg("image.png");
       assertEquals("image.png", imperishable.getImg()); 
    }

    @Test 
    public void ImperishableDeleteTest(){
       imperishable.delete(20);
       assertEquals(30, imperishable.getQuantity()); 
    }

    @Test 
    public void ImperishableIsPerishableTest(){
      assertFalse(imperishable.isPerishable());
    } 

    @Test
    public void ImperishableDeleteExceedingQuantityTest() {
        imperishable.delete(60); // Attempt to delete more than available
        assertEquals(0, imperishable.getQuantity());
    }

    @Test
    public void ImperishableSetNegativeQuantityTest() {
        imperishable.setQuantity(-5);
        assertEquals(0, imperishable.getQuantity());
    }

    @Test
    public void ImperishableIsPerishableIntTest() {
        int isPerishableInt = imperishable.isPerishableInt();
        assertEquals(0, isPerishableInt);
    }

}
