package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.action_participation.ActionParticipationDao;
import es.uji.ei1027.clubesportiu.dao.initiative_participation.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.dao.uji_participant.UjiParticipantDao;
import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
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
@RequestMapping("/initiative_participation")
public class InitiativeParticipationController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeParticipationDao initiativeParticipationDao;
    private UjiParticipantDao ujiParticipantDao;
    private String nInitiative;
    private ActionParticipationDao actionParticipationDao;

    private ActionDao actionDao;

    @Autowired
    public void setInitiativeParticipationDao(InitiativeParticipationDao initiativeParticipationDao) {
        this.initiativeParticipationDao = initiativeParticipationDao;
    }

    @Autowired
    public void setUjiParticipantDao(UjiParticipantDao ujiParticipantDao) {
        this.ujiParticipantDao = ujiParticipantDao;
    }
    @Autowired
    public void setActionParticipationDao(ActionParticipationDao actionParticipationDao) {
        this.actionParticipationDao = actionParticipationDao;
    }
    @Autowired
    public void setActionDao(ActionDao actionDao) {
        this.actionDao = actionDao;
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
            @ModelAttribute("participation") InitiativeParticipation participation,// RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/add");
            return "redirect:/login";
        }

        InitiativeParticiapantValidator validator = new InitiativeParticiapantValidator(ujiParticipantDao.getAllUjiParticipants(),
                this.initiativeParticipationDao.getAllInitiativeParticipations(nInitiative), usuario.getMail());

        session.setAttribute("nextUrl", "/myInitiative/addParticipant/"+nInitiative);
        participation.setNameIni(nInitiative);

        validator.validate(participation, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("nInitiative", this.nInitiative);
            model.addAttribute("particpants", this.initiativeParticipationDao.getAllInitiativeParticipations(nInitiative));
            model.addAttribute("CONTENT_TITLE", "Gestionando participantes");
            model.addAttribute("SELECTED_NAVBAR","Área privada");

            return "myInitiative/add_participant"; // TRY AGAIN, HAD ERRORS
        }

        initiativeParticipationDao.addInitiativeParticipation(participation);

        for (Action action: actionDao.getActions(nInitiative)){
            ActionParticipation actionParticipation = new ActionParticipation();
            actionParticipation.setMail(participation.getMail());
            actionParticipation.setNameIni(participation.getNameIni());
            actionParticipation.setNameAct(action.getNameAction());
            actionParticipation.setStartDate(participation.getStartDate());
            if(actionParticipationDao.getActionParticipation(actionParticipation) == null)
                actionParticipationDao.addActionParticipation(actionParticipation);
        }


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
        //session.setAttribute("nextUrl", "/myInitiative/addParticipant/"+nInitiative);
    }

}
