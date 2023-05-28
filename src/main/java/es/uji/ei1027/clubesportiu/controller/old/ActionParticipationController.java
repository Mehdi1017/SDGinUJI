package es.uji.ei1027.clubesportiu.controller.old;

import es.uji.ei1027.clubesportiu.controller.Initiative.InitiativeParticiapantValidator;
import es.uji.ei1027.clubesportiu.dao.action_participation.ActionParticipationDao;
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
@RequestMapping("/actionParticipation")
public class ActionParticipationController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private ActionParticipationDao actionParticipationDao;



    private UjiParticipantDao ujiParticipantDao;

    @Autowired
    public void setUjiParticipantDao(UjiParticipantDao ujiParticipantDao) {
        this.ujiParticipantDao = ujiParticipantDao;
    }
    @Autowired
    public void setActionParticipationDao(ActionParticipationDao actionParticipationDao) {
        this.actionParticipationDao = actionParticipationDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/addParticipant/{nAct}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String addParticipant(Model model, HttpSession session,
                                 @PathVariable String nAct) {  // RETRIEVE PATH VARIABLE

        session.setAttribute("nAct", nAct);

        setAtributes(model, nAct, session.getAttribute("nIni").toString());
        model.addAttribute("participation", new ActionParticipation());
        return "action/add_participant";
    }

    public void setAtributes(Model model, String nAct, String nIni){
        model.addAttribute("nAct", nAct);
        model.addAttribute("nIni", nIni);
        model.addAttribute("particpants", actionParticipationDao.getAllActionParticipation(nAct, nIni));
        model.addAttribute("CONTENT_TITLE", "Gestionando participantes de la accion");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
    }

    @RequestMapping(value="/addParticipant", method = RequestMethod.POST)
    public String processAddParticipantSubmit(
            Model model, HttpSession session,
            @ModelAttribute("participation") ActionParticipation participation,// RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/list");
            return "redirect:/login";
        }

        String nAct = session.getAttribute("nAct").toString();
        String nIni = session.getAttribute("nIni").toString();

        ActionParticiapantValidator validator = new ActionParticiapantValidator(ujiParticipantDao.getAllUjiParticipants(),
                this.actionParticipationDao.getAllActionParticipation(nAct, nIni), usuario.getMail());

        session.setAttribute("nextUrl", "/action/addParticipant/"+nAct);
        participation.setNameIni(nIni);
        participation.setNameAct(nAct);

        validator.validate(participation, bindingResult);

        if (bindingResult.hasErrors()){
            setAtributes(model, nAct, nIni);
            return "action/add_participant"; // TRY AGAIN, HAD ERRORS
        }
        actionParticipationDao.addActionParticipation(participation);
        setAtributes(model, nAct, nIni);
        model.addAttribute("participation", new ActionParticipation());


        return "action/add_participant";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    @RequestMapping(value = "/delete/{mail}")
    public String processDeleteParticipation(Model model, HttpSession session, @PathVariable String mail) {
        ActionParticipation participation = new ActionParticipation();
        participation.setMail(mail);
        String nIni = session.getAttribute("nIni").toString();
        String nAct = session.getAttribute("nAct").toString();
        participation.setNameIni(nIni);
        participation.setNameAct(nAct);
        actionParticipationDao.deleteActionParticipation(participation);
        setAtributes(model, nAct, nIni);
        model.addAttribute("participation", new ActionParticipation());
        return "action/add_participant";
    }


//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

}
