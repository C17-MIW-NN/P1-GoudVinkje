package nl.miwnn.ch17.pixeldae.goudvinkje.service.mappers;


import nl.miwnn.ch17.pixeldae.goudvinkje.dto.GoudVinkjeUserDTO;
import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;

/**
 * @author Simon van der Kooij
 *
 */

public class GoudVinkjeUserMapper {

    public static GoudVinkjeUser fromDTO(GoudVinkjeUserDTO goudVinkjeUserDTO) {
        GoudVinkjeUser goudVinkjeUser = new GoudVinkjeUser();

        goudVinkjeUser.setGoudVinkjeUserID(goudVinkjeUserDTO.getUserID());
        goudVinkjeUser.setUsername(goudVinkjeUserDTO.getUsername());
        goudVinkjeUser.setPassword(goudVinkjeUserDTO.getPassword());

        return goudVinkjeUser;
    }

    public static GoudVinkjeUserDTO toDTO(GoudVinkjeUser goudVinkjeUser) {
        GoudVinkjeUserDTO goudVinkjeUserDTO = new GoudVinkjeUserDTO();

        goudVinkjeUserDTO.setUserID(goudVinkjeUser.getUserID());
        goudVinkjeUserDTO.setUsername(goudVinkjeUser.getUsername());

        return goudVinkjeUserDTO;
    }
}
