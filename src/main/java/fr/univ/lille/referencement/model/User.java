package fr.univ.lille.referencement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * User is the class that represents a user in the store that can order articles.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * The id of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The email of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The role of the user.
     */
    private Role role;

    /**
     * The list of orders of the user.
     */
    @OneToMany
    List<Order> order;

    /**
     * Constructor.
     * @param email the email of the user.
     * @param password the password of the user.
     * @param role the role of the user.
     */
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * The method to get the string representation of the user.
     * @return the string representation of the user.
     */
    @Override
    public String toString() {
        return "User [role=" + role + ", id=" + id + ", email=" + email + ", password=" + password + "]";
    }

    /**
     * The method to get the order that is not buyed.
     * @return the order that is not buyed.
     */
    public Order getNotBuyedCommand(){
      for (Order o : order) {
        if((StatusCommand.NOT_BUYED.equals(o.getStatus()))){
          return o;
        }
      }
      return null;
    }
}
