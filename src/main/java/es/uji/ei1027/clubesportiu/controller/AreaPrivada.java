package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AreaPrivada {


    @GetMapping("/area")
    public String index(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            session.setAttribute("nextUrl", "/area");
            return "redirect:login";
        }
        String nombre = usuario.getUsername();

        model.addAttribute("SELECTED_NAVBAR","Área privada");
        if(!usuario.isAdmin()) {
            model.addAttribute("CONTENT_TITLE", "Bienvenid@ " + nombre + " 😆");
            return "front_office/area_privada";
        }
        else {
            model.addAttribute("CONTENT_TITLE", "Bienvenid@ " + nombre);
            return "back_office/area_privada";
        }
    }
}
