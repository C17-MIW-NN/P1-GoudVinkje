package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import nl.miwnn.ch17.pixeldae.goudvinkje.model.GoudVinkjeUser;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.GoudVinkjeUserRepository;
import nl.miwnn.ch17.pixeldae.goudvinkje.service.GoudVinkjeUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Simon van der Kooij
 *
 */

@Controller
@RequestMapping("/gebruiker")
public class UserController {

    private final GoudVinkjeUserRepository goudVinkjeUserRepository;
    private final GoudVinkjeUserService goudVinkjeUserService;

    public UserController(GoudVinkjeUserRepository goudVinkjeUserRepository,
                          GoudVinkjeUserService goudVinkjeUserService) {
        this.goudVinkjeUserRepository = goudVinkjeUserRepository;
        this.goudVinkjeUserService = goudVinkjeUserService;
    }

    @GetMapping({"/", "/overzicht"})
    public String showUserOverview(Model datamodel) {
        datamodel.addAttribute("users", goudVinkjeUserRepository.findAll());

        return "userOverview";
    }

    @GetMapping("/aanpassen/{username}")
    public String showEditUserForm(@PathVariable("username") String username, Model datamodel) {
        Optional<GoudVinkjeUser> optionalUser = goudVinkjeUserRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            return showUserForm(datamodel, optionalUser.get());
        }

        return "redirect:/gebruiker/overzicht";
    }

    @GetMapping("/toevoegen")
    public String showAddUserForm(Model datamodel) {

        return showUserForm(datamodel, new GoudVinkjeUser());
    }

    @GetMapping("/verwijderen/{userID}")
    public String deleteUser (@PathVariable("userID") Long userID) {

        goudVinkjeUserRepository.deleteById(userID);

        return "redirect:/gebruiker/overzicht";
    }

    @PostMapping("/opslaan")
    public String saveUserForm(@ModelAttribute("user") GoudVinkjeUser goudVinkjeUser, BindingResult result) {

        if (!result.hasErrors()) {
            goudVinkjeUserService.saveUser(goudVinkjeUser);
        }

        return "redirect:/gebruiker/overzicht";
    }



    private String showUserForm(Model datamodel, GoudVinkjeUser goudVinkjeUser) {

        datamodel.addAttribute("user", goudVinkjeUser);

        return "userForm";
    }
}

