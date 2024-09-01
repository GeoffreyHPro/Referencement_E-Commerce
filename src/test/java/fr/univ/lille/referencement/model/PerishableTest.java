package fr.univ.lille.referencement.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PerishableTest {

   static Perishable perishable;
   static Category category;
   static List<Category> listeCategories;
   static List<Lot> listeLot;
   static Lot lot;

   @Before
   public void init(){
      perishable = new Perishable("confiture", 50.9,"default.png"); 
      category = new Category("alimentaire", new ArrayList<>()); 
      listeCategories = new ArrayList<>();
      perishable.setCategories(new ArrayList<>());
      lot = new Lot(50, "2024-05-14");
      listeLot = new ArrayList<>();
      listeLot.add(lot);
      perishable.setLot(listeLot);
   }
    
   @Test
   public void PerishableConstructorTest() {
      assertEquals("confiture", perishable.getName());
      assertEquals(50.9, perishable.getPrice(), 0.0);
      assertEquals("default.png", perishable.getImg());
      System.out.println(perishable.getCategories());
      
      assertEquals( new ArrayList<>(), perishable.getCategories());

   }

   @Test
   public void InsertCategoriesInImperishableTest(){
      assertEquals(new ArrayList<>(), perishable.getCategories());
      listeCategories.add(category);
      perishable.setCategories(listeCategories);
      assertEquals(1,perishable.getCategories().size());
      assertEquals("alimentaire", perishable.getCategories().get(0).getName());
   }

   @Test
   public void ImperishableChangeNameTest(){
      perishable.setName("velo");
      assertEquals("velo",perishable.getName() );
   }

   @Test 
   public void ImperishableChangePriceTest(){
      perishable.setPrice(20.0);
      assertEquals(20.0, perishable.getPrice(), 0.0); 
   }

   @Test 
   public void ImperishableChangeQuantityTest(){

      perishable.getLot().get(0).setQuantity(20);;
      assertEquals(20, perishable.getQuantity()); 
   }

   @Test 
   public void ImperishableChangeImagePathTest(){
      perishable.setImg("image.png");
      assertEquals("image.png", perishable.getImg()); 
   }

   @Test 
   public void ImperishableDeleteTest(){
      perishable.delete(20);
      assertEquals(30, perishable.getQuantity()); 
   }

   @Test 
   public void ImperishableIsPerishableTest(){
   assertTrue(perishable.isPerishable());
   } 

   @Test
   public void PerishableIsNearPerishedTest() {
      Lot nearPerishedLot = new Lot(10, LocalDate.now().plusDays(4).toString());
      perishable.getLot().add(nearPerishedLot);
      assertEquals(1, perishable.isPerishableInt());
   }

   @Test
   public void PerishableIsPerishedTest() {
      Lot perishedLot = new Lot(5, LocalDate.now().minusDays(1).toString());
      perishable.getLot().add(perishedLot);
      assertEquals(2, perishable.isPerishableInt());
   }

   @Test
   public void PerishableIsNotPerishedTest() {
      Lot nonPerishedLot = new Lot(15, LocalDate.now().plusDays(10).toString());
      perishable.getLot().add(nonPerishedLot);
      assertEquals(0, perishable.isPerishableInt());
   }

}
