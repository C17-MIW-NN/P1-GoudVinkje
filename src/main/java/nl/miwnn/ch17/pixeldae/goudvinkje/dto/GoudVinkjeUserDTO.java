package nl.miwnn.ch17.pixeldae.goudvinkje.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * @author Simon van der Kooij
 */

@Getter @Setter
public class GoudVinkjeUserDTO {

    private Long userID;
    private String username;
    private String password;
    private String confirmPassword;
    private boolean AdminUser;

}

