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

    protected static final int AMOUNT_OF_GRAMS_IN_100_GRAM = 100;
    protected static final int AMOUNT_OF_TBSP_IN_100_GRAM = 7;
    protected static final int AMOUNT_OF_TSP_IN_100_GRAM = 33;

    @Id
    @GeneratedValue
    private Long ingredientId;

    @Column(unique = true)
    private String description;

    private double calories;

    private String quantityUnit;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RecipeHasIngredient> recipeHasIngredient;

    // methods
    public int checkCaloryUnitFactor() {
        int factor = 1;
        if (this.quantityUnit.equals("g") || this.quantityUnit.equals("ml")) {
            factor = AMOUNT_OF_GRAMS_IN_100_GRAM;
        } else if (this.quantityUnit.equals("el")) {
            factor = AMOUNT_OF_TBSP_IN_100_GRAM;
        } else if (this.quantityUnit.equals("tl")) {
            factor = AMOUNT_OF_TSP_IN_100_GRAM;
        }
        return factor;
    }

    public double getCorrectedCalories() {
        return Math.round((calories / checkCaloryUnitFactor()) * 100.0) / 100.0;
    }

}
