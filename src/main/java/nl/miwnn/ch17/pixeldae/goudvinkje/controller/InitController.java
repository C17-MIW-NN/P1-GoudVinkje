package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.*;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.*;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.GoudVinkjeUserService;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.ImageService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * @author Simon van der Kooij
 * fills the application with useful data intended for development.
 */

@Controller
public class InitController {

    private static final String STANDARD_PASSWORD = "2025";
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeHasIngredientRepository recipeHasIngredientRepository;
    private final StepRepository stepRepository;
    private final GoudVinkjeUserService goudVinkjeUserService;
    private final ImageService imageService;

    public InitController(RecipeRepository recipeRepository,
                          IngredientRepository ingredientRepository,
                          RecipeHasIngredientRepository recipeHasIngredientRepository,
                          StepRepository stepRepository,
                          GoudVinkjeUserService goudVinkjeUserService,
                          ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeHasIngredientRepository = recipeHasIngredientRepository;
        this.stepRepository = stepRepository;
        this.goudVinkjeUserService = goudVinkjeUserService;
        this.imageService = imageService;
    }

    @EventListener
    private void seed(ContextRefreshedEvent ignoredEvent) {
        if (recipeRepository.count() == 0 && ingredientRepository.count() == 0) {
            initializeDB();
        }
    }

    private void initializeDB() {

        GoudVinkjeUser chadGPete = makeUser("Chad G Pete");
        GoudVinkjeUser simon = makeUser("Simon");
        GoudVinkjeUser annelies = makeUser("Annelies");
        GoudVinkjeUser admin = makeUser("admin");

        // --- Recept 1---
        Recipe recipe = makeRecipe(
                "Veganistische linzencurry",
                chadGPete,
                "Een heerlijke, romige curry met rode linzen en kokosmelk — 100% plantaardig.",
                4,
                "/image/vegan-rode-linzen-curry.jpg"
        );

        //  Ingrediënten aanmaken
        Ingredient linzen = makeIngredient("Rode linzen", 350, "g");
        Ingredient kokosmelk = makeIngredient("Kokosmelk", 190, "ml");
        Ingredient ui = makeIngredient("Ui", 40, "st");
        Ingredient knoflook = makeIngredient("Knoflook (teen)", 130, "stk");
        Ingredient gember = makeIngredient("Verse gember", 80, "el");
        Ingredient currypoeder = makeIngredient("Currypoeder", 320, "el");
        Ingredient tomatenblokjes = makeIngredient("Tomatenblokjes", 25, "g");
        Ingredient spinazie = makeIngredient("Verse spinazie", 30, "g");
        Ingredient olie = makeIngredient("Plantaardige olie", 900, "el");
        Ingredient zout = makeIngredient("Zout", 0, "tl");

        // Ingrediënten toevoegen via Map
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

        // --- Tweede recept --- (Vegan Tofu Stir-Fry)
        Recipe tofuStirFry = makeRecipe(
                "Vegan Tofu Stir-Fry",
                chadGPete,
                "Een snelle, kleurrijke roerbak met tofu, groenten en een sojasaus-dressing.",
                2,
                "/image/stir-fry-noodles.jpg"
        );

        Ingredient tofu = makeIngredient("Tofu", 150, "g");
        Ingredient broccoli = makeIngredient("Broccoli", 35, "g");
        Ingredient paprika = makeIngredient("Paprika", 30, "g");
        Ingredient wortel = makeIngredient("Wortel", 41, "g");
        Ingredient sojasaus = makeIngredient("Sojasaus", 53, "el");
        Ingredient sesamolie = makeIngredient("Sesamolie", 900, "el");
        Ingredient peper = makeIngredient("Zwarte peper", 0, "tl");

        addIngredient(tofuStirFry, Map.of(
                tofu, 200,
                broccoli, 150,
                paprika, 100,
                wortel, 100,
                ui, 1,
                knoflook, 2,
                gember, 1,
                sojasaus, 2,
                sesamolie, 1,
                peper, 1
        ));

        tofuStirFry.addStep(makeStep(1,
                "Snijd tofu in blokjes en marineer kort in sojasaus."));
        tofuStirFry.addStep(makeStep(2,
                "Verhit sesamolie in een wok en bak tofu goudbruin."));
        tofuStirFry.addStep(makeStep(3,
                "Voeg ui, knoflook en gember toe en roerbak 2 minuten."));
        tofuStirFry.addStep(makeStep(4,
                "Voeg groenten toe (broccoli, paprika, wortel) en bak 5-7 minuten tot ze gaar zijn."));
        tofuStirFry.addStep(makeStep(5,
                "Voeg de rest van de sojasaus toe en breng op smaak met peper."));
        tofuStirFry.addStep(makeStep(6,
                "Serveer direct, eventueel met rijst of noedels."));

        recipeRepository.save(tofuStirFry);

        // --- Derde recept --- (Extreem Lang Recept)
        Recipe langRecept = makeRecipe(
                "Romige Plant-based Pasta Met Groenten, Kruiden En Een Langzaam Gekookte Saus",
                annelies,
                "Een uitgebreide, plantaardige pastamaaltijd met een romige saus op basis van "
                        + "geweekte noten, kruidige groenten en zorgvuldig opgebouwde smaken. De "
                        + "omschrijving is bewust lang zodat de layout getest kan worden.",
                4,
                "/image/pasta-in-plantaardige-romige-saus.jpg"
        );

        Ingredient plantaardigeMelk = makeIngredient(
                "Plantaardige melk, bijvoorbeeld havermelk", 300, "ml");

        Ingredient cashewPasta = makeIngredient(
                "Gebrande cashewnoten, geweekt en gepureerd", 120, "g");

        Ingredient groentebouillon = makeIngredient(
                "Groentebouillon van goede kwaliteit voor extra diepgang", 200, "ml");

        Ingredient citroensap = makeIngredient(
                "Vers citroensap voor een frisse, lichte zurige toets", 2, "el");

        Ingredient edelgist = makeIngredient(
                "Edelgist vlokken (voedingsgist) voor een subtiele kaasachtige smaak", 3, "el");

        Ingredient cherryTomaten = makeIngredient("Cherry tomaten", 200, "g");
        Ingredient pasta = makeIngredient("Volkoren pasta", 300, "g");
        Ingredient olijfolie = makeIngredient("Olijfolie extra vierge", 2, "el");

        addIngredient(langRecept, Map.of(
                plantaardigeMelk, 300,
                cashewPasta, 80,
                groentebouillon, 800,
                citroensap, 10,
                ui, 1,
                knoflook, 3,
                edelgist, 50,
                cherryTomaten, 200,
                pasta, 300,
                olijfolie, 2
        ));

        langRecept.addStep(makeStep(1,
                "Kook de pasta volgens de aanwijzingen op de verpakking in ruim gezouten water. "
                        + "Roer af en toe, zodat de pasta niet gaat plakken, en zorg dat hij beetgaar wordt "
                        + "om later goed te kunnen mengen met de romige saus."));

        langRecept.addStep(makeStep(2,
                "Verhit ondertussen een ruime hoeveelheid olijfolie in een grote pan op middelhoog vuur. "
                        + "Bak de ui en knoflook voorzichtig aan tot ze glazig en aromatisch zijn. Laat ze niet "
                        + "te donker worden, zodat de smaak mild blijft en de uiteindelijke saus niet bitter wordt."));

        langRecept.addStep(makeStep(3,
                "Voeg de rode paprika en cherry tomaten toe en bak ze gedurende enkele minuten totdat "
                        + "ze beginnen te karamelliseren. Dit draagt bij aan een diepere, lichtzoete smaak in de saus. "
                        + "Roer regelmatig om aanbranden te voorkomen."));

        langRecept.addStep(makeStep(4,
                "Schenk de groentebouillon en plantaardige melk in de pan en breng het geheel langzaam aan "
                        + "de kook. Voeg vervolgens de gepureerde geweekte cashewnoten toe om de saus romig en vol "
                        + "van textuur te maken. Laat dit geheel op laag vuur zachtjes pruttelen, waardoor de smaken "
                        + "zich goed kunnen ontwikkelen en versmelten."));

        langRecept.addStep(makeStep(5,
                "Voeg wanneer de saus heeft ingedikt de verse spinazie, edelgist, oregano en basilicum toe. "
                        + "Meng alles rustig door elkaar totdat de spinazie is geslonken. Breng op smaak met zout, "
                        + "peper en eventueel een scheutje citroensap voor frisheid. Proef en pas naar wens aan."));

        langRecept.addStep(makeStep(6,
                "Giet de pasta af, voeg deze toe aan de saus en schep zorgvuldig om zodat alle pasta volledig "
                        + "bedekt is met de romige mengsel. Laat het geheel nog één minuut zachtjes verwarmen voordat "
                        + "je serveert. Garneer met extra edelgist of een handje verse kruiden indien gewenst."));

        recipeRepository.save(langRecept);


        // --- Vierde recept --- (Kort recept, pompoensoep)
        Recipe kortRecept = makeRecipe(
                "Pompoensoep",
                annelies,
                "Altijd een success.",
                4,
                "/image/pompoen-soep.jpg"
        );

        Ingredient pompoen = makeIngredient("Pompoen", 180, "st");
        Ingredient water = makeIngredient("Water", 0, "ml");

        addIngredient(kortRecept, Map.of(
                pompoen, 1,
                water, 1000
        ));

        kortRecept.addStep(makeStep(1, "Snijd de pompoen in stukken."));
        kortRecept.addStep(makeStep(2, "Kook de pompoen met het water."));
        kortRecept.addStep(makeStep(3, "Pureer na 20 min."));

        recipeRepository.save(kortRecept);
    }

    private Recipe makeRecipe(String name, GoudVinkjeUser author, String description, int nrOfPortions, String filename) {
        Recipe recipe = new Recipe();

        recipe.setName(name);
        recipe.setAuthor(author);
        recipe.setDateAdded(LocalDate.now());
        recipe.setDescription(description);
        recipe.setNrOfPortions(nrOfPortions);

        try {
            saveImage(filename);
            recipe.setImageURL(filename);
        } catch (IOException error) {
            throw new RuntimeException(error);
        }

        recipeRepository.save(recipe);

        return recipe;
    }

    private Ingredient makeIngredient(String description, int calories, String quantityUnit) {

        Ingredient ingredient = new Ingredient();

        ingredient.setDescription(description);
        ingredient.setCalories(calories);
        ingredient.setQuantityUnit(quantityUnit);

        ingredientRepository.save(ingredient);

        return ingredient;
    }

    private Step makeStep(int sequenceNr, String instruction) {

        Step step = new Step();

        step.setSequenceNr(sequenceNr);
        step.setInstruction(instruction);

        stepRepository.save(step);

        return step;
    }

    private void addIngredient(Recipe recipe, Map<Ingredient, Integer> ingredientsQuantities) {

        for (Map.Entry<Ingredient, Integer> ingredientQuantity : ingredientsQuantities.entrySet()) {
            RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();
            recipeHasIngredient.setRecipe(recipe);
            recipeHasIngredient.setIngredient(ingredientQuantity.getKey());
            recipeHasIngredient.setQuantity(ingredientQuantity.getValue());

            recipeHasIngredientRepository.save(recipeHasIngredient);
        }

    }

    private GoudVinkjeUser makeUser(String username) {
        GoudVinkjeUser goudVinkjeUser = new GoudVinkjeUser();

        goudVinkjeUser.setUsername(username);
        goudVinkjeUser.setPassword(STANDARD_PASSWORD);
        if (username.equals("admin")) {
            goudVinkjeUser.setRole("ROLE_ADMIN");
        } else {
            goudVinkjeUser.setRole("ROLE_USER");
        }

        goudVinkjeUserService.saveUser(goudVinkjeUser);

        return goudVinkjeUser;
    }

    private void saveImage(String filename) throws IOException {
        ClassPathResource imageResource = new ClassPathResource("sampledata" + filename);
        imageService.saveImage(imageResource);
    }
}
