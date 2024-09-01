package fr.univ.lille.referencement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Lot;
import fr.univ.lille.referencement.model.Perishable;
import fr.univ.lille.referencement.utils.ExceptionArticle;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


/**
 * This class is the implementation of the PerishableDao interface. It is used to manage the perishables in the database.
 */
@Component
@Transactional
public class PerishableDaoImpl implements PerishableDao{
  @Autowired
  LotDao lotDao;

  /**
   * The entity manager used to manage the persistence context.
   */
  @PersistenceContext
	private EntityManager em;

  /**
   * Constructor of the class.
   * @param em the entity manager used to manage the persistence context.
   */
	public PerishableDaoImpl(EntityManager em) {
		this.em = em;
	}

  @Override
  public Perishable getPerishable(int id) throws ExceptionArticle {
    return em.createQuery("SELECT p FROM Perishable p WHERE id = :id", Perishable.class).setParameter("id", id).getSingleResult();
  }

  @Override
    public void addPerishable(Perishable perishable) throws ExceptionArticle{
      TypedQuery<Perishable> query = em.createQuery("SELECT u FROM Perishable u WHERE name = :name AND price = :price AND img = :img", Perishable.class);
      query.setParameter("name", perishable.getName());
      query.setParameter("price", perishable.getPrice());
      query.setParameter("img", perishable.getImg());
      List<Perishable> perishables = query.getResultList();
      if(perishables.isEmpty()){
        em.persist(perishable);
      }
    }

    @Override
    public void addPerishable(Perishable perishable, Lot lot) throws ExceptionArticle {
      List<Lot> l = new ArrayList<>();
      l.add(lot);
      perishable.setLot(l);
      addPerishable(perishable);
      lot.setPerishable(perishable);
      lotDao.addLot(lot);
    }

    @Override
    public void addLot(Perishable perishable, Lot lot) {
        perishable.getLot().add(lot);
        lot.setPerishable(perishable);
        lotDao.addLot(lot);
        em.persist(perishable);
    }

}
