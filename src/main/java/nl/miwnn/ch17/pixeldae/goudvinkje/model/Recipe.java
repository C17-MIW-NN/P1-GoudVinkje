package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @OneToMany(mappedBy = "recipe")
    private Collection<RecipeHasIngredient> ingredients;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps = new ArrayList<>();

    public Collection<RecipeHasIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<RecipeHasIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getId() {
        return recipeID;
    }

    public void setId(Long id) {
        this.recipeID = recipeID;
    }
}
