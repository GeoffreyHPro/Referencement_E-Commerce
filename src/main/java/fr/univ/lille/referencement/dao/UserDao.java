package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Order;
import fr.univ.lille.referencement.model.User;
import fr.univ.lille.referencement.utils.ExceptionArticle;

/**
 * This interface is used to manage the users in the database.
 */
public interface UserDao {

    /**
     * This method is used to get a user by its email and password.
     * @param email the email of the user.
     * @param password the password of the user.
     * @return the user.
     * @throws ExceptionArticle if the user does not exist or if the password is wrong.
     */
    User getUserByEmailAndPassword(String email, String password) throws ExceptionArticle;

    /**
     * This method is used to add a user.
     * @param user the user to add.
     * @throws ExceptionArticle if the user already exists.
     */
    void addUser(User user) throws ExceptionArticle;

    /**
     * This method is used to add an order to a user.
     * @param id the id of the user.
     * @param order the order to add.
     */
    void addOrder(int id, Order order);

}
