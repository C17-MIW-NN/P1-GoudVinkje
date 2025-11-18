package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon van der Kooij
 * This model handles every recipe
 */

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @With
public class Recipe {

    @Id
    @GeneratedValue
    private Long recipeID;

    private String name;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private GoudVinkjeUser author;

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

    public Recipe newCopiedRecipe(GoudVinkjeUser newAuthor) {
        Recipe copiedRecipe = this.withAuthor(newAuthor).withRecipeID(null).withDateAdded(LocalDate.now());

        List<RecipeHasIngredient> copiedRHIlist = this.recipeHasIngredients.stream().map(originalRHI -> {
            RecipeHasIngredient copiedRHI = new RecipeHasIngredient();
            copiedRHI.setIngredient(originalRHI.getIngredient());
            copiedRHI.setQuantity(originalRHI.getQuantity());
            copiedRHI.setRecipe(copiedRecipe);
            return copiedRHI;
        }).toList();

        List<Step> copiedSteplist = this.steps.stream().map(originalStep -> {
            Step copiedStep = new Step();
            copiedStep.setInstruction(originalStep.getInstruction());
            copiedStep.setRecipe(copiedRecipe);
            return copiedStep;
        }).toList();

        copiedRecipe.setRecipeHasIngredients(copiedRHIlist);
        copiedRecipe.setSteps(copiedSteplist);

        return copiedRecipe;
    }

    public double getCalories() {
        return (Math.round((countRecipeCalories()/ nrOfPortions) * 100.0) / 100.0) ;
    }

}
