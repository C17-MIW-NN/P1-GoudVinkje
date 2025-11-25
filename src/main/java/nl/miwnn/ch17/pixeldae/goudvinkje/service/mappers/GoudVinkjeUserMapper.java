package nl.miwnn.ch17.pixeldae.goudvinkje.service.mappers;

import nl.miwnn.ch17.pixeldae.goudvinkje.dto.GoudVinkjeUserDTO;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;

/**
 * @author Simon van der Kooij
 * maps the persistant user information with the DTO user information
 */

public class GoudVinkjeUserMapper {

    public static GoudVinkjeUser fromDTO(GoudVinkjeUserDTO goudVinkjeUserDTO) {
        GoudVinkjeUser goudVinkjeUser = new GoudVinkjeUser();

        goudVinkjeUser.setUserID(goudVinkjeUserDTO.getUserID());
        goudVinkjeUser.setUsername(goudVinkjeUserDTO.getUsername());
        goudVinkjeUser.setPassword(goudVinkjeUserDTO.getPassword());
        goudVinkjeUser.setRole(goudVinkjeUserDTO.isAdminUser() ? "ROLE_ADMIN" : "ROLE_USER");

        return goudVinkjeUser;
    }

    public static GoudVinkjeUserDTO toDTO(GoudVinkjeUser goudVinkjeUser) {
        GoudVinkjeUserDTO goudVinkjeUserDTO = new GoudVinkjeUserDTO();

        goudVinkjeUserDTO.setUserID(goudVinkjeUser.getUserID());
        goudVinkjeUserDTO.setUsername(goudVinkjeUser.getUsername());
        goudVinkjeUserDTO.setAdminUser(goudVinkjeUser.getRole().equals("ROLE_ADMIN"));

        return goudVinkjeUserDTO;
    }
}
