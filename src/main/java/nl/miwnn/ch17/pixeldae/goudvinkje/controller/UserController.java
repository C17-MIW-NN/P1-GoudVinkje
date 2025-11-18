package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import nl.miwnn.ch17.pixeldae.goudvinkje.dto.GoudVinkjeUserDTO;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.GoudVinkjeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author Simon van der Kooij
 */

@Controller
@RequestMapping("/gebruiker")
public class UserController {

    private final GoudVinkjeUserService goudVinkjeUserService;

    public UserController(GoudVinkjeUserService goudVinkjeUserService) {
        this.goudVinkjeUserService = goudVinkjeUserService;
    }

    @GetMapping({"/", "/overzicht"})
    public String showUserOverview(Model datamodel) {
        datamodel.addAttribute("users", goudVinkjeUserService.findAll());

        return "userOverview";
    }

    @GetMapping("/aanpassen/{username}")
    public String showEditUserForm(@PathVariable("username") String username, Model datamodel) {

        GoudVinkjeUserDTO goudVinkjeDTO = goudVinkjeUserService.editUser(username);

        return showUserForm(datamodel, goudVinkjeDTO);
    }

    @GetMapping("/toevoegen")
    public String showAddUserForm(Model datamodel) {

        return showUserForm(datamodel, new GoudVinkjeUserDTO());
    }

    @GetMapping("/verwijderen/{userID}")
    public String deleteUser (@PathVariable("userID") Long userID) {

        goudVinkjeUserService.deleteUserByID(userID);

        return "redirect:/gebruiker/overzicht";
    }

    @PostMapping("/opslaan")
    public String saveUserForm(@ModelAttribute("user") GoudVinkjeUserDTO goudVinkjeUserDTO, BindingResult result) {

        if (goudVinkjeUserService.usernameInUse(goudVinkjeUserDTO.getUsername()) &&
                goudVinkjeUserDTO.getUserID() == null) {
            result.rejectValue("username", "duplicate", "This username is not available");
        }

        if (!goudVinkjeUserDTO.getPassword().equals(goudVinkjeUserDTO.getConfirmPassword())) {
            result.rejectValue("password", "no.match", "The passwords do not match");
        }

        if (!result.hasErrors()) {
            goudVinkjeUserService.save(goudVinkjeUserDTO);
        } else {
            return "userForm";
        }

        return "redirect:/gebruiker/overzicht";
    }

    private String showUserForm(Model datamodel, GoudVinkjeUserDTO goudVinkjeUserDTO) {

        datamodel.addAttribute("user", goudVinkjeUserDTO);

        return "userForm";
    }
}

