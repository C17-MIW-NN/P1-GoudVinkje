package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Annelies Hofman
 * A tag is a label that can be applied to a recipe, to categorize recipes into groups such as: 'breakfast', 'Italian'
 */

@Entity
public class Tag {

    @Id @GeneratedValue
    private Long tagID;

    @Column(unique = true)
    private String tagName;

    // Constructors
    public Tag() {
    }

    // Getters
    public Long getTagID() {
        return tagID;
    }
    public String getTagName() {
        return tagName;
    }

    // Setters
    public void setTagID(Long tagID) {
        this.tagID = tagID;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
