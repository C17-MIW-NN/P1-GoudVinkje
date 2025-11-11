package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Step;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.IngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeHasIngredientRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.RecipeRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.StepRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Simon van der Kooij
 * fills the application with useful data intended for development.
 */

@Controller
public class InitController {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeHasIngredientRepository recipeHasIngredientRepository;
    private final StepRepository stepRepository;

    public InitController(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeHasIngredientRepository recipeHasIngredientRepository, StepRepository stepRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.stepRepository = stepRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        System.err.println(recipeRepository.count() + " - " + ingredientRepository.count());
        if (recipeRepository.count() == 0 && ingredientRepository.count() == 0) {

            // --- Recept ---
            Recipe recipe = makeRecipe(
                    "Veganistische linzencurry",
                    "Chef Joas",
                    "Een heerlijke, romige curry met rode linzen en kokosmelk — 100% plantaardig.",
                    4
            );

            // --- Ingrediënten aanmaken ---
            Ingredient linzen = makeIngredient("Rode linzen", 350, "g");
            Ingredient kokosmelk = makeIngredient("Kokosmelk", 190, "ml");
            Ingredient ui = makeIngredient("Ui", 40, "stuks");
            Ingredient knoflook = makeIngredient("Knoflook", 130, "teentjes");
            Ingredient gember = makeIngredient("Verse gember", 80, "el");
            Ingredient currypoeder = makeIngredient("Currypoeder", 320, "el");
            Ingredient tomatenblokjes = makeIngredient("Tomatenblokjes", 25, "g");
            Ingredient spinazie = makeIngredient("Verse spinazie", 30, "g");
            Ingredient olie = makeIngredient("Plantaardige olie", 900, "el");
            Ingredient zout = makeIngredient("Zout", 0, "tl");

            // --- Ingrediënten toevoegen via Map ---
            addIngredient(recipe, Map.of(
                    linzen, 200,
                    kokosmelk, 400,
                    ui, 1,
                    knoflook, 2,
                    gember, 1,
                    currypoeder, 2,
                    tomatenblokjes, 400,
                    spinazie, 100,
                    olie, 2,
                    zout, 1
            ));

            // --- Stappen ---
            recipe.addStep(makeStep(1, "Verhit de olie in een grote pan en fruit de ui, knoflook en gember tot ze geuren."));
            recipe.addStep(makeStep(2, "Voeg het currypoeder toe en bak kort mee."));
            recipe.addStep(makeStep(3, "Roer de rode linzen, tomatenblokjes en kokosmelk erdoor en breng aan de kook."));
            recipe.addStep(makeStep(4, "Laat 20 minuten zachtjes pruttelen tot de linzen gaar zijn."));
            recipe.addStep(makeStep(5, "Roer de spinazie erdoor en breng op smaak met zout."));
            recipe.addStep(makeStep(6, "Serveer warm met rijst of naanbrood."));

            recipeRepository.save(recipe);
        }
    }

    public Recipe makeRecipe(String name, String author, String description, int nrOfPortions) {
        Recipe recipe = new Recipe();

        recipe.setName(name);
        recipe.setAuthor(author);
        recipe.setDateAdded(LocalDate.now());
        recipe.setDescription(description);
        recipe.setNrOfPortions(nrOfPortions);

        recipeRepository.save(recipe);

        return recipe;
    }

    public Ingredient makeIngredient(String name, int caloriesPer100, String quantityUnit) {

        Ingredient ingredient = new Ingredient();

        ingredient.setName(name);
        ingredient.setCaloriesPer100(caloriesPer100);
        ingredient.setQuantityUnit(quantityUnit);

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    public Step makeStep(int sequenceNr, String instruction) {

        Step step = new Step();

        step.setSequenceNr(sequenceNr);
        step.setInstruction(instruction);

        stepRepository.save(step);

        return step;
    }

    public void addIngredient(Recipe recipe, Map<Ingredient, Integer> ingredientsQuantities) {

        for (Map.Entry<Ingredient, Integer> ingredientQuantity : ingredientsQuantities.entrySet()) {
            RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();
            recipeHasIngredient.setRecipe(recipe);
            recipeHasIngredient.setIngredient(ingredientQuantity.getKey());
            recipeHasIngredient.setQuantity(ingredientQuantity.getValue());

            recipeHasIngredientRepository.save(recipeHasIngredient);
        }

    }





}
