package fr.univ.lille.referencement.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.Category;
import fr.univ.lille.referencement.utils.ExceptionArticle;
import fr.univ.lille.referencement.model.Imperishable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * This class is the implementation of the ArticleDao interface. It is used to manage the articles in the database.
 */
@Component
@Transactional
public class ArticleDaoImpl implements ArticleDao {

    /**
     * The error messages.
     */
    private static final String ERR_MSG_ARTICLE_EXISTS = "Article already exists";
    private static final String ERR_MSG_ARTICLE_DO_NOT_EXISTS = "Article doesn't exist";
    private static final String ERR_MSG_PRICE_RANGE = "Minimum price is greater than maximum price";

    /**
     * The entity manager used to manage the articles in the database.
     */
    @PersistenceContext
	private EntityManager em;

    /**
     * The constructor of the ArticleDaoImpl class.
     * @param em The entity manager used to manage the articles in the database.
     */
	public ArticleDaoImpl(EntityManager em) {
		this.em = em;
	}

    @Override
    public void addArticle(Article article) throws ExceptionArticle {
      List<Article> articles = em.createQuery("SELECT a FROM Article u WHERE a.name = :name", Article.class).setParameter("email", article.getName()).getResultList();
        if (!articles.isEmpty()) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, null);
        }
        em.persist(article);
    }

    @Override
    public void deleteArticle(int id) {
        try {
            em.createQuery("DELETE FROM Article a WHERE id = :id").setParameter("id", id).executeUpdate();
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, e);
        }
    }

    @Override
    public Article updateArticle(int id, String name, double price, String description) {
        try {
            em.createQuery("UPDATE Article a SET price =:price, name =:name, description =:description WHERE id = :id").setParameter("id", id).setParameter("name", name).setParameter("price", price).setParameter("description", description).executeUpdate();
            return getArticle(id);
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, e);
        }
    }

    @Override
    public Article updateArticle(Article article) {
        try {
            em.createQuery("UPDATE Article a SET price =:price, name =:name WHERE id = :id").setParameter("id", article.getId()).setParameter("name", article.getName()).setParameter("price", article.getPrice()).executeUpdate();
            return getArticle(article.getId());
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, e);
        }
    }

    @Override
    public Article getArticle(int id) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE id =:id", Imperishable.class).setParameter("id", id).getSingleResult();
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, e);
        }
    }

    @Override
    public List<Article> getAllArticles() {
      return em.createQuery("SELECT a FROM Article a", Article.class).getResultList();
    }

    @Override
    public List<Article> getArticleByCategory(Category category) {
        try {
            return em.createQuery("SELECT a FROM Article a WHERE category =:category", Article.class).setParameter("category", category).getResultList();
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_EXISTS, e);
        }
    }

    @Override
    public List<Article> getArticleByPriceRange(double min, double max) throws ExceptionArticle {
        if (min > max) {
            throw new ExceptionArticle(ERR_MSG_PRICE_RANGE, null);
        }
        return em.createQuery("SELECT a FROM Article a WHERE price >=:min AND price <=:max", Article.class).setParameter("min", min).setParameter("max", max).getResultList();
    }

    @Override
    public void addImg(int id, String path) {

        try{
            Article article = em.createQuery("SELECT a FROM Article a WHERE a.id = :id", Article.class).setParameter("id", id).getSingleResult();
            if (article != null) {
                TypedQuery<Article> query = em.createQuery("UPDATE Article a SET a.img = :img WHERE a.id = :id", Article.class);
                query.setParameter("img", path).setParameter("id", id).executeUpdate();
            }
        } catch (Exception e) {
            throw new ExceptionArticle(ERR_MSG_ARTICLE_DO_NOT_EXISTS, e);
        }
    }
}
