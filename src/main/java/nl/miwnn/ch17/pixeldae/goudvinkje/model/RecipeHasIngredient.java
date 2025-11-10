package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Simon van der Kooij
 * This model brings ingredients and recipes together.
 */

@Entity
public class RecipeHasIngredient {

    @Id
    @GeneratedValue
    private Long recipeHasIngredientID;

    @ManyToOne
    @JoinColumn(name = "recipe_recipe_id")
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "ingredient_ingredient_id")
    private Ingredient ingredient;

    private int quantity;

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public RecipeHasIngredient() {
    }

}
