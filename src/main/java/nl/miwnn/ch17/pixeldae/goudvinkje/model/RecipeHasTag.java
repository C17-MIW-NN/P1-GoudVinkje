package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Annelies Hofman
 * Creates a unique identifier for the many-to-many relationship between Recipe and Tag
 */

public class RecipeHasTag {

    @Id
    private Long recipeId;
    @Id
    private Long tagId;

    // Recipe+Tag ID class
    public static class RecipeHasTagId implements Serializable {
        private Long recipeId;
        private Long tagId;
    }

    // Constructor
    public RecipeHasTag() {
    }

    // Methods
        // to create Recipe+Tag ID
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof RecipeHasTag.RecipeHasTagId that)) {
            return false;
        }
        return Objects.equals(recipeId, that.recipeId) &&
                Objects.equals(tagId, that.tagId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(recipeId, tagId);
    }

}
