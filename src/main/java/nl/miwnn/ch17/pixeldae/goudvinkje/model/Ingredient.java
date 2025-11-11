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

    public void setName(String name) {
        this.name = name;
    }

    public void setCaloriesPer100(int caloriesPer100) {
        this.caloriesPer100 = caloriesPer100;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public void setRecipes(Collection<RecipeHasIngredient> recipes) {
        this.recipes = recipes;
    }
}
