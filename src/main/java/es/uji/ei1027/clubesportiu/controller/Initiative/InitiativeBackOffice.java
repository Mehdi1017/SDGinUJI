package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.external_services.MailManager;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
@RequestMapping("/InitiativeBackOffice")
public class InitiativeBackOffice {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;
    private MailManager mailManager;

    @Autowired
    public void setMailManager(MailManager mailmanager){
        this.mailManager = mailmanager;
    }

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
            session.setAttribute("nextUrl", "/InitiativeBackOffice/list");
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Pendientes");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("iniciativas", initiativeDao.getPendingInitiatives());
        return "back_office/InitiativeBackOffice/list";
    }

    @RequestMapping("/accept/{nInitiative}")
    public String acceptInitiative(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);

        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/area");
            return "redirect:/login";
        }
        mailManager.sendMail(iniciativa.getMail(),"Propuesta de Iniciativa aceptada","Su iniciativa: "+iniciativa.getNameIni() + "ha sido aceptada. \nJuntos hacemos un mundo mejor");

        iniciativa.setStat("Approved");
        initiativeDao.updateInitiative(iniciativa);

        model.addAttribute("CONTENT_TITLE","Iniciativa Aceptada");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("iniciativa", iniciativa);
        return "back_office/InitiativeBackOffice/accept";
    }

    @RequestMapping("/reject/{nInitiative}")
    public String rejectInitiative(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);
        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/area");
            return "redirect:/login";
        }

        iniciativa.setStat("Rejected");
        initiativeDao.updateInitiative(iniciativa);

        mailManager.sendMail(iniciativa.getMail(),"Propuesta de Iniciativa rechazada","Su iniciativa: "+iniciativa.getNameIni() + "ha sido rechazada. \nEsperamos que la próxima vez tengas mas suerte");

        model.addAttribute("CONTENT_TITLE","Iniciativa Rechazada");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("iniciativa", iniciativa);
        return "back_office/InitiativeBackOffice/reject";
    }
    @RequestMapping("/confirm/{action}/{nInitiative}")
    public String confirmation(Model model, HttpSession session, @PathVariable String nInitiative, @PathVariable String action){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);
        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/area");
            return "redirect:/login";
        }
        String actionTxt;
        if (Objects.equals(action, "accept")){
            actionTxt = "ACEPTAR";
        } else {
            actionTxt = "RECHAZAR";
        }
        model.addAttribute("accion",action);
        model.addAttribute("accionTxt",actionTxt);


        model.addAttribute("iniciativa",iniciativa);
        model.addAttribute("prevURL","/InitiativeBackOffice/list");

        model.addAttribute("CONTENT_TITLE","Confirmar ");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        return "back_office/InitiativeBackOffice/confirm";
    }
}