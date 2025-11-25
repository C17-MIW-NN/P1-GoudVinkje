package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Simon van der Kooij & Annelies Hofman
 * this model handles all the ingredients
 */

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "quantityUnit"})})
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredientId;

    @NotNull(message = "Omschrijving moet 5-100 karakters lang zijn")
    @Size(min = 2, max = 100, message = "Omschrijving moet 5-100 karakters lang zijn")
    private String description;

    private Integer calories;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RecipeHasIngredient> recipeHasIngredient;

    @Enumerated(EnumType.STRING)
    private QuantityUnit quantityUnit;
    @Getter
    public enum QuantityUnit {
        G ("g", 100.0),
        KG ("kg", 0.1),
        ML ("ml", 100.0),
        L ("l", 0.1),
        TL ("tl", 30.0),
        EL ("el", 10.0),
        ST ("stuks", 1.0),
        SNUF ("snufje", 1000.0);

        private final String displayName;
        private final double caloryFactor;

        QuantityUnit(String displayName, double caloryFactor) {
            this.displayName = displayName;
            this.caloryFactor = caloryFactor;
        }
    }

    public Integer getCorrectedCalories() {
        if (calories == null) {
            return null;
        }
        return (int) (calories / quantityUnit.getCaloryFactor());
    }

    public int countUsesInRecipes() {
        return recipeHasIngredient.size();
    }

}
