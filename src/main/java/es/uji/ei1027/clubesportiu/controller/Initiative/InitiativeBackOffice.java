package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.subscription.SubscriptionDao;
import es.uji.ei1027.clubesportiu.services.MailManager;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.Subscription;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import es.uji.ei1027.clubesportiu.services.InitiativeFilter;
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
    private SubscriptionDao subscriptionDao;
    private MailManager mailManager;
    @Autowired
    private InitiativeFilter iniFilter;

    @Autowired
    public void setMailManager(MailManager mailmanager){
        this.mailManager = mailmanager;
    }

    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }
    @Autowired
    public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
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
        session.setAttribute("prevUrl", "/InitiativeBackOffice/list");


        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Pendientes por SDG");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("allInitiative", iniFilter.getPendingInitiativesByOds());
        return "back_office/InitiativeBackOffice/list_ods";
    }


    @RequestMapping("/list/by-target")
    public String listInitiativeByTarget(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/InitiativeBackOffice/list");
            return "redirect:/login";
        }
        session.setAttribute("prevUrl", "/InitiativeBackOffice/list");


        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Pendientes por Target");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("allInitiative", iniFilter.getPendingInitiativesByTarget());
        return "back_office/InitiativeBackOffice/list_target";
    }


    @RequestMapping("/list/rejected")
    public String listInitiativeRejected(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/InitiativeBackOffice/list/rejected");
            return "redirect:/login";
        }
        session.setAttribute("prevUrl", "/InitiativeBackOffice/list/rejected");


        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Rechazadas por SDG");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("allInitiative", iniFilter.getRejectedInitiativesByOds());
        return "back_office/InitiativeBackOffice/list_rejected_ods";
    }

    @RequestMapping("/list/rejected/by-target")
    public String listInitiativeRejectedByTarget(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/InitiativeBackOffice/list/rejected");
            return "redirect:/login";
        }
        session.setAttribute("prevUrl", "/InitiativeBackOffice/list/rejected");


        model.addAttribute("CONTENT_TITLE","Viendo las Iniciativas Rechazadas por Target");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("allInitiative", iniFilter.getRejectedInitiativesByOds());
        return "back_office/InitiativeBackOffice/list_rejected_target";
    }

    @RequestMapping("/accept/{nInitiative}")
    public String acceptInitiative(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        Initiative iniciativa = initiativeDao.getInitiative(nInitiative);

        if (usuario == null || !usuario.isAdmin()){
            session.setAttribute("nextUrl", "/area");
            return "redirect:/login";
        }

        iniciativa.setStat("Approved");
        initiativeDao.updateInitiative(iniciativa);

        // member && send mails to subscribers
        mailManager.sendMail(iniciativa.getMail(),"Propuesta de Iniciativa aceptada","Su iniciativa: "+iniciativa.getNameIni() + "ha sido aceptada. \nJuntos hacemos un mundo mejor");
        for (Subscription subscription : subscriptionDao.getAllActiveSubscriptionByOds(iniciativa.getNameOds()))
            mailManager.sendMail(subscription.getMail(),
                    "New initiative posted relative to " + iniciativa.getNameOds(),
                    "Buenas tardes! \n" +
                            "Una nueva iniciativa referente a tu ODS favorita " + iniciativa.getNameOds() + " ha sido publicada.\n" +
                            "Si deseas consultar más información ya está disponible en nuestra página.\n" +
                            "Un saludo.");

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

        // send mail to member
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