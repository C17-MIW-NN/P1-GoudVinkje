package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeHasIngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Simon van der Kooij
 * handles all data concerning ingredients.
 */

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    public final IngredientRepository ingredientRepository;
    public final RecipeHasIngredientRepository recipeHasIngredientRepository;
    public final RecipeRepository recipeRepository;

    public IngredientController(IngredientRepository ingredientRepository, RecipeHasIngredientRepository recipeHasIngredientRepository, RecipeRepository recipeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping({"/", "/overzicht"})
    public String showIngredients(Model datamodel) {
        datamodel.addAttribute("ingredients", ingredientRepository.findAll());

        return "ingredientOverview";
    }

    @GetMapping("/toevoegen")
    public String showIngredientForm(Model datamodel) {

        return showIngredientForm(datamodel, new Ingredient());
    }

    @GetMapping("/aanpassen/{ingredientDescription}")
    public String showEditIngredientForm(@PathVariable("ingredientDescription") String ingredientDescription, Model datamodel) {
        Optional<Ingredient> optionalIngredient = ingredientRepository.findByDescription(ingredientDescription);

        if (optionalIngredient.isPresent()) {
            return showIngredientForm(datamodel, optionalIngredient.get());
        }
        return "redirect:/ingredient/overzicht";
    }

    @PostMapping("/opslaan")
    public String saveIngredientForm(@ModelAttribute("ingredient") Ingredient ingredient, BindingResult result) {

        if (!result.hasErrors()) {
            ingredientRepository.save(ingredient);
        }
        return "redirect:/ingredient/overzicht";
    }

    @GetMapping("/verwijderen/{ingredientID}")
    public String deleteIngredient(@PathVariable("ingredientID") Long ingredientID) {
        ingredientRepository.deleteById(ingredientID);

        return "redirect:/ingredient/";
    }

    private String showIngredientForm(Model datamodel, Ingredient ingredient) {
        datamodel.addAttribute("ingredient", ingredient);
        return "ingredientForm";
    }

    @GetMapping("/aanvullen/{recipeID}")
    public String showAddCaloriesForm(@PathVariable("recipeID") Long recipeId, Model datamodel) {

        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();

        List<Ingredient> ingredients = new ArrayList<>();
        for (RecipeHasIngredient recipeHasIngredient : recipe.getRecipeHasIngredients()) {
            if (recipeHasIngredient.getIngredient().getCalories() == null) {
                ingredients.add(recipeHasIngredient.getIngredient());
            }
        }
        datamodel.addAttribute("ingredients", ingredients);
        return showAddCaloriesForm(datamodel, recipe);
    }

    private String showAddCaloriesForm(Model datamodel, Recipe recipe) {

        datamodel.addAttribute("formRecipe", recipe);

        return "addCaloriesForm";
    }


    @PostMapping("/cal-opslaan")
    public String saveAddCaloriesForm(@ModelAttribute("formRecipe") Recipe recipe) {
        //TODO ingredienten uit lijst opslaan
//        ingredientRepository.save(ingredient);
        return "redirect:/recept/" + recipe.getRecipeID();
    }
}
