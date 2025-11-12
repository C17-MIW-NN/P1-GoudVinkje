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

//    @Column(unique = true)
    private String description;

    private double calories;

    private String quantityUnit;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RecipeHasIngredient> recipeHasIngredient;

    // constructor
    public Ingredient() {
    }

    // getters
    public Collection<RecipeHasIngredient> getRecipeHasIngredient() {
        return recipeHasIngredient;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getDescription() {
        return description;
    }

    public double getCalories() {
        return calories;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    // setters


    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public void setRecipeHasIngredient(Collection<RecipeHasIngredient> recipeHasIngredient) {
        this.recipeHasIngredient = recipeHasIngredient;
    }

    @Override
    public String toString() {
        return String.format("ingredientId: %s, description: %s, calories: %s, quantityUnit: %s, recipeHasIngredient: %s", this.ingredientId, this.description, this.calories, this.quantityUnit, this.recipeHasIngredient);
    }
}
