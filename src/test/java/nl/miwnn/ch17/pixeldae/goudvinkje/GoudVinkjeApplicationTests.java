package nl.miwnn.ch17.pixeldae.goudvinkje;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class GoudVinkjeApplicationTests {

    @Autowired
    private Validator validator;
//    private final Validator validator;
//
//    GoudVinkjeApplicationTests(Validator validator) {
//        this.validator = validator;
//    }
//    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void contextLoads() {
    }


    // calory related tests
    @Test
    void countCaloriesPerIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setCalories(100);
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();
        recipeHasIngredient.setQuantity(1);
        recipeHasIngredient.setIngredient(ingredient);

        double result = recipeHasIngredient.countCaloriesPerIngredient();

        assertEquals(100.0, result);
    }

    @Test
    @DisplayName("test if calories are counted correctly for each recipe")
    void countCaloriesPerRecipe() {
        // Arrange
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setCalories(500);
        ingredient1.setQuantityUnit("g");
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setCalories(150);
        ingredient2.setQuantityUnit("g");

        List<RecipeHasIngredient> ingredientList = new ArrayList<>();
        ingredientList.add( new RecipeHasIngredient(ingredient1, 1));
        ingredientList.add(new RecipeHasIngredient(ingredient2, 1));

        Recipe recipe = new Recipe();
        recipe.setRecipeHasIngredients(ingredientList);
        recipe.setNrOfPortions(1);

        // Act
        double result = recipe.countRecipeCalories();

        // Assert
        assertEquals(6.5, result);
    }

    //recipe name length tests
    @Test
    @DisplayName("test if name length causes validation errors")
    void setSlightlyTooShortName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Soep");

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", !violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setNearlyTooShortName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setExactMaxLengthName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, aardappelen en kaas die iedereen zal waarderen!\n");//100 characters

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setNearlyTooLongName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, aardappelen en kaas die iedereen zal waarderen\n");//99 characters

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setSlightlyTooLongName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, aardappelen en kaas die iedereen zal waarderen!!\n");//101 characters

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", !violations.isEmpty());
    }
}
