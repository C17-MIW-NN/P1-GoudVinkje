package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeHasIngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.StepRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * controlls everything concerning recipes
 */

@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeHasIngredientRepository recipeHasIngredientRepository;
    private final StepRepository stepRepository;

    public RecipeController(RecipeRepository recipeRepository, RecipeHasIngredientRepository recipeHasIngredientRepository, StepRepository stepRepository) {
        this.recipeRepository = recipeRepository;
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
    private String showRecipeDeatil(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeID);

        if (!recipe.isPresent()) {
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
                                 BindingResult result, Model datamodel, @RequestParam String action) {

        for (Step step : recipe.getSteps()) {
            step.setRecipe(recipe);
        }

        if (action.equals("save")) {
            if (!result.hasErrors()) {
                recipeRepository.save(recipe);
            }
        }
        return "redirect:/";
    }

    @GetMapping("/recept/verwijderen/{recipeID}")
    public String deleteRecipe(@PathVariable("recipeID") Long recipeID) {
        recipeRepository.deleteById(recipeID);
        return "redirect:/recept/";
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

//    @PostMapping("/recept/{recipeId}/stap/verwijderen/{stepId}")
//    public String deleteRecipeStep(@PathVariable("stepId") Long stepId,
//                                   @PathVariable("recipeId") Long recipeID,
//                                   Model datamodel) {
//        Recipe recipeOfStep = recipeRepository.findById(recipeID).orElseThrow();
//        recipeOfStep.getSteps().removeIf(step -> step.getStepId() == stepId);
//        recipeRepository.save(recipeOfStep);
//
//        return showRecipeForm(datamodel, recipeOfStep);
//    }

    //TODO Is bovenstaande of onderstaande beter?


    @PostMapping("/recept/{recipeId}/stap/verwijderen/{stepId}")
//    @Transactional
    public String deleteRecipeStep(@PathVariable("stepId") Long stepId,
                                   @PathVariable("recipeId") Long recipeID,
                                   Model datamodel) {

        stepRepository.deleteById(stepId);

        return "redirect:/recept/aanpassen/" + recipeID;
    }


//    @PostMapping("/recept/{recipeId}/ingredient/verwijderen/{ingredientId}")
//    public String deleteRecipeIngredient(@PathVariable("recipeId") Long recipeId,
//                                         @PathVariable("ingredientId") Long ingredientId,
//                                         Model datamodel) {
//        recipeHasIngredientRepository.deleteById();
//    }


    private String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);

        return "recipeForm";
    }
}
