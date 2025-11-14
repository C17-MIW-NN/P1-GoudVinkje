package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;

/**
 * @author Annelies Hofman
 * Steps are the different actions, in a specific order, that need to be followed to complete a recipe while cooking
 */

@Entity
//code om combinatie van recept en stapID uniek te maken, zodat sequenceNr automatisch afgehandeld kan gaan worden
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"sequence_nr", "recipe_recipeid"})})
public class Step implements Comparable<Step> {

    protected static final int DEFAULT_SEQUENCE_NR = 0;
    protected static final String DEFAULT_INSTRUCTION = "";

    @Id @GeneratedValue
    private long stepId;

//    @Column(name = "sequence_nr")
    private int sequenceNr;

    private String instruction;

    @ManyToOne
//    @Column(name = "recipe_recipeid")
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

    @Override
    public String toString() {
        return String.format("stepId: %s, sequenceNr: %s, instruction: %s, recipe: %s", this.stepId, this.sequenceNr, this.instruction, this.recipe);
    }

    @Override
    public int compareTo(Step otherStep) {
        return this.sequenceNr - otherStep.sequenceNr;
    }
}
