package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 * controlls everything concerning the homepage
 */


@Controller
public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // showRecipeOverview
    @GetMapping({"/", "/recipe/"})
    private String showRecipeOverview(Model datamodel) {

        datamodel.addAttribute("recipes", recipeRepository.findAll());

        return "showRecipeOverview";
    }

    // recipeForm methods
    @GetMapping("/recipe/add")
    public String showAddRecipeForm(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        return showRecipeForm(datamodel, new Recipe());
    }

    @GetMapping("/recipe/edit/{recipeID}")
    public String showEditRecipeForm(@PathVariable("recipeID") Long recipeID, Model datamodel) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeID);
        if (optionalRecipe.isPresent()) {
            return showRecipeForm(datamodel, optionalRecipe.get());
        }
        return "redirect:/";
    }

    @PostMapping("/recipe/save")
    public String saveRecipeForm(@ModelAttribute("formRecipe") Recipe recipe,
                                 BindingResult result, Model datamodel, @RequestParam String action) {
        if (action.equals("save")) {
            if (!result.hasErrors()) {
                recipeRepository.save(recipe);
            }
        }
        return "redirect:/";
    }

    private String showRecipeForm(Model datamodel, Recipe recipe) {
        datamodel.addAttribute("formRecipe", recipe);
        return "recipeForm";
    }
}
