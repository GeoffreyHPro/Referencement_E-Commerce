package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Lot;
import fr.univ.lille.referencement.model.Perishable;
import fr.univ.lille.referencement.utils.ExceptionArticle;

/**
 * This interface is used to manage the perishables in the database.
 */
public interface PerishableDao {

    /**
     * This method is used to get a perishable by its id.
     * @param id the id of the perishable.
     * @return the perishable.
     */
    Perishable getPerishable(int id) throws ExceptionArticle;

    /**
     * This method is used to add a perishable.
     *
     * @param perishable the perishable to add.
     */
    void addPerishable(Perishable perishable) throws ExceptionArticle;

    /**
     * This method is used to add a perishable to a lot.
     *
     * @param perishable the perishable to add.
     * @param lot        the lot to add.
     */
    void addPerishable(Perishable perishable, Lot lot) throws ExceptionArticle;

    void addLot(Perishable perishable, Lot lot) throws ExceptionArticle;

}
