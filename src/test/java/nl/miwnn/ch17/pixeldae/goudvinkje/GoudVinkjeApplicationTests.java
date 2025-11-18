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
        Recipe recipe = new Recipe();

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setCalories(50);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setCalories(15);

        List<RecipeHasIngredient> ingredientList = new ArrayList<>();
        ingredientList.add( new RecipeHasIngredient(ingredient1, 1));
        ingredientList.add(new RecipeHasIngredient(ingredient2, 2));

        recipe.setRecipeHasIngredients(ingredientList);

        // Act
        double result = recipe.countRecipeCalories();

        // Assert
        assertEquals(65.0, result);
    }
}
