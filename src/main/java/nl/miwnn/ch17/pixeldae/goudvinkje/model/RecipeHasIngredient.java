package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Simon van der Kooij
 * This model brings ingredients and recipes together.
 */

@Entity
@Getter @Setter @NoArgsConstructor
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

    // constructors
    public RecipeHasIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public RecipeHasIngredient(Ingredient ingredient, int quantity) {
        this.ingredient = ingredient;
        this.quantity = quantity;
    }
}
