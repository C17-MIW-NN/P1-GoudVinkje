package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.*;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.ImageService;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.GoudVinkjeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * controls all data concerning recipes
 */

@Controller
@RequestMapping("/recept")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final GoudVinkjeUserService goudVinkjeUserService;
    private final ImageService imageService;

    public RecipeController(RecipeRepository recipeRepository,
                            IngredientRepository ingredientRepository,
                            GoudVinkjeUserService goudVinkjeUserService,
                            ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.goudVinkjeUserService = goudVinkjeUserService;
        this.imageService = imageService;
    }

    // showRecipeOverview
    @GetMapping({ "/", "/overzicht"})
    private String showRecipeOverview(Model datamodel) {
        GoudVinkjeUser loggedInUser = goudVinkjeUserService.getLoggedInUser();
        datamodel.addAttribute("publicRecipes", recipeRepository.findAllByAuthorNot(loggedInUser));
        datamodel.addAttribute("ownRecipes", recipeRepository.findAllByAuthor(loggedInUser));

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
        Recipe recipe = new Recipe(LocalDate.now());
        recipe.getSteps().add(new Step(1));
        recipe.getRecipeHasIngredients().add(new RecipeHasIngredient(new Ingredient()));
        recipe.setAuthor(goudVinkjeUserService.getLoggedInUser());
        recipe.setPubliclyVisible(true);

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

        return "recipeForm";
    }

    @PostMapping("/opslaan")
    public String saveRecipeForm(@ModelAttribute("formRecipe") Recipe recipe,
                                 BindingResult result) {

        for (Step step : recipe.getSteps()) { step.setRecipe(recipe); }

        preventDuplicateIngredients(recipe);

        if (!result.hasErrors()) {
            ifRecipeExistsRemoveAllIngredients(recipe);

            GoudVinkjeUser loggedInUser = goudVinkjeUserService.getLoggedInUser();
            if (!recipe.getAuthor().getUsername().equals(loggedInUser.getUsername())) {
                recipe = recipe.newCopiedRecipe(loggedInUser);
            }

            recipe.setAuthor(loggedInUser);
            recipeRepository.save(recipe);
        }

        return "redirect:/recept/" + recipe.getRecipeID();
    }

    // if an ingredient already exists, use the prior existing ingrediënt
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

    // if the recipe already exists, remove all references to the ingredients;
    // otherwise references to deleted ingredients would remain in the RecipeHasIngredients table.
    private void ifRecipeExistsRemoveAllIngredients(Recipe recipe) {
        if (recipe.getRecipeID() == null) { return; }

        Recipe recipeFromDB = recipeRepository.findById(recipe.getRecipeID()).orElseThrow();
        recipeFromDB.getRecipeHasIngredients().clear();
    }

    @GetMapping("/verwijderen/{recipeID}")
    public String deleteRecipe(@PathVariable("recipeID") Long recipeID) {
        recipeRepository.deleteById(recipeID);
        return "redirect:/recept/";
    }
}
