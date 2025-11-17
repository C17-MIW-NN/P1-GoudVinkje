package nl.miwnn.ch17.pixeldae.goudvinkje;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
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


}
