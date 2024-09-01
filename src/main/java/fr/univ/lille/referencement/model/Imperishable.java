package fr.univ.lille.referencement.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * Imperishable is the class that represent the imperishable articles in the store. So, it contains articles that do not have shelf life.
 */
@Entity
@Getter @Setter
public class Imperishable extends Article{

    /**
     * The quantity of the imperishable article.
     */
    int quantity;

    /**
     * Constructor of Imperishable.
     * @param name the name of the imperishable article.
     * @param price the price of the imperishable article.
     * @param img the image of the imperishable article.
     * @param quantity the quantity of the imperishable article.
     */
    public Imperishable(String name, double price, String img, int quantity){
      super(name, price, img);
      this.name = name;
      this.img = img;
      this.price = price;
      this.quantity = quantity;
    }

    public Imperishable(){}

    /**
     * This method is used to change the quantity of the article.
     * @param q the new quantity of the article.
     */
    public void setQuantity(int q) {
        if (q < 0) {
            this.quantity = 0;
        } else {
            this.quantity = q;
        }
    }

    /**
     * This method is used to know if the article is perishable or not.
     * @return false because the article is not perishable.
     */
    @Override
    public Boolean isPerishable() {
        return false;
    }

    /**
     * This method is used to know the quantity of the article.
     * @return the quantity of the article.
     */
    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public void delete(int q) {
        if((this.quantity - q) >= 0){
            this.quantity = this.quantity - q;
        }else{
            this.quantity = 0;
        }
    }

    @Override
    public void add(int q) {
        this.quantity = this.quantity + q;
    }

    @Override
    public int isPerishableInt() {
        return 0;
    }

}
