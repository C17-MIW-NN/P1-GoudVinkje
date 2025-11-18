package nl.miwnn.ch17.pixeldae.goudvinkje;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GoudVinkjeApplicationTests {

    @Test
    void contextLoads() {
    }

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
}
