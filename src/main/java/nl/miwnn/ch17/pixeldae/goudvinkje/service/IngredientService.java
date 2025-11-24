package nl.miwnn.ch17.pixeldae.goudvinkje.service;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 */

public class IngredientService {

    public final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    // if an ingredient already exists, use the prior existing ingrediënt
    public void preventDuplicateIngredients(Recipe recipe) {
        for (RecipeHasIngredient recipeHasIngredient : recipe.getRecipeHasIngredients()) {
            Ingredient ingredientFromForm = recipeHasIngredient.getIngredient();
            String description = ingredientFromForm.getDescription();
            Long ingredientID = ingredientFromForm.getIngredientId();

            Optional<Ingredient> sameIngredientAlreadyPresent =
                    ingredientRepository.findByDescription(description);

            if (sameIngredientAlreadyPresent.isPresent() &&
                    !sameIngredientAlreadyPresent.get().getIngredientId().equals(ingredientID)) {
                recipeHasIngredient.setIngredient(sameIngredientAlreadyPresent.get());
                if (ingredientID != null) {
                    ingredientRepository.deleteById(ingredientID);
                }
            }

            ingredientRepository.save(recipeHasIngredient.getIngredient());
            recipeHasIngredient.setRecipe(recipe);
        }

    }
}
