package fr.univ.lille.referencement.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Category is the class that represent the categories in the store. It contains Articles.
 */
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    /**
     * The name of the category.
     */
    @Id
    String name;

    /**
     * The articles of the category.
     */
    @ManyToMany
    List<Article> articles;
}
