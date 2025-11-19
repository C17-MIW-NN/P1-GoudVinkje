package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Simon van der Kooij
 *
 */

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findAllByAuthor(GoudVinkjeUser loggedInUser);

    List<Recipe> findAllByAuthorNot(GoudVinkjeUser loggedInUser);
}
