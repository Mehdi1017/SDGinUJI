package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.model.Initiative;

import es.uji.ei1027.clubesportiu.model.Ods;
import es.uji.ei1027.clubesportiu.model.Target;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/initiative")
public class InitiativeController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;

    @Autowired
    private TargetDao targetDao;

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private InitiativeFilter iniFilter;

    @Autowired
    private OdsDao odsDao;

    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String list(Model model, HttpSession session) {
        session.setAttribute("prevUrl", "/initiative/list");

        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas actuales");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        List<Ods> ods = odsDao.getAllOds();
        model.addAttribute("allOds",ods);

        Map<String, List<Target>> listTargets = new HashMap<>();
        for (Ods o: ods){
            List<Target> listaIni = listTargets.computeIfAbsent(o.getNameOds(), k -> new LinkedList<>());
            listaIni.addAll(targetDao.getTargetByOds(o.getNameOds()));
        }
        model.addAttribute("OdsByTarget", listTargets);
        session.setAttribute("contexto", "initiative");


        model.addAttribute("allInitiative", iniFilter.getActualInitiativesByTarget());
        return "Initiative/list";

    }

    @RequestMapping("/list/ended")
    public String listEnded(Model model, HttpSession session) {
        session.setAttribute("prevUrl", "/initiative/list/ended");

        model.addAttribute("CONTENT_TITLE","Viendo Iniciativas finalizadas");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        List<Ods> ods = odsDao.getAllOds();
        model.addAttribute("allOds",ods);

        Map<String, List<Target>> listTargets = new HashMap<>();
        for (Ods o: ods){
            List<Target> listaIni = listTargets.computeIfAbsent(o.getNameOds(), k -> new LinkedList<>());
            listaIni.addAll(targetDao.getTargetByOds(o.getNameOds()));
        }
        model.addAttribute("OdsByTarget", listTargets);


        model.addAttribute("allInitiative", iniFilter.getEndedInitiativesByTarget());
        return "Initiative/list_ended";

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
    public String editInitiative(Model model, @PathVariable String nInitiative, HttpSession session) {
        Initiative initiative = initiativeDao.getInitiative(nInitiative); // RETRIEVE PATH VARIABLE
        model.addAttribute("initiative", initiative);
        model.addAttribute("CONTENT_TITLE", "Editando iniciativa 📝");
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");
        model.addAttribute("odsList", odsDao.getAllOds());  // needed data
        session.setAttribute("initiativeUpdate", nInitiative);
        session.setAttribute("nextUrl", "/initiative/update/"+nInitiative);// SET MODEL ATTRIBUTE
        return "Initiative/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("SELECTED_NAVBAR","Iniciativas");

        System.out.println("RECIBIDO UPDATE");

        // validate basic initiative data
        String nIni = session.getAttribute("initiativeUpdate").toString();
        initiative.setNameIni(nIni);
        InitiativeValidator initiativeValidator = new InitiativeValidator(initiativeDao.getAllInitiative());
        initiativeValidator.validate(initiative, bindingResult);
        if (false){
        //if (bindingResult.hasErrors()){

            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Editando Iniciativa 📝");
            return "Initiative/update";
        }
//        System.out.println(initiative);
        initiativeDao.updateInitiative(initiative);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

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
        } else if (!usuario.isAdmin() && usuario.getMail().equals(ini.getMail())){
            return "Initiative/view_user";
        } else if (usuario.isAdmin()){
            return "Initiative/view_staff";
        } else {
            return "Initiative/view_public";
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
        model.addAttribute("CONTENT_TITLE","Confirmar finalizacion");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("initiative",initiativeDao.getInitiative(nInitiative));
        model.addAttribute("usuario", usuario);
        return "Initiative/delete_confirm";
    }

}
