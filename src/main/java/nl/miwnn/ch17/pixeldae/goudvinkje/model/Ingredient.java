package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;

import java.util.Collection;

/**
 * @author Simon van der Kooij
 * this model handles all the ingredients
 */

@Entity
public class Ingredient {

    protected static final int AMOUNT_OF_GRAMS_IN_100_GRAM = 100;
    protected static final int AMOUNT_OF_TBSP_IN_100_GRAM = 7;
    protected static final int AMOUNT_OF_TSP_IN_100_GRAM = 33;

    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(unique = true)
    private String description;

    private double calories;

    private String quantityUnit;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RecipeHasIngredient> recipeHasIngredient;

    // constructor
    public Ingredient() {
    }

    // methods
    public int checkCaloryUnitFactor() {
        int factor = 1;
        if (this.quantityUnit.equals("g") || this.quantityUnit.equals("ml")) {
            factor = AMOUNT_OF_GRAMS_IN_100_GRAM;
        } else if (this.quantityUnit.equals("el")) {
            factor = AMOUNT_OF_TBSP_IN_100_GRAM;
        } else if (this.quantityUnit.equals("tl")) {
            factor = AMOUNT_OF_TSP_IN_100_GRAM;
        }
        return factor;
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

    public double getCorrectedCalories() {
        return Math.round((calories / checkCaloryUnitFactor()) * 100.0) / 100.0;
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
