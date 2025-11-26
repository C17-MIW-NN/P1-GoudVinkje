package nl.miwnn.ch17.pixeldae.goudvinkje;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class GoudVinkjeApplicationTests {

    @Autowired
    private Validator validator;

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

        double result = ingredient.getCalories();

        assertEquals(100.0, result);
    }

    @Test
    @DisplayName("test if number field removes anything below 1")
    void enterTooLowNumbersInQuantityField() {
        // Arrange
        RecipeHasIngredient recipeHasIngredient = new RecipeHasIngredient();
        recipeHasIngredient.setQuantity(0);

        // Act
        var violations = validator.validate(recipeHasIngredient);

        // Assert
        assertTrue("Dit is te weinig", !violations.isEmpty());
    }

    @Test
    @DisplayName("test if calories are counted correctly for each recipe")
    void countCaloriesPerRecipe() {
        // Arrange
        Ingredient ingredient1 = new Ingredient();
        ingredient1.setCalories(500);
        ingredient1.setQuantityUnit(Ingredient.QuantityUnit.G);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setCalories(150);
        ingredient2.setQuantityUnit(Ingredient.QuantityUnit.G);

        List<RecipeHasIngredient> ingredientList = new ArrayList<>();
        ingredientList.add( new RecipeHasIngredient(ingredient1, 1));
        ingredientList.add(new RecipeHasIngredient(ingredient2, 1));

        Recipe recipe = new Recipe();
        recipe.setRecipeHasIngredients(ingredientList);
        recipe.setNrOfPortions(1);

        // Act
        double result = recipe.getCalories();

        // Assert
        assertEquals(6.5, result);
    }
}
