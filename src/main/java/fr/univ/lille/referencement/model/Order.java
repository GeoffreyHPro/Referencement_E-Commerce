package fr.univ.lille.referencement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Order class represents an order made by a client. It contains the delivery date and the list of order lines.
 */
@Getter @Setter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    /**
     * The id of the order.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * The delivery date of the order.
     */
    private String deliveryDate;

    /**
     * The status of the order.
     */
    private StatusCommand status;

    /**
     * The list of order lines.
     */
    @OneToMany
    private List<OrderLine> lines;

    /**
     * The user who made the order.
     */
    @ManyToOne
    User user;

    public Order() {}

    /**
     * Constructor.
     * @param deliveryDate the delivery date of the order.
     */
    public Order(String deliveryDate) {
        this.status = StatusCommand.NOT_BUYED;
        this.deliveryDate = deliveryDate;
        this.lines = new ArrayList<>();
    }

}