package fr.univ.lille.referencement.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * OrderLine class represents a line in an order.
 */
@Getter
@Setter
@Entity
public class OrderLine {

    /**
     * The id of the order line.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    /**
     * The quantity of the order line.
     */
    int quantity;

    /**
     * The order of the order line.
     */
    @ManyToOne
    Order order;

    /**
     * The article of the order line.
     */
    @ManyToOne
    Article article;

    public OrderLine() {}

    /**
     * Constructor.
     * @param article the article of the order line.
     */
    public OrderLine(Article article) {
        this.article = article;
        this.quantity = 0;
    }

    public void setQuantity(int q) {
      if (q >= 0 && q <= article.getQuantity()) {
          this.quantity = q;
      } else if (q > article.getQuantity()) {
          this.quantity = article.getQuantity();
      }
  }
}