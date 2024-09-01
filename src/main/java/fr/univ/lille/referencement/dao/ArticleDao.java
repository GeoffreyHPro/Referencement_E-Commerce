package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Article;
import fr.univ.lille.referencement.model.Category;
import fr.univ.lille.referencement.utils.ExceptionArticle;

import java.util.List;

/**
 * This interface is used to manage the articles in the database.
 */
public interface ArticleDao {

    /**
     * This method is used to add an article in the database.
     * @param article The article to add in the database.
     * @throws ExceptionArticle If the article already exists in the database.
     */
    void addArticle(Article article) throws ExceptionArticle;

    /**
     * This method is used to delete an article from the database.
     * @param id The id of the article to delete from the database.
     */
    void deleteArticle(int id);

    /**
     * This method is used to update an article in the database.
     * @param id The id of the article to update in the database.
     * @param name The new name of the article.
     * @param price The new price of the article.
     * @return The updated article.
     */
    public Article updateArticle(int id, String name, double price, String description);

    /**
     * This method is used to update an article in the database.
     * @param article The article to update in the database.
     * @return The updated article.
     */
    Article updateArticle(Article article);

    /**
     * This method is used to get an article from the database.
     * @param id The id of the article to get from the database.
     * @return The article from the database.
     */
    Article getArticle(int id);

    /**
     * This method is used to get all the articles from the database.
     * @return The list of all the articles from the database.
     */
    List<Article> getAllArticles();

    /**
     * This method is used to get the articles of a category from the database.
     * @param category The category of the perishable articles to get from the database.
     * @return The list of the perishable articles of the category from the database.
     */
    List<Article> getArticleByCategory(Category category);

    /**
     * This method is used to get the perishable articles depending on a price range from the database.
     * @param min The minimum price of the perishable articles to get from the database.
     * @param max The maximum price of the perishable articles to get from the database.
     * @return The list of the perishable articles depending on the price range from the database.
     */
    List<Article> getArticleByPriceRange(double min, double max);

    /**
     * This method is used to add an image to an article in the database.
     * @param id The id of the article to add an image to in the database.
     * @param path The path of the image to add to the article in the database.
     */
    void addImg(int id, String path);


}
