package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Simon van der Kooij
 */

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
