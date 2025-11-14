package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Simon van der Kooij
 * This model handles every recipe
 */

@Entity
public class Recipe {

    @Id
    @GeneratedValue
    private Long recipeID;

    private String name;

    private String author;

    private LocalDate dateAdded;

    private String description;

    private int nrOfPortions;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeHasIngredient> recipeHasIngredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sequenceNr ASC")
    private List<Step> steps = new ArrayList<>();

    // methods
    public void addStep(Step step) {
        if (steps == null) steps = new ArrayList<>();
        step.setRecipe(this);
        this.steps.add(step);
    }

    // getters
    public Long getRecipeID() {
        return recipeID;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public String getDescription() {
        return description;
    }

    public int getNrOfPortions() {
        return nrOfPortions;
    }

    public List<RecipeHasIngredient> getRecipeHasIngredients() {
        return recipeHasIngredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    // setters
    public void setRecipeHasIngredients(List<RecipeHasIngredient> recipeHasIngredients) {
        this.recipeHasIngredients = recipeHasIngredients;
    }

    public void setRecipeID(Long recipeID) {
        this.recipeID = recipeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDateAdded(LocalDate dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNrOfPortions(int nrOfPortions) {
        this.nrOfPortions = nrOfPortions;
    }

}
