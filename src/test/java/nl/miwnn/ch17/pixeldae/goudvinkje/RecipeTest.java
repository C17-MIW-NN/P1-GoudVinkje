package nl.miwnn.ch17.pixeldae.goudvinkje;

import jakarta.validation.Validator;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

/**
 * @author Annelies Hofman
 * test functions related to recipes
 */

@SpringBootTest
public class RecipeTest {

    @Autowired
    private Validator validator;

    @Test
    void contextLoads() {
    }

    //recipe name length tests
    @Test
    @DisplayName("test if name length causes validation errors")
    void setSlightlyTooShortName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Soep");
        recipe.setNrOfPortions(4);

        // Act
        var violations = validator.validate(recipe);
        var nameViolation = violations.iterator().next();

        // Assert
        assertTrue("Titel moet 5-100 karakters lang zijn", !violations.isEmpty());
        assertEquals("name", nameViolation.getPropertyPath().toString());
        assertEquals("Titel moet 5-100 karakters lang zijn", nameViolation.getMessage());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setNearlyTooShortName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Pizza");
        recipe.setNrOfPortions(4);

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("any error message", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setExactMaxLengthName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, " +
                "aardappelen en kaas die iedereen zal waarderen!\n");//100 characters
        recipe.setNrOfPortions(4);

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("any error message", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setNearlyTooLongName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, " +
                "aardappelen en kaas die iedereen zal waarderen\n");//99 characters
        recipe.setNrOfPortions(4);

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertTrue("any error message", violations.isEmpty());
    }

    @Test
    @DisplayName("test if name length causes validation errors")
    void setSlightlyTooLongName() {
        // Arrange
        Recipe recipe = new Recipe();
        recipe.setName("Recept voor een heerlijke ovenschotel met groenten, " +
                "aardappelen en kaas die iedereen zal waarderen!!\n");//101 characters
        recipe.setNrOfPortions(4);

        // Act
        var violations = validator.validate(recipe);

        // Assert
        assertFalse("any error message", violations.isEmpty());

        var nameViolation = violations.iterator().next();
        assertEquals("name", nameViolation.getPropertyPath().toString());
        assertEquals("Titel moet 5-100 karakters lang zijn", nameViolation.getMessage());
    }
}
