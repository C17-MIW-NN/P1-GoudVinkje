package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Simon van der Kooij
 *
 */

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
