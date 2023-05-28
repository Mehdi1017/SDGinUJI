package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.model.Initiative;

import es.uji.ei1027.clubesportiu.model.UserDetails;
import es.uji.ei1027.clubesportiu.services.InitiativeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/initiative")
public class InitiativeController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private InitiativeFilter iniFilter;

    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    /*
    @RequestMapping("/list")
    public String listInitiative(Model model, HttpSession session) {
        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas actuales");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        model.addAttribute("allInitiative", initiativeDao.getAllActualInitiative());
        return "Initiative/list_no_filters";

    }*/
    @RequestMapping("/list")
    public String listInitiativeByOds(Model model, HttpSession session) {
        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas actuales por SDG");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        session.setAttribute("prevUrl", "/initiative/list");


        model.addAttribute("allInitiative", iniFilter.getActualInitiativesByODS());
        return "Initiative/list_by_ods";

    }
    @RequestMapping("/list/by-target")
    public String listInitiativeByTarget(Model model, HttpSession session) {
        session.setAttribute("prevUrl", "/initiative/list/by-target");

        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas actuales por Target");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        model.addAttribute("allInitiative", iniFilter.getActualInitiativesByTarget());
        return "Initiative/list_by_target";

    }

    @RequestMapping("/list/ended")
    public String listInitiativeByOdsEnded(Model model, HttpSession session) {
        session.setAttribute("prevUrl", "/initiative/list/ended");

        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas finalizadas por SDG");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        model.addAttribute("allInitiative", iniFilter.getEndedInitiativesByODS());
        return "Initiative/list_by_ods";

    }
    @RequestMapping("/list/by-target/ended")
    public String listInitiativeByTargetEnded(Model model, HttpSession session) {
        session.setAttribute("prevUrl", "/initiative/list/by-target/ended");

        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas finalizadas por Target");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        model.addAttribute("allInitiative", iniFilter.getEndedInitiativesByTarget());
        return "Initiative/list_by_target";

    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addInitiative(Model model) {
        model.addAttribute("initiative", new Initiative());  // SET MODEL ATTRIBUTE
        return "Initiative/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("initiative") Initiative initiative,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Initiative/add";
        initiativeDao.addInitiativeNaif(initiative);
        return "redirect:list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

@RequestMapping(value="/update/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
public String editInitiative(Model model,
                      @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE
    model.addAttribute("initiative", initiativeDao.getInitiative(nInitiative));  // SET MODEL ATTRIBUTE
    return "Initiative/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
}

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "Initiative/update";    // TRY AGAIN, HAD ERRORS
        System.out.println(initiative);
        initiativeDao.updateInitiative(initiative);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }
   /* @RequestMapping(value = "/delete/{nInitiative}")
    public String processDeleteInitiative(@PathVariable String nInitiative) {
        initiativeDao.deleteInitiative(nInitiative);
        return "redirect:../list";
    }*/

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    @RequestMapping(value="/view/{nInitiative}")  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String viewInitiative(Model model, HttpSession session,
                                 @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE
        Initiative ini = initiativeDao.getInitiative(nInitiative);
        ini.setActions(actionDao.getActions(ini.getNameIni()));
        model.addAttribute("initiative", ini);
        model.addAttribute("CONTENT_TITLE", "Viendo Iniciativa");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");
        session.setAttribute("nextUrl", "/initiative/view/"+ UriUtils.encodePath(nInitiative, "UTF-8"));
        session.setAttribute("prevUrl2", "/initiative/view/"+nInitiative);

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            return "Initiative/view_public";
        } else if (!usuario.isAdmin()){
            return "Initiative/view_user";
        } else {
            return "Initiative/view_staff";
        }
    }

    @RequestMapping("/delete/confirm/{nInitiative}")
    public String deleteConfirm(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            return "redirect:/login";
        }
        try {
            initiativeDao.deleteInitiative(nInitiative);
        } catch (DataAccessException e){
            model.addAttribute("initiative",initiativeDao.getInitiative(nInitiative));
            return "Initiative/error_delete";
        }
        if (usuario.isAdmin())
            return "redirect:/initiative/list";
        return "redirect:/myInitiative/list";
    }

    @RequestMapping("/delete/{nInitiative}")
    public String delete(Model model, HttpSession session, @PathVariable String nInitiative){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("initiative",initiativeDao.getInitiative(nInitiative));
        model.addAttribute("usuario", usuario);
        return "Initiative/delete_confirm";
    }

}
