package nl.miwnn.ch17.pixeldae.goudvinkje.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Simon van der Kooij
 * Handle request regarding the homepage
 */

@Controller
public class HomepageController {

    @GetMapping({"/", "/login"})
    public String gotoHomepage() {
        return "homepage.html";
    }
}
