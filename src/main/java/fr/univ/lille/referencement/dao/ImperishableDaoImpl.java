package fr.univ.lille.referencement.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Imperishable;
import fr.univ.lille.referencement.utils.ExceptionArticle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * This class is used to define the methods that will be used to manipulate the imperishable table.
 */
@Component
@Transactional
public class ImperishableDaoImpl implements ImperishableDao{

  /**
   * The entity manager used to manage the imperishable articles in the database.
   */
  @PersistenceContext
	private EntityManager em;

  /**
   * This constructor is used to set the entity manager.
   * @param em The entity manager to set.
   */
	public ImperishableDaoImpl(EntityManager em) {
		this.em = em;
	}

  @Override
  public Imperishable getImperishable(int id) throws ExceptionArticle {
    return em.createQuery("SELECT u FROM Imperishable u WHERE id =:id", Imperishable.class).setParameter("id", id).getSingleResult();
  }

  @Override
  public List<Imperishable> getAllImperishablesWithStock() throws ExceptionArticle {
    return em.createQuery("SELECT u FROM Imperishable u WHERE quantity > 0", Imperishable.class).getResultList();
  }

    @Override
    public void addImperishable(Imperishable imperishable) throws ExceptionArticle {
      List<Imperishable> imperishables = em.createQuery("SELECT u FROM Imperishable u WHERE name = :name AND price = :price AND img = :img", Imperishable.class)
      .setParameter("name", imperishable.getName())
      .setParameter("price", imperishable.getPrice())
      .setParameter("img", imperishable.getImg())
      .getResultList();
      if(imperishables.isEmpty()){
        em.persist(imperishable);
      }
    }

  @Override
  public void updateImperishableStock(int id, int quantity) throws ExceptionArticle {
    em.createQuery("UPDATE Imperishable u SET quantity =:quantity WHERE id = :id").setParameter("id", id).setParameter("quantity", quantity).executeUpdate();
  }
}