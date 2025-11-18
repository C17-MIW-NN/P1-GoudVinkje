package nl.miwnn.ch17.pixeldae.goudvinkje.dto;


/**
 * @author Simon van der Kooij
 */

public class GoudVinkjeUserDTO {
    private Long userID;
    private String username;
    private String password;
    private String confirmPassword;

    // getters & setters

    public Long getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
