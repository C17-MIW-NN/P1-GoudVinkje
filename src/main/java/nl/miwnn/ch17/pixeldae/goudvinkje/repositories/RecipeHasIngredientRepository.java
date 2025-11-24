package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 */

public interface RecipeHasIngredientRepository extends JpaRepository<RecipeHasIngredient, Long> {

    Optional<RecipeHasIngredient> findByRecipe(Optional<Recipe> optionalRecipe);
}
