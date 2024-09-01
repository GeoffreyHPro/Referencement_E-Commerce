package fr.univ.lille.referencement.dao;

import org.springframework.stereotype.Component;
import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.OrderLine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 *  This class is the implementation of the OrderDao interface. It is used to manage the orders in the database.
 */
@Component
@Transactional
public class OrderLineDaoImpl implements OrderLineDao {

  /**
   * The entity manager used to manage the persistence context.
   */
  @PersistenceContext
  private EntityManager em;

  /**
   * Constructor of the class.
   * @param em the entity manager used to manage the persistence context.
   */
	public OrderLineDaoImpl(EntityManager em) {
		this.em = em;
	}

    @Override
    public void addOrderLine(OrderLine orderline) {
      em.persist(orderline);
    }


    @Override
    public void addOrderLine(OrderLine orderline, Article article) {
      orderline.setArticle(article);
      em.persist(orderline);
    }

  @Override
  public void updateOrderLine(int id, int quantity) {
    OrderLine l = getOrderLine(id);
    l.setQuantity( quantity);
    em.merge(l);
  }

  @Override
  public OrderLine getOrderLine(int id) {
    return em.createQuery("SELECT l FROM OrderLine l WHERE id=:id", OrderLine.class).setParameter("id", id)
        .getSingleResult();
  }

  /**
   * This method is used to verify the id of an order line in an order.
   * @param id the id of the order line.
   * @return true
   */
  public Boolean verifyQuantity(int id){
    return true;
  }

}
