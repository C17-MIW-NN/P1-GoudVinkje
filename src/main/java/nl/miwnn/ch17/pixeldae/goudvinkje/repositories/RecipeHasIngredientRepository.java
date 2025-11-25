package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.RecipeHasIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeHasIngredientRepository extends JpaRepository<RecipeHasIngredient, Long> {

}
