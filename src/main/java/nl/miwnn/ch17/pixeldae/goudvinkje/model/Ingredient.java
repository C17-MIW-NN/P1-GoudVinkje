package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

/**
 * @author Simon van der Kooij
 * this model handles all the ingredients
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {

    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(unique = true)
    private String description;

    private int calories;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RecipeHasIngredient> recipeHasIngredient;

    @Enumerated(EnumType.STRING)
    private QuantityUnit quantityUnit;
    public enum QuantityUnit {
        G ("g", 100.0),
        KG ("kg", 0.1),
        ML ("ml", 100.0),
        L ("l", 0.1),
        TL ("tl", 33.3),
        EL ("el", 7.3),
        ST ("stk", 1.0);

        private final String displayName;
        private final double caloryFactor;

        QuantityUnit(String displayName, double caloryFactor) {
            this.displayName = displayName;
            this.caloryFactor = caloryFactor;
        }

        public String getDisplayName() {
            return displayName;
        }

        public double getCaloryFactor() {
            return caloryFactor;
        }
    }

    public String getUnitDisplayName() {
        return quantityUnit.getDisplayName();
    }

    public int getCorrectedCalories() {
        return (int) (calories / quantityUnit.getCaloryFactor());
    }

    public int countUsesInRecipes() {
        return recipeHasIngredient.size();
    }
}
