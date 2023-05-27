package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.initiative_participation.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
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
@RequestMapping("/initiative_participation")
public class InitiativeParticipationController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeParticipationDao initiativeParticipationDao;
    private String nInitiative;

    @Autowired
    public void setInitiativeParticipationDao(InitiativeParticipationDao initiativeParticipationDao) {
        this.initiativeParticipationDao = initiativeParticipationDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/addParticipant/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String addParticipant(Model model, HttpSession session,
                                 @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE

        this.nInitiative = nInitiative;
        setAtributes(model, session);
        return "myInitiative/add_participant";
    }

    @RequestMapping(value="/addParticipant", method = RequestMethod.POST)
    public String processAddParticipantSubmit(
            Model model, HttpSession session,
            @ModelAttribute("partcipation") InitiativeParticipation participation,// RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE", "Gestionando participantes");
            model.addAttribute("SELECTED_NAVBAR","Área privada");
            return "myInitiative/add_participant"; // TRY AGAIN, HAD ERRORS
        }

        session.setAttribute("nextUrl", "/myInitiative/addParticipant/"+nInitiative);
        participation.setNameIni(nInitiative);
        initiativeParticipationDao.addInitiativeParticipation(participation);

        setAtributes(model, session);

        return "myInitiative/add_participant";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    @RequestMapping(value = "/delete/{mail}")
    public String processDeleteParticipation(Model model, HttpSession session, @PathVariable String mail) {
        System.out.println(mail+nInitiative);
        InitiativeParticipation participation = new InitiativeParticipation();
        participation.setMail(mail);
        participation.setNameIni(nInitiative);
        initiativeParticipationDao.deleteInitiativeParticipation(participation);
        System.out.println("acabo");
        setAtributes(model, session);
        return "myInitiative/add_participant";
    }

    public void setAtributes(Model model, HttpSession session){
        model.addAttribute("nInitiative", this.nInitiative);
        model.addAttribute("particpants", this.initiativeParticipationDao.getAllInitiativeParticipations(nInitiative));
        model.addAttribute("participation", new InitiativeParticipation());
        model.addAttribute("CONTENT_TITLE", "Gestionando participantes");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        session.setAttribute("nextUrl", "/myInitiative/addParticipant/"+nInitiative);
    }

}
