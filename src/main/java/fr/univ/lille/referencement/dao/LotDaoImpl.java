package fr.univ.lille.referencement.dao;

import java.time.LocalDateTime;
import java.util.List;

import fr.univ.lille.referencement.model.Perishable;
import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Lot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;


/**
 *  This class is the implementation of the LotDao interface. It is used to manage the lots in the database.
 */
@Component
@Transactional
public class LotDaoImpl implements LotDao {

    /**
     * This attribute allows the connection to the database.
     */
    @PersistenceContext
	private EntityManager em;

    /**
     * This constructor is used to create a LotDaoImpl object.
     * @param em The entity manager.
     */
	public LotDaoImpl(EntityManager em) {
		this.em = em;
	}

    @Override
    public Lot getLot(int id) {
        return em.createQuery("SELECT l FROM Lot l WHERE id =:id", Lot.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Lot> getLotBeforeDate(LocalDateTime date) {
        return em.createQuery("SELECT l FROM Lot l WHERE expirationDate >= :date", Lot.class).setParameter("date", date).getResultList();
    }

    @Override
    public void addLot(Lot lot) {
        TypedQuery<Lot> query = em.createQuery("SELECT l FROM Lot l WHERE expirationDate = :expirationDate", Lot.class);
        query.setParameter("expirationDate", lot.getExpirationDate());
        if(query.getResultList().isEmpty()){
            em.persist(lot);
        }
    }

    @Override
    public Lot deleteLot(int id) {
        Lot lot = getLot(id);
        Perishable perishable = lot.getPerishable();
        perishable.getLot().remove(lot) ;
        em.merge(perishable) ;
        em.remove(lot);
        return lot;
    }

    @Override
    public void updateLot(int id, int quantity, LocalDateTime date) {
      em.createQuery("UPDATE Lot l SET quantity =:quantity, expirationDate =:date WHERE id = :id").setParameter("id", id).setParameter("date", date).setParameter("quantity", quantity).executeUpdate();
    }
}
