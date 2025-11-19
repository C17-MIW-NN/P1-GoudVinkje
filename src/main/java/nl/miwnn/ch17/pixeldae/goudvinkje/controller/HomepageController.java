package nl.miwnn.ch17.pixeldae.goudvinkje.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Simon van der Kooij
 *
 */
@Controller
public class HomepageController {

    @GetMapping("/")
    public String gotoHomepage() {
        return "homepage.html";
    }
}
