package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@With
public class Recipe {

    protected static final int DEFAULT_NR_OF_PORTIONS = 2;
    protected static final boolean DEFAULT_PUBLICLY_VISIBLE = true;
    protected static final LocalDate DEFAULT_DATE_ADDED_NOW = LocalDate.now();
    protected static final int DEFAULT_SEQUENCE_NR = 1;


    @Id
    @GeneratedValue
    private Long recipeID;

    @Size(min = 5, max = 100, message = "Titel moet 5-100 karakters lang zijn")
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private GoudVinkjeUser author;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateAdded;

    @Column(columnDefinition = "text")
    private String description;

    private int nrOfPortions;

    private boolean publiclyVisible;

    private String imageURL;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeHasIngredient> recipeHasIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequenceNr ASC")
    private List<Step> steps = new ArrayList<>();

    // constructors
    public Recipe(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Recipe(GoudVinkjeUser author) {
        this.nrOfPortions = DEFAULT_NR_OF_PORTIONS;
        this.dateAdded = DEFAULT_DATE_ADDED_NOW;
        this.publiclyVisible = DEFAULT_PUBLICLY_VISIBLE;
        this.recipeHasIngredients.add(new RecipeHasIngredient(new Ingredient()));
        this.steps.add(new Step(DEFAULT_SEQUENCE_NR));
        this.author = author;
    }

    // methods
    public void addStep(Step step) {
        if (steps == null) steps = new ArrayList<>();
        step.setRecipe(this);
        this.steps.add(step);
    }

    public Integer countRecipeCalories() {
        int totalCalories = 0;
        for (RecipeHasIngredient recipeIngredient : recipeHasIngredients) {
            Integer correctedCalories = recipeIngredient.getIngredient().getCorrectedCalories();
            if (correctedCalories == null) { return null; }
            int calories = (recipeIngredient.getQuantity() * correctedCalories);
            totalCalories += calories;
        }

        totalCalories = getTotalCaloriesPerPortion(totalCalories);

        return totalCalories;
    }

    private int getTotalCaloriesPerPortion(int totalCalories) {
        return totalCalories / nrOfPortions;
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

    public Integer getCalories() {
        int totalCalories = 0;
        for (RecipeHasIngredient recipeIngredient : recipeHasIngredients) {
            Integer correctedCalories = recipeIngredient.getIngredient().getCorrectedCalories();
            if (correctedCalories == null) { return null; }
            int calories = (recipeIngredient.getQuantity() * correctedCalories);
            totalCalories += calories;
        }
        totalCalories = getTotalCaloriesPerPortion(totalCalories);

        return totalCalories;

    }
}
