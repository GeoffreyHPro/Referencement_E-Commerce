package fr.univ.lille.referencement.dao;

import fr.univ.lille.referencement.model.Category;

import java.util.List;

/**
 * This interface is used to manage the categories in the database.
 */
public interface CategoryDao {
    /**
     * This method is used to add a category in the database.
     * @param category The category to add in the database.
     */
    void addCategory(Category category);

    /**
     * This method is used to delete a category from the database.
     * @param id The id of the category to delete from the database.
     */
    void deleteCategory(int id);

    /**
     * This method is used to update a category in the database.
     * @param category The category to update in the database.
     * @return The updated category.
     */
    Category updateCategory(Category category);

    /**
     * This method is used to get a category from the database.
     * @param id The id of the category to get from the database.
     * @return The category from the database.
     */
    Category getCategory(int id);

    /**
     * This method is used to get all categories from the database.
     * @return The list of all categories from the database.
     */
    List<Category> getAllCategories();
}
