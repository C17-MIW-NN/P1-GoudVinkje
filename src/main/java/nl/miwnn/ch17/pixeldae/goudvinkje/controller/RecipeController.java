package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import jakarta.validation.Valid;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.*;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.GoudVinkjeUserService;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.IngredientService;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * controls all data concerning recipes
 */

@Controller
@RequestMapping("/recept")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final GoudVinkjeUserService goudVinkjeUserService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public RecipeController(RecipeRepository recipeRepository,
                            IngredientRepository ingredientRepository,
                            GoudVinkjeUserService goudVinkjeUserService,
                            RecipeService recipeService, IngredientService ingredientService) {
        this.recipeRepository = recipeRepository;
        this.goudVinkjeUserService = goudVinkjeUserService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    // showRecipeOverview
    @GetMapping({ "/", "/overzicht"})
    private String showRecipeOverview(Model datamodel) {

        GoudVinkjeUser loggedInUser = goudVinkjeUserService.getLoggedInUser();
        datamodel.addAttribute("publicRecipes",
                recipeRepository.findAllByPubliclyVisibleAndAuthorNot(true, loggedInUser));
        datamodel.addAttribute("ownRecipes",
                recipeRepository.findAllByAuthor(loggedInUser));

        return "recipeOverview";
    }

    // showRecipeDetail
    @GetMapping("/{recipeID}")
    private String showRecipeDetail(@PathVariable("recipeID") Long recipeID, Model datamodel) {

        Optional<Recipe> recipe = recipeRepository.findById(recipeID);
        if (recipe.isEmpty()) {
            return "redirect:/recept/lijst";
        }
        datamodel.addAttribute("recipe", recipe.get());

        return "recipeDetail";
    }

    // recipeForm
    @GetMapping("/toevoegen")
    public String showAddRecipeForm(Model datamodel) {

        Recipe recipe = new Recipe(goudVinkjeUserService.getLoggedInUser());

        return showRecipeForm(datamodel, recipe);
    }

    @GetMapping("/aanpassen/{recipeID}")
    public String showEditRecipeForm(@PathVariable("recipeID") Long recipeID, Model datamodel) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeID);
        if (optionalRecipe.isPresent()) {
            return showRecipeForm(datamodel, optionalRecipe.get());
        }
        return "redirect:/";
    }

    private String showRecipeForm(Model datamodel, Recipe recipe) {

        datamodel.addAttribute("formRecipe", recipe);
        datamodel.addAttribute("quantityUnits", Ingredient.QuantityUnit.values());

        return "recipeForm";
    }

    @PostMapping("/opslaan")
    public String saveRecipeForm(@Valid @ModelAttribute("formRecipe") Recipe recipe, BindingResult result) {

        for (Step step : recipe.getSteps()) { step.setRecipe(recipe); }
        ingredientService.preventDuplicateIngredients(recipe);

        if (result.hasErrors()) { //show validation error messages in form
            return "recipeForm";
        } else { // If it's someone else's recipe, make a copy.
            GoudVinkjeUser loggedInUser = goudVinkjeUserService.getLoggedInUser();
            if (!recipe.getAuthor().getUsername().equals(loggedInUser.getUsername())) {
                recipe = recipe.newCopiedRecipe(loggedInUser);
            } else {
                recipeService.ifRecipeExistsRemoveAllIngredients(recipe);
            }
            recipe.setAuthor(loggedInUser);
            recipeRepository.save(recipe);
        }
        return "redirect:/recept/" + recipe.getRecipeID();
    }

    @GetMapping("/verwijderen/{recipeID}")
    public String deleteRecipe(@PathVariable("recipeID") Long recipeID) {

        recipeRepository.deleteById(recipeID);
        return "redirect:/recept/overzicht";
    }
}
