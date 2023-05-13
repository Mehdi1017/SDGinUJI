package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.OdsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AreaPrivada {
    @GetMapping("/area")
    public String index(Model model) {

        model.addAttribute("CONTENT_TITLE","Bienvenido Usuario");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        return "front_office/area_privada";
    }
}
