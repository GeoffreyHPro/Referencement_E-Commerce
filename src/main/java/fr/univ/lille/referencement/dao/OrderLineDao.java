package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.OrderLine;

/**
 * This interface is used to manage the order lines in the database.
 */
public interface OrderLineDao {

  /**
   * This method is used to get an order line by its id.
   * @param id the id of the order line.
   * @return the order line.
   */
  OrderLine getOrderLine(int id);

  /**
   * This method is used to add an order line.
   * @param orderline the order line to add.
   */
  void addOrderLine(OrderLine orderline);

  /**
   * This method is used to add an order line to an order.
   * @param orderline the order line to add.
   * @param article the article to add.
   */
  void addOrderLine(OrderLine orderline, Article article);

  /**
   * This method is used to update an order line.
   * @param id the id of the order line.
   * @param quantity the quantity of the order line.
   */
  void updateOrderLine(int id, int quantity) ;

  /**
   * This method is used to verify the quantity of an order line.
   * @param id the id of the order line.
   * @return the boolean.
   */
  public Boolean verifyQuantity(int id);
}
