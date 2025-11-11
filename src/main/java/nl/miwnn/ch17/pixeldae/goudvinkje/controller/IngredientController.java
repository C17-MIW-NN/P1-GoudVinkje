package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Simon van der Kooij
 *
 */

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    public final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/")
    public String showIngredients(Model datamodel) {
        datamodel.addAttribute("ingredients", ingredientRepository.findAll());

        return "showIngredientOverview";
    }

}
