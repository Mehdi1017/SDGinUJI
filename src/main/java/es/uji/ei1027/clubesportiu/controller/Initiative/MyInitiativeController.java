package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.model.Action;
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
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/myInitiative")
public class MyInitiativeController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;
    private OdsDao odsDao;

    private ActionDao actionDao;

    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }
    @Autowired
    public void setOdsDao(OdsDao odsDao){this.odsDao = odsDao;}
    @Autowired
    public void setActionDao(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listInitiative(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Mostrando tus Iniciativas 📋");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("myInitiatives", initiativeDao.getMyInitiative(usuario.getMail()));
        return "myInitiative/list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addInitiative(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("initiative", new Initiative());  // SET MODEL ATTRIBUTE
        model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
        model.addAttribute("actions", new HashSet<Action>());
        return "myInitiative/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("initiative") Initiative initiative,
                                   @ModelAttribute("actions") HashSet<Action> actions,
                                   BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        InitiativeValidator initiativeValidator = new InitiativeValidator(initiativeDao.getAllInitiative());
        initiativeValidator.validate(initiative, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝");
            return "myInitiative/add";
        }

        // validate actions

        if (actions.isEmpty()) {
            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝 - Faltan Acciones");
            return "myInitiative/add";
        }

        // create initiative

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        initiative.setMail(usuario.getMail());
        initiativeDao.addInitiative(initiative);
        model.addAttribute("CONTENT_TITLE","Iniciativa Enviada! 😁📤");
        return "myInitiative/iniciativa_creada";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/update/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editInitiative(Model model, @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE
        model.addAttribute("initiative", initiativeDao.getInitiative(nInitiative));  // SET MODEL ATTRIBUTE
        return "initiative/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "initiative/update";    // TRY AGAIN, HAD ERRORS
        System.out.println(initiative);
        initiativeDao.updateInitiative(initiative);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/delete/{nInitiative}")
    public String processDeleteInitiative(@PathVariable String nInitiative) {
        initiativeDao.deleteInitiative(nInitiative);
        return "redirect:../list";
    }



    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

}
