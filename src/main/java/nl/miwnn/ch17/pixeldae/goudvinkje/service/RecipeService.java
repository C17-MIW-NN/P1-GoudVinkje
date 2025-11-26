package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
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

    public void addOriginalAuthorNameToTitle(Recipe recipe) {
        if (!recipe.getAuthor().equals(goudVinkjeUserService.getLoggedInUser())) {
            if (!recipe.getName().contains("à la")) {
                recipe.setName(recipe.getName() + " à la " + recipe.getAuthor().getUsername());
            }
            recipe.setPubliclyVisible(false);
        }
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
