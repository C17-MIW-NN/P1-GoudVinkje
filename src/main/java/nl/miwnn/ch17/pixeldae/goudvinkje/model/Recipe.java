package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon van der Kooij
 * This model handles every recipe
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue
    private Long recipeID;

    private String name;

    private String author;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateAdded;

    @Column(columnDefinition = "text")
    private String description;

    private int nrOfPortions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeHasIngredient> recipeHasIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequenceNr ASC")
    private List<Step> steps = new ArrayList<>();

    // constructors
    public Recipe(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void addStep(Step step) {
        if (steps == null) steps = new ArrayList<>();
        step.setRecipe(this);
        this.steps.add(step);
    }

    // methods
    public double countRecipeCalories() {
        double totalCalories = 0;
        for (RecipeHasIngredient recipeIngredient : recipeHasIngredients) {
            double calories = (recipeIngredient.getQuantity() * recipeIngredient.getIngredient().getCorrectedCalories());
            totalCalories += calories;
        }
        return totalCalories;
    }

    public double getCalories() {
        return (Math.round((countRecipeCalories()/ nrOfPortions) * 100.0) / 100.0) ;
    }

}
