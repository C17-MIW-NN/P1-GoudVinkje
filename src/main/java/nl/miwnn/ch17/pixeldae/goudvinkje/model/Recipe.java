package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

/**
 * @author Simon van der Kooij
 * This model handles every recipe
 */

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private Long recipeID;

    private String name;

    private String author;

    private LocalDate dateAdded;

    private String description;

    private int nrOfPortions;

    public Recipe() {
    }

    public Long getId() {
        return recipeID;
    }

    public void setId(Long id) {
        this.recipeID = recipeID;
    }
}
