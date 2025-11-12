package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 * handles everything concerning ingredients.
 */

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    public final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping({"/", "/overzicht"})
    public String showIngredients(Model datamodel) {
        datamodel.addAttribute("ingredients", ingredientRepository.findAll());

        return "showIngredientOverview";
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
    public String saveIngredientForm(@ModelAttribute("ingredient") Ingredient ingredient,
                                 BindingResult result, Model datamodel, @RequestParam String action) {
        if (action.equals("save")) {
            if (!result.hasErrors()) {
                ingredientRepository.save(ingredient);
            }
        }
        return "redirect:/ingredient/overzicht";
    }

    private String showIngredientForm(Model datamodel, Ingredient ingredient) {
        datamodel.addAttribute("ingredient", ingredient);
        return "ingredientForm";
    }

}
