package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.MediaType;

/**
 * @author Annelies Hofman
 * images can be attached to recipe's to give a visual representation of the recipe
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue
    private Long imageId;

    @Column(unique = true)
    private String fileName;
    private String contentType;

    @Lob
    private byte[] data;

    public MediaType getContentType() {
        return MediaType.parseMediaType(contentType);
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType.toString();
    }
}
