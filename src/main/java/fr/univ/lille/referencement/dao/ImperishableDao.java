package fr.univ.lille.referencement.dao;

import java.util.List;

import fr.univ.lille.referencement.model.Imperishable;
import fr.univ.lille.referencement.utils.ExceptionArticle;

/**
 * This interface is used to define the methods that will be used to manipulate the imperishable table.
 */
public interface ImperishableDao {

    /**
     * This method is used to get an imperishable article from the database.
     *
     * @param id The id of the imperishable to get from the database.
     * @return The imperishable article from the database.
     * @throws ExceptionArticle If the imperishable doesn't exist in the database.
     */
    Imperishable getImperishable(int id) throws ExceptionArticle;

    /**
     * This method is used to get all the imperishable articles from the database.
     *
     * @return The list of all the imperishable articles from the database.
     * @throws ExceptionArticle If there is an error while getting the imperishable articles from the database.
     */
    List<Imperishable> getAllImperishablesWithStock() throws ExceptionArticle;

    /**
     * This method is used to add an imperishable article in the database.
     *
     * @param imperishable The imperishable to add in the database.
     * @throws ExceptionArticle If the imperishable already exists in the database.
     */
    void addImperishable(Imperishable imperishable) throws ExceptionArticle;

    /**
     * This method is used to update the stock of an imperishable article from the database.
     *
     * @param id       The id of the imperishable to update from the database.
     * @param quantity The quantity of the imperishable to update from the database.
     * @throws ExceptionArticle If the imperishable doesn't exist in the database.
     */
    void updateImperishableStock(int id, int quantity) throws ExceptionArticle;
}