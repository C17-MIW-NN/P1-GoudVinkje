package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;

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

    // constructor
    public RecipeHasIngredient() {
    }

    public RecipeHasIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    // methods
    public double countCaloriesPerIngredient() {
        return (double) this.quantity * ingredient.getCalories();
    }

    // getters
    public Long getRecipeHasIngredientID() {
        return recipeHasIngredientID;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    // setters
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
