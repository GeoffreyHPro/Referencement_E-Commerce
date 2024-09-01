package fr.univ.lille.referencement.dao;
import fr.univ.lille.referencement.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is the implementation of the CategoryDao interface. It is used to manage the categories in the database.
 */
@Component
@Transactional
public class CategoryDaoImpl implements CategoryDao {

    /**
     * The entity manager used to manage the categories in the database.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * This constructor is used to set the entity manager.
     * @param em The entity manager to set.
     */
    public CategoryDaoImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void addCategory(Category category) {
        em.persist(category);
    }

    @Override
    public void deleteCategory(int id) {
        em.createQuery("DELETE FROM Category c WHERE id =:id").setParameter("id", id).executeUpdate();
    }

    @Override
    public Category updateCategory(Category category) {
        return em.merge(category);
    }

    @Override
    public Category getCategory(int id) {
        return em.createQuery("SELECT c FROM Category c WHERE id =:id", Category.class).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Category> getAllCategories() {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
}
