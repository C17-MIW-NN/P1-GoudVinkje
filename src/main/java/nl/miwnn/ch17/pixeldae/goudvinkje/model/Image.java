package nl.miwnn.ch17.pixeldae.goudvinkje.model;

import jakarta.persistence.*;
import org.springframework.http.MediaType;

/**
 * @author Annelies Hofman
 * images can be attached to recipe's to give a visual representation of the recipe
 */

@Entity
public class Image {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String fileName;
    private String contentType;

    @Lob
    private byte[] data;

    // Getters & Setters
    public MediaType getContentType() {
        return MediaType.parseMediaType(contentType);
    }

    public void setContentType(MediaType contentType) {
        this.contentType = contentType.toString();
    }

    // standard getters & setters

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
