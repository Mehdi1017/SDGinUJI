package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.OdsDao;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class AreaPrivada {
    @GetMapping("/area")
    public String index(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null)
            return "redirect:login";
        String nombre = usuario.getUsername();

        model.addAttribute("CONTENT_TITLE","Bienvenido " + nombre);
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        if(!usuario.isAdmin())
            return "front_office/area_privada";
        else
            return  "/login";
    }
}
