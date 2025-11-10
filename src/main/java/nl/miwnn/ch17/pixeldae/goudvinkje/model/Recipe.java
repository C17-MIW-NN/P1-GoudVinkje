package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Simon van der Kooij
 *
 */

@Entity
public class Recipe {

    @Id @GeneratedValue
    private Long id;
    private int recipeID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
