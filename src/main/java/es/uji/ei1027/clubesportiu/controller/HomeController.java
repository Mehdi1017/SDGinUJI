package es.uji.ei1027.clubesportiu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("CONTENT_TITLE","EI1027 - Spring MVC");
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        return "index";
    }
}
