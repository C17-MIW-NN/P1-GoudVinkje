package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * @author Annelies Hofman & Simon van der Kooij
 * Steps are the different actions, in a specific order, that need to be followed to complete a recipe while cooking
 */

@Entity
@Getter @Setter @NoArgsConstructor
public class Step {

    @Id @GeneratedValue
    private Long stepId;

    private Integer sequenceNr;

    @NotBlank(message = "De omschrijving mag niet weggelaten worden.")
    @Column(columnDefinition = "text")
    private String instruction;

    @ManyToOne
    @JoinColumn(name = "recipe_recipeid")
    private Recipe recipe;

    // Constructors
    public Step(Integer sequenceNr) {
        this.sequenceNr = sequenceNr;
    }
}
