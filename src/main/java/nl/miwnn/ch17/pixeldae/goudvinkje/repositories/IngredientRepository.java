package nl.miwnn.ch17.pixeldae.goudvinkje.repositories;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Optional<Ingredient> findByDescription(String ingredientDescription);

    Optional<Ingredient> findByQuantityUnitAndDescription(Ingredient.QuantityUnit quantityUnit, String description);
}
