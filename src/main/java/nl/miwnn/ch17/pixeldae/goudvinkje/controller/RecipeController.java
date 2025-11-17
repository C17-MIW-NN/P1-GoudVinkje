package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * controls all data concerning recipes
 */

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeController(RecipeRepository recipeRepository,
                            IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    // showRecipeOverview
    @GetMapping({"/", "/recept/", "/recept/overzicht"})
    private String showRecipeOverview(Model datamodel) {
        datamodel.addAttribute("recipes", recipeRepository.findAll());

        return "recipeOverview";
    }

    // showRecipeDetail
    @GetMapping("/recept/{recipeID}")
    private String showRecipeDetail(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeID);

        if (recipe.isEmpty()) {
            return "redirect:/recept/lijst";
        }

        datamodel.addAttribute("recipe", recipe.get());

        return "recipeDetail";
    }

    // recipeForm
    @GetMapping("/recept/toevoegen")
    public String showRecipeForm(Model datamodel) {
        Recipe recipe = new Recipe(LocalDate.now());
        recipe.getSteps().add(new Step(1));
        recipe.getRecipeHasIngredients().add(new RecipeHasIngredient(new Ingredient()));

        return showRecipeForm(datamodel, recipe);
    }

    @GetMapping("/recept/aanpassen/{recipeID}")
    public String showEditRecipeForm(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeID);
        if (optionalRecipe.isPresent()) {
            return showRecipeForm(datamodel, optionalRecipe.get());
        }
        return "redirect:/";
    }

    private String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);

        return "recipeForm";
    }

    @PostMapping("/recept/opslaan")
    public String saveRecipeForm(@ModelAttribute("formRecipe") Recipe recipe,
                                 BindingResult result) {

        for (Step step : recipe.getSteps()) { step.setRecipe(recipe); }

        preventDuplicateIngredients(recipe);

        if (!result.hasErrors()) {
            ifRecipeExistsRemoveAllIngredients(recipe);
            recipeRepository.save(recipe);
        }

        return "redirect:/recept/" + recipe.getRecipeID();
    }

    // if the recipe already exists, remove all the ingredients;
    // otherwise deleted ingredients would remain in the database.
    private void ifRecipeExistsRemoveAllIngredients(Recipe recipe) {
        if (recipe.getRecipeID() == null) { return; }

        Recipe recipeFromDB = recipeRepository.findById(recipe.getRecipeID()).orElseThrow();
        recipeFromDB.getRecipeHasIngredients().clear();
    }

    // if the ingredient already exists, use the prior existing ingrediënt
    private void preventDuplicateIngredients(Recipe recipe) {
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

    @GetMapping("/recept/verwijderen/{recipeID}")
    public String deleteRecipe(@PathVariable("recipeID") Long recipeID) {
        recipeRepository.deleteById(recipeID);
        return "redirect:/recept/";
    }
}
