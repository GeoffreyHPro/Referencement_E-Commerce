package fr.univ.lille.referencement.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Order;
import fr.univ.lille.referencement.model.User;
import fr.univ.lille.referencement.utils.ExceptionArticle;
import fr.univ.lille.referencement.utils.ExceptionConnexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * This class is the implementation of the UserDao interface. It is used to manage the users in the database.
 */
@Component
@Transactional
public class UserDaoJPAImpl implements UserDao{

    /**
     * The entity manager used to manage the persistence context.
     */
    @PersistenceContext
	private EntityManager em;

    /**
     * The order dao used to manage the orders in the database.
     */
    @Autowired
    OrderDao orderDao;

    /**
     * Constructor of the class.
     * @param em the entity manager used to manage the persistence context.
     */
	public UserDaoJPAImpl(EntityManager em) {
		this.em = em;
	}

    /**
     * This method is used to get a user by his id.
     * @param object the id of the user.
     * @return the user.
     */
    public User getUserById(Object object){
        return em.createQuery("SELECT u FROM User u WHERE id =:id", User.class).setParameter("id", object).getSingleResult();      
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws ExceptionArticle {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();

        if (user == null) {
            throw new ExceptionConnexion("User not found", null);
        } else if (!user.getPassword().equals(password)) {
            throw new ExceptionConnexion("Wrong password", null);
        }
        return user;
    }

    @Override
    public void addUser(User user) throws ExceptionArticle {
        List<User> users = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class).setParameter("email", user.getEmail()).getResultList();
        if (!users.isEmpty()) {
            throw new ExceptionConnexion("User already exists", null);
        }
        em.persist(user);
        user.setOrder(new ArrayList<>());
        Order o = new Order("");
        orderDao.addOrder(o);
        addOrder(user.getId(), o);
    }

    public void addOrder(int id, Order order){
      User u = getUserById(id);  
      
      u.getOrder().add(order);
      orderDao.addOrder(order);
      em.merge(u);
    }

}
