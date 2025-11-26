package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Simon van der Kooij
 */

@Service
public class RecipeService {

    public final RecipeRepository recipeRepository;
    public final GoudVinkjeUserService goudVinkjeUserService;

    public RecipeService(RecipeRepository recipeRepository, GoudVinkjeUserService goudVinkjeUserService) {
        this.recipeRepository = recipeRepository;
        this.goudVinkjeUserService = goudVinkjeUserService;
    }

    // if the recipe already exists, remove all references to the ingredients;
    // otherwise references to deleted ingredients would remain in the RecipeHasIngredients table.
    public void ifRecipeExistsRemoveAllIngredients(Recipe recipe) {
        if (recipe.getRecipeID() == null) { return; }

        Recipe recipeFromDB = recipeRepository.findById(recipe.getRecipeID()).orElseThrow();
        recipeFromDB.getRecipeHasIngredients().clear();
    }

    public String saveRecipe(Recipe recipe) {
        recipeRepository.save(recipe);
        return "redirect:/recept/" + recipe.getRecipeID();
    }

    public Optional<Recipe> getOptionalRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId);
    }

    public Recipe makeDummyRecipeWithIngredients(Long recipeId) {
        Optional<Recipe> optionalRecipe = getOptionalRecipe(recipeId);
        if (optionalRecipe.isEmpty()) {
            return null;
        }

        return transferIngredientsBetweenRecipes(optionalRecipe.get(), makeDummyRecipe(optionalRecipe.get()));
    }

    private Recipe makeDummyRecipe(Recipe recipe) {
        Recipe dummyRecipe = new Recipe();
        dummyRecipe.setRecipeID(recipe.getRecipeID());
        return dummyRecipe;
    }

    private Recipe transferIngredientsBetweenRecipes(Recipe fromRecipe, Recipe toRecipe) {
        List<RecipeHasIngredient> dummyRecipeHasIngredients = new ArrayList<>();
        for (RecipeHasIngredient recipeHasIngredient : fromRecipe.getRecipeHasIngredients()) {
            if (recipeHasIngredient.getIngredient().getCalories() == null) {
                dummyRecipeHasIngredients.add(recipeHasIngredient);
            }
        }

        toRecipe.setRecipeHasIngredients(dummyRecipeHasIngredients);

        return toRecipe;
    }

    public void connectStepsToRecipe(Recipe recipe) {
        for (Step checkStep : recipe.getSteps()) {
            if (!checkStep.getInstruction().isEmpty()) {
                for (Step saveStep : recipe.getSteps()) { saveStep.setRecipe(recipe); }
            }
        }
    }

    public Recipe ifSomeoneElsesRecipeMakeCopy(Recipe recipe) {
        GoudVinkjeUser loggedInUser = goudVinkjeUserService.getLoggedInUser();
        if (!recipe.getAuthor().getUsername().equals(loggedInUser.getUsername())) {
            recipe = recipe.newCopiedRecipe(loggedInUser);
        } else {
            ifRecipeExistsRemoveAllIngredients(recipe);
        }
        recipe.setAuthor(loggedInUser);
        return recipe;
    }

}
