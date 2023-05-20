package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/initiative_participation")
public class InitiativeParticipationController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeParticipationDao initiativeParticipationDao;

    @Autowired
    public void setInitiativeParticipationDao(InitiativeParticipationDao initiativeParticipationDao) {
        this.initiativeParticipationDao = initiativeParticipationDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listInitiativeParticipations(Model model) {
        model.addAttribute("allInitiativeParticipations",
                initiativeParticipationDao.getAllInitiativeParticipations());
        return "initiative_participation/list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addInitiativeParticipation(Model model) {
        model.addAttribute("initiativeParticipation", new InitiativeParticipation());  // SET MODEL ATTRIBUTE
        return "initiative_participation/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("initiativeParticipation") InitiativeParticipation initiativeParticipation,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "initiative/add";
        initiativeParticipationDao.addInitiativeParticipation(initiativeParticipation);
        return "redirect:list";
    }

}
