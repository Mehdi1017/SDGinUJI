package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/InitiativeBackOffice")
public class InitiativeBackOffice {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;

    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listInitiative(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()){
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Pendientes");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("iniciativas", initiativeDao.getPendingInitiatives());
        return "InitiativeBackOffice/list";
    }

    @RequestMapping("/accept/{nInitiative}")
    public String acceptInitiative(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);

        if (usuario == null || !usuario.isAdmin()){
            return "redirect:/login";
        }

        iniciativa.setStat("Approved");
        initiativeDao.updateInitiative(iniciativa);

        model.addAttribute("CONTENT_TITLE","Iniciativa Aceptada");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("iniciativa", iniciativa);
        return "InitiativeBackOffice/accept";
    }

    @RequestMapping("/reject/{nInitiative}")
    public String rejectInitiative(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);
        if (usuario == null || !usuario.isAdmin()){
            return "redirect:/login";
        }

        iniciativa.setStat("Rejected");
        initiativeDao.updateInitiative(iniciativa);

        model.addAttribute("CONTENT_TITLE","Iniciativa Rechazada");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("iniciativa", iniciativa);
        return "InitiativeBackOffice/reject";
    }


}