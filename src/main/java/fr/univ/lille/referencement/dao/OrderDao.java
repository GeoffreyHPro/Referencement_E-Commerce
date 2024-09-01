package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Order;
import fr.univ.lille.referencement.model.OrderLine;

import java.util.List;

/**
 * This interface is used to manage the orders in the database.
 */
public interface OrderDao {

    /**
     * This method is used to get an order by its id.
     * @param id the id of the order.
     * @return the order.
     */
    Order getOrder(int id);

    /**
     * This method is used to get all the orders.
     * @return the list of all the orders.
     */
    List<Order> getAllOrders();

    /**
     * This method is used to add an order.
     * @param order the order to add.
     */
    void addOrder(Order order);

    /**
     * This method is used to add an order line to an order.
     * @param id the id of the order.
     * @param line the order line to add.
     */
    void addOrderLine(int id, OrderLine line);

    Boolean validate(int id);
}
