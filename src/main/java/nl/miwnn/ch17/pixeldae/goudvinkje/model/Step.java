package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * @author Annelies Hofman
 * Steps are the different actions, in a specific order, that need to be followed to complete a recipe while cooking
 */

@Entity
public class Step {

    protected static final int DEFAULT_SEQUENCE_NR = 0;
    protected static final String DEFAULT_INSTRUCTION = "";

    @Id @GeneratedValue
    private long stepId;

    private int sequenceNr;
    private String instruction;

    @ManyToOne
    private Recipe recipe;

    // Constructors
    public Step(Recipe recipe) {
        this.recipe = recipe;
        this.sequenceNr = DEFAULT_SEQUENCE_NR;
        this.instruction = DEFAULT_INSTRUCTION;
    }

    public Step() {
    }

    // Getters
    public long getStepId() {
        return stepId;
    }
    public int getSequenceNr() {
        return sequenceNr;
    }
    public String getInstruction() {
        return instruction;
    }
    public Recipe getRecipe() {
        return recipe;
    }

    // Setters
    public void setStepId(long stepId) {
        this.stepId = stepId;
    }
    public void setSequenceNr(int sequenceNr) {
        this.sequenceNr = sequenceNr;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
