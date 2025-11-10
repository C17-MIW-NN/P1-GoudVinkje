package nl.miwnn.ch17.pixeldae.goudvinkje.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * @author Simon van der Kooij
 * All data and logic around users of this app
 */

@Entity
public class GoudVinkjeUser {

    @Id
    @GeneratedValue
    private Long goudVinkjeUserID;

    private String goudVinkjeUserName;

    public GoudVinkjeUser() {
    }

}
