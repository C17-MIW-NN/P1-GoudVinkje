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
    private String description;

    private int caloriesPer100;

    private String quantityUnit;

    @OneToMany(mappedBy = "ingredient")
    private Collection<RecipeHasIngredient> recipes;

    public Collection<RecipeHasIngredient> getRecipes() {
        return recipes;
    }

    // constructor
    public Ingredient() {
    }

    // getters
    public Long getIngredientId() {
        return ingredientId;
    }

    public String getDescription() {
        return description;
    }

    public int getCaloriesPer100() {
        return caloriesPer100;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    // setters
    public void setIngredients(Collection<RecipeHasIngredient> recipes) {
        this.recipes = recipes;
    }

    public void setDescription(String name) {
        this.description = name;
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
