package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;

/**
 * @author Simon van der Kooij
 */

public class RecipeService {

    public final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // if the recipe already exists, remove all references to the ingredients;
    // otherwise references to deleted ingredients would remain in the RecipeHasIngredients table.
    public void ifRecipeExistsRemoveAllIngredients(Recipe recipe) {
        if (recipe.getRecipeID() == null) { return; }

        Recipe recipeFromDB = recipeRepository.findById(recipe.getRecipeID()).orElseThrow();
        recipeFromDB.getRecipeHasIngredients().clear();
    }


}
