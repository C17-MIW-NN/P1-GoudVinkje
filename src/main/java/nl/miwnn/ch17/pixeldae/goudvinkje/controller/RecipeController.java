package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeHasIngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.StepRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * controlls everything concerning recipes
 */

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeHasIngredientRepository recipeHasIngredientRepository;
    private final StepRepository stepRepository;

    public RecipeController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeHasIngredientRepository recipeHasIngredientRepository, StepRepository stepRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.stepRepository = stepRepository;
    }

    // showRecipeOverview
    @GetMapping({"/", "/recept/", "/recept/lijst"})
    private String showRecipeOverview(Model datamodel) {

        datamodel.addAttribute("recipes", recipeRepository.findAll());

        return "showRecipeOverview";
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
        Recipe recipe = new Recipe();
        recipe.getSteps().add(new Step());

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

    @PostMapping("/recept/opslaan")
    public String saveRecipeForm(@ModelAttribute("formRecipe") Recipe recipe,
                                 BindingResult result, Model datamodel) {

        for (Step step : recipe.getSteps()) {
            step.setRecipe(recipe);
            System.err.println("ID:" + step.getStepId());
            System.err.println("De:" + step.getInstruction());
        }

//        preventDuplicateIngredients(recipe);


        if (!result.hasErrors()) {
            // remove all the ingredients; otherwise deleted ingredients will remain in the database
//            Recipe recipeFromDB = recipeRepository.findById(recipe.getRecipeID()).orElseThrow();
//            recipeFromDB.getRecipeHasIngredients().clear();
            recipeRepository.save(recipe);
        }

        return "redirect:/recept/" + recipe.getRecipeID();
    }

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

    @PostMapping("/recept/{recipeID}/ingredienttoevoegen")
    public String addRecipeIngredient(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Recipe recipe = recipeRepository.findById(recipeID).orElseThrow();
        Ingredient ingredient = new Ingredient();
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();

        recipeHasIngredient.setRecipe(recipe);
        recipeHasIngredient.setIngredient(ingredient);

        ingredientRepository.save(ingredient);
        recipeHasIngredientRepository.save(recipeHasIngredient);

        return "redirect:/recept/aanpassen/" + recipeID;
    }

        //recipeForm mappings related to steps
    @PostMapping("/recept/{recipeID}/staptoevoegen")
    public String addRecipeStep(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Recipe recipe = recipeRepository.findById(recipeID).orElseThrow();

        recipe.getSteps().add(new Step());
        for (Step step : recipe.getSteps()) {
            step.setRecipe(recipe);
        }
        recipeRepository.save(recipe);

        return "redirect:/recept/aanpassen/" + recipeID;
    }
    //TODO BindingResult toevoegen?
    //TODO methode werkt nu niet voor nieuwe recepten, omdat die geen ID hebben, dus de url is dan incompleet

    @PostMapping("/recept/{recipeId}/stap/verwijderen/{stepId}")
    public String deleteRecipeStep(@PathVariable("stepId") Long stepId,
                                   @PathVariable("recipeId") Long recipeId,
                                   Model datamodel) {

        stepRepository.deleteById(stepId);

        return "redirect:/recept/aanpassen/" + recipeId;
    }


    @PostMapping("/recept/{recipeId}/ingredient/verwijderen/{recipeHasIngredientId}")
    public String deleteRecipeIngredient(@PathVariable("recipeId") Long recipeId,
                                         @PathVariable("recipeHasIngredientId") Long ingredientId,
                                         Model datamodel) {

        recipeHasIngredientRepository.deleteById(ingredientId);

        return "redirect:/recept/aanpassen/" + recipeId;
    }


    private String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);

        return "recipeForm";
    }
}
