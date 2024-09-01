package fr.univ.lille.referencement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Article is the abstract class that represents an article in the store.
 */
@Setter
@Getter
@Entity
@Inheritance
@Table(name = "articles")
public abstract class Article {

    private static final String LOREM_IPSUM = "Product description. Lorem ipsum dolor sit amet, consectetur adipisicing elit." ;

    /**
     * The id of the article.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     * The name of the article.
     */
    String name;

    /**
     * The price of the article.
     */
    double price;

    /**
     * The image of the article.
     */
    String img;

    /**
     * The description of the article.
     */
    String description;

    /**
     * The categories of the article.
     */
    @ManyToMany
    List<Category> categories;

    protected Article(){}

    /**
     * Constructor
     * @param name The name of the article.
     * @param price The price of the article.
     * @param img The image of the article.
     */
    protected Article(String name, double price, String img){
        this.name = name;
        this.img = img;
        this.price = price;
        this.categories = new ArrayList<>() ;
        this.description = LOREM_IPSUM ;
    }

    /**
     * To know if the article is perishable.
     * @return Returns true if the article is perishable, false otherwise.
     */
    public abstract Boolean isPerishable();

    /**
     * To delete a quantity of the article.
     * @param q The quantity to delete.
     */
    public abstract void delete(int q);

    /**
     * To add a quantity of the article.
     * @param q The quantity to add.
     */
    public abstract void add(int q);

    /**
     * To know the quantity of the article.
     * @return Returns the quantity of the article.
     */
    public abstract int getQuantity();

    /**
     * To know the categories names of the article.
     * @return Returns the categories names of the article.
     */
    public List<String> getCategoriesNames(){
        return this.categories.stream().map(Category::getName).toList();
    }

    /**
     * To know if the article is perished.
     * @return Returns 0 if the article is not perished, 1 if the article is near to be perished, 2 if the article is perished.
     */
    public abstract int isPerishableInt() ;
}
