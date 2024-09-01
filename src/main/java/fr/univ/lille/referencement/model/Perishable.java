package fr.univ.lille.referencement.model;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Perishable article class (extends Article). It is an article that as a shelf life.
 */
@Entity
@Getter
@Setter
@ToString
public class Perishable extends Article{

    /**
     * List of lots of this article.
     */

    @OneToMany(cascade = CascadeType.ALL)
    List<Lot> lot;

    /**
     * Constructor.
     * @param name the name of the article.
     * @param price the price of the article.
     * @param img the image of the article.
     */
    public Perishable(String name, double price, String img){
      super(name, price, img);
    }

    public Perishable(){}

    /**
     * Check if the article is perishable.
     * @return true if the article is perishable, false otherwise.
     */
    @Override
    public Boolean isPerishable() {
      return true;
    }

    /**
     * Get the quantity of the article.
     * @return the quantity of the article.
     */
    @Override
    public int getQuantity() {
      Iterator<Lot> it = lot.iterator();
      int total = 0;
      while (it.hasNext()){
        Lot l = it.next();
        if (l.getExpirationDate().isAfter(LocalDateTime.now().minusDays(5))){
          int qtt = l.getQuantity();
          total += qtt;
        }
      }
      return total;
    }

    @Override
    public void delete(int q) {
      researchLot(q);
    }

    /**
     * Research a lot of the article.
     * @param q the quantity of the article.
     */
    public void researchLot(int q){
      List<Lot> result = lot.stream()
      .filter(l -> l.getExpirationDate().isAfter(LocalDateTime.now().plusDays(5)))
			.sorted((o1,o2)-> o2.getExpirationDate().compareTo(o2.getExpirationDate()))
			.toList();

      Iterator<Lot> it = result.iterator();
      while (q > 0) {
        Lot lotTmp = it.next() ;
        if (q > lotTmp.getQuantity()) {
          q -= lotTmp.getQuantity();
          lotTmp.setQuantity(0);
        }
        else {
          lotTmp.setQuantity(lotTmp.getQuantity() - q);
          return;
        }
      }
    }

    @Override
    public int isPerishableInt() {
        Iterator<Lot> it = lot.iterator();
        boolean isNearPerished = false;

        do{
          Lot l = it.next();
          int result = l.isPerishableInt();
          if (result == 1){
            isNearPerished = true;
          }
          if (result == 2){
            return 2;
          }
        } while (it.hasNext());
        if (isNearPerished){
            return 1;
        }
        return 0;
    }
    @Override
    public void add(int q) { /* We don't have to add perishable articles because if a perishable is returned by a client, it is destroyed */ }

}