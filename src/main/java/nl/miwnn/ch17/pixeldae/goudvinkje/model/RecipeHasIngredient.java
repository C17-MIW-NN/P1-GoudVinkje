package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Simon van der Kooij
 * This model brings ingredients and recipes together.
 */

@Entity
@IdClass(RecipeHasIngredient.RecipeHasIngredientsId.class)

public class RecipeHasIngredient {

    @Id
    private Long recipeID;

    @Id
    private Long ingredientID;

    private int quantity;

    public static class RecipeHasIngredientsId implements Serializable {
        private Long recipeID;
        private Long ingredientID;
    }

    public RecipeHasIngredient() {
    }

    @Override

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RecipeHasIngredientsId that)) return false;
        return Objects.equals(recipeID, that.recipeID) &&
                Objects.equals(ingredientID, that.ingredientID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeID, ingredientID);
    }

}
