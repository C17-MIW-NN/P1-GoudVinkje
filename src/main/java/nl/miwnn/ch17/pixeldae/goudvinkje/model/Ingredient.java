package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author Simon van der Kooij
 * this model handles all the ingredients
 */

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(unique = true)
    private String name;

    private int caloriesPer100;

    private String quantityUnit;

    public Ingredient() {
    }

    @OneToMany(mappedBy = "ingredient")
    private Collection<RecipeHasIngredient> recipes;

    public Collection<RecipeHasIngredient> getRecipes() {
        return recipes;
    }

    public void setIngredients(Collection<RecipeHasIngredient> recipes) {
        this.recipes = recipes;
    }
}
