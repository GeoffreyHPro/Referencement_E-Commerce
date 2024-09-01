package fr.univ.lille.referencement.dao;

import java.util.List;

import fr.univ.lille.referencement.model.StatusCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Order;
import fr.univ.lille.referencement.model.OrderLine;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * This class is the implementation of the OrderDao interface. It is used to manage the orders in the database.
 */
@Component
@Transactional
public class OrderDaoImpl implements OrderDao {

    /**
     * The order line dao used to manage the order lines in the database.
     */
    @Autowired
    OrderLineDao lineDao;

    /**
    * The entity manager used to manage the persistence context.
    */
    @PersistenceContext
	private EntityManager em;

    /**
    * Constructor of the class.
    * @param em the entity manager used to manage the persistence context.
    */
	public OrderDaoImpl(EntityManager em) {
		this.em = em;
	}

    @Override
    public Order getOrder(int id) {
        return em.createQuery("SELECT o FROM Order o WHERE id = :id", Order.class).setParameter("id", id).getSingleResult();
    }
    @Override
    public List<Order> getAllOrders() {
        return em.createQuery("SELECT o FROM Order o WHERE status = BUYED", Order.class).getResultList();
    }

    @Override
    public void addOrder(Order order) {
    em.persist(order);
    }

    @Override
    public void addOrderLine(int id, OrderLine line) {
        Order o = getOrder(id);
        int i = in(o, line);
        if (i != -1) {
            OrderLine l = o.getLines().get(i);
            o.getLines().get(i).setQuantity(line.getQuantity() + l.getQuantity() );
        } else {
            List<OrderLine> l = o.getLines();
            l.add(line);
            o.setLines(l);
            lineDao.addOrderLine(line);
        }
    }

    private int in(Order o, OrderLine line) {
        List<OrderLine> lines = o.getLines();
        int i = 0;
        for (OrderLine l : lines) {
            if (l.getArticle().getName().equals(line.getArticle().getName())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Boolean verifyQuantity(int id) {
        Order order = getOrder(id);
        List<OrderLine> lines = order.getLines();
        for (OrderLine l : lines) {
            if (Boolean.FALSE.equals((lineDao.verifyQuantity(l.getId())))) {
                return false;
            }
        }
        return true;
    }

    public Boolean validate(int id) {
        if (Boolean.TRUE.equals(verifyQuantity(id))) {
            decrementeStock(id);
            return true;
        }
        return false;
    }

    private void decrementeStock(int id) {
        Order order = getOrder(id);
        order.setDeliveryDate(String.valueOf(java.time.LocalDateTime.now().toString().split("T")[0]));
        order.setStatus(StatusCommand.BUYED);
        List<OrderLine> l = order.getLines();
        for (OrderLine line : l) {
            line.getArticle().delete(line.getQuantity());
            em.merge(line.getArticle());
        }
        em.merge(order);
    }

    public Order getOrderNotBuyed(int idUser){
        List<Order> o = em.createQuery("SELECT o FROM Order o WHERE status = NOT_BUYED AND userId =:id", Order.class).setParameter("id", idUser).getResultList();
        if(o.isEmpty()){
            return null;
        }
        return o.get(0);
    }
}