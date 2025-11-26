package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import jakarta.validation.Valid;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.*;
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
    private String showRecipeDetail(@PathVariable("recipeID") Long recipeId, Model datamodel) {

        Optional<Recipe> recipe = recipeService.getOptionalRecipe(recipeId);
        if (recipe.isEmpty()) {
            return "redirect:/recept/lijst";
        }
        datamodel.addAttribute("formRecipe", recipe.get());

        return "recipeDetail";
    }

    // recipeForm
    @GetMapping("/toevoegen")
    public String showAddRecipeForm(Model datamodel) {

        Recipe recipe = new Recipe(goudVinkjeUserService.getLoggedInUser());

        return showRecipeForm(datamodel, recipe);
    }

    @GetMapping("/aanpassen/{recipeID}")
    public String showEditRecipeForm(@PathVariable("recipeID") Long recipeId, Model datamodel) {

        Optional<Recipe> optionalRecipe = recipeService.getOptionalRecipe(recipeId);
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
    public String saveRecipeForm(@Valid @ModelAttribute("formRecipe") Recipe recipe,
                                 BindingResult result, Model datamodel,
                                 @RequestParam("action") String action ) {

        System.err.println(recipe);

        if (result.hasErrors()) {
            return showRecipeForm(datamodel, recipe);
        }

        System.err.println("TOT HIER 2");

        ingredientService.preventDuplicateIngredients(recipe);

        recipeService.connectStepsToRecipe(recipe);

        recipe = recipeService.ifSomeoneElsesRecipeMakeCopy(recipe);

        recipeService.saveRecipe(recipe);

        if ("saveAddCalories".equals(action)) {
            if (ingredientService.isIngredientWithoutCaloriesPresent(recipe.getRecipeHasIngredients())) {
                return "redirect:/ingredient/aanvullen/" + recipe.getRecipeID();
            }
        }

        return "redirect:/recept/" + recipe.getRecipeID();


    }

    @GetMapping("/verwijderen/{recipeID}")
    public String deleteRecipe(@PathVariable("recipeID") Long recipeID) {

        recipeRepository.deleteById(recipeID);
        return "redirect:/recept/overzicht";
    }
}
