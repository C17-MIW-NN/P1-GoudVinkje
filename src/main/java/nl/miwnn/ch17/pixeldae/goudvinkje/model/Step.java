package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;

/**
 * @author Annelies Hofman
 * Steps are the different actions, in a specific order, that need to be followed to complete a recipe while cooking
 */

@Entity
public class Step {

    @Id @GeneratedValue
    private Long stepId;

    private Integer sequenceNr;

    @Column(columnDefinition = "text")
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "recipe_recipeid")
    private Recipe recipe;

    // Constructors
    public Step() {
    }

    public Step(Integer sequenceNr) {
        this.sequenceNr = sequenceNr;
    }

    // Getters
    public Long getStepId() {
        return stepId;
    }
    public Integer getSequenceNr() {
        return sequenceNr;
    }
    public String getInstruction() {
        return instruction;
    }
    public Recipe getRecipe() {
        return recipe;
    }

    // Setters
    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }
    public void setSequenceNr(Integer sequenceNr) {
        this.sequenceNr = sequenceNr;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
