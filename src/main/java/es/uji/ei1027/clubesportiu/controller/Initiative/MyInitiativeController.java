package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.initiative_participation.InitiativeParticipationDao;
import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.dao.uji_participant.UjiParticipantDao;
import es.uji.ei1027.clubesportiu.model.*;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/myInitiative")
public class MyInitiativeController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;
    private static Initiative initiative;
    private OdsDao odsDao;

    private TargetDao targetDao;
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
    @Autowired
    public void setTargetDao(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listInitiative(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/list");
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Mostrando tus Iniciativas 📋");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        List<Initiative> myInitiatives = initiativeDao.getMyInitiative(usuario.getMail());
        model.addAttribute("myInitiatives", myInitiatives);
        session.setAttribute("contexto", "myInitiative");

        return "myInitiative/list";
    }

    // ADD (3 STEPS - add form - send add + addAction form - save complete initiative)
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    // prepare model - redirecto to form
    @RequestMapping(value="/add")
    public String addInitiative(Model model, HttpSession session) {
        if (validateUser(session) != null) return validateUser(session);

        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝"); // page data
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("odsList", odsDao.getAllOds());  // needed data

        if (session.getAttribute("tmp_initiative") != null)
            model.addAttribute("initiative", session.getAttribute("tmp_initiative"));

        else
            model.addAttribute("initiative", new Initiative());  // data to fill
        return "myInitiative/add";
    }

    // retrieve initial form values - save persistent model data - redirect to action creation form
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddInitiative(@ModelAttribute("initiative") Initiative initiative,
                                       BindingResult bindingResult, Model model, HttpSession session) {
        if (validateUser(session) != null) return validateUser(session);

        UserDetails usuario = (UserDetails) session.getAttribute("user");

        System.out.println("[myIni Controller]" + initiative);

        // validate basic initiative data
        InitiativeValidator initiativeValidator = new InitiativeValidator(initiativeDao.getAllInitiative());
        initiativeValidator.validate(initiative, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝");
            return "myInitiative/add";
        }

        // complete initiative with user & prev actions if exists
        initiative.setMail(usuario.getMail());
        if ( session.getAttribute("tmp_initiative") != null &&
                ((Initiative) session.getAttribute("tmp_initiative")).getNameOds().equals(initiative.getNameOds())) // si misma ods --> no editada !
            initiative.setActions(((Initiative) session.getAttribute("tmp_initiative")).getActions());

        // set initiative as session parameter for persistance | overwrite if existing
        session.setAttribute("tmp_initiative", initiative);

        // prepare model for action form page
        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        model.addAttribute("action", new Action());

        // redirect to action creation - page with tmp info + form for action creation
        return "myInitiative/addAction";
    }

    @RequestMapping(value="/addAction", method= RequestMethod.POST)
    public String processAddAction(@ModelAttribute("action") Action action,
                                   @SessionAttribute("tmp_initiative") Initiative initiative,
                                   BindingResult bindingResult, Model model, HttpSession session) {
        if (validateUser(session) != null) return validateUser(session);

        // validate new action
        ActionValidator actionValidator = new ActionValidator(initiative, session);
        actionValidator.validate(action, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
            model.addAttribute("SELECTED_NAVBAR","Área privada");
            model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data
            session.setAttribute("tmp_initiative", initiative);
            return "myInitiative/addAction";
        }

        // complete & add action to persistent initiative
        action.setNameInitiative(initiative.getNameIni());
        action.setNameOds(initiative.getNameOds());
        initiative.getActions().add(action);
        session.setAttribute("tmp_initiative", initiative);

        // prepare model for new action
        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        model.addAttribute("action", new Action());

        // redirect to action creation - page with tmp info + form for action creation
        return "myInitiative/addAction";
    }
    
    @RequestMapping(value="/editAction/{nAction}")
    public String processEditAction(@SessionAttribute("tmp_initiative") Initiative initiative,
                                    @PathVariable String nAction,
                                    Model model, HttpSession session) {
        if (validateUser(session) != null) return validateUser(session);

        // extract & delete action from persistent data
        Action act = new Action();
        for (Action action : initiative.getActions())
            if (action.getNameAction().equals(nAction)) {
                act = action;
                break;
            }
        initiative.getActions().remove(act);

        // set extracted action as model param for next page form
        model.addAttribute("action", act);

        // set initiative as session parameter for persistance | overwrite if existing
        session.setAttribute("tmp_initiative", initiative);

        // prepare model for action form page
        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        // redirect to addAction with model set
        return "myInitiative/addAction";
    }

    @RequestMapping(value="/submitInitiative")
    public String processAddFinal(@SessionAttribute("tmp_initiative") Initiative initiative,
                                  Model model, HttpSession session) {
        if (validateUser(session) != null) return validateUser(session);

        // validate actions - inside initiative
        if (initiative.getActions().isEmpty()) {
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añade al menos Una Acción ❌");
            model.addAttribute("SELECTED_NAVBAR","Área privada");
            model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data
            session.setAttribute("tmp_initiative", initiative);
            model.addAttribute("action", new Action());
            return "myInitiative/addAction";
        }

        // save initiative & prepare and save actions
        initiativeDao.addInitiative(initiative);
        for (Action action : initiative.getActions()) {
            action.setNameInitiative(initiative.getNameIni());
            action.setNameOds(initiative.getNameOds());
            actionDao.addActionn(action);
        }

        // prepare & redirect to feedback template
        model.addAttribute("CONTENT_TITLE","Iniciativa Enviada! 😁📤");
        model.addAttribute("initiative", initiative);
        session.removeAttribute("tmp_initiative");

        return "myInitiative/iniciativa_creada";
    }

    // ADD FUNCTIONALITIES IN addAction Form
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    // delete, update action methods & edit init
    @RequestMapping(value = "/deleteAction/{nAction}")
    public String processDeleteAction(@PathVariable String nAction,
                                      @SessionAttribute("tmp_initiative") Initiative initiative,
                                      Model model) {
        initiative.getActions().removeIf((action) -> action.getNameAction().equals(nAction));

        // prepare model for action form page
        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        model.addAttribute("action", new Action());

        // redirect to action creation - page with tmp info + form for action creation
        return "myInitiative/addAction";
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/update/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editInitiative(Model model, @PathVariable String nInitiative, HttpSession session) {
        initiative = initiativeDao.getInitiative(nInitiative); // RETRIEVE PATH VARIABLE
        model.addAttribute("initiative", initiative);
        model.addAttribute("CONTENT_TITLE", "Editando iniciativa 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("odsList", odsDao.getAllOds());  // needed data
        session.setAttribute("initiativeUpdate", nInitiative);
        session.setAttribute("nextUrl", "/myInitiative/update/"+nInitiative);// SET MODEL ATTRIBUTE
        return "myInitiative/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        // validate basic initiative data
        String nIni = session.getAttribute("initiativeUpdate").toString();
        initiative.setNameIni(nIni);
        InitiativeValidator initiativeValidator = new InitiativeValidator(initiativeDao.getAllInitiative());
        initiativeValidator.validate(initiative, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Editando Iniciativa 📝");
            return "myInitiative/update";
        }
//        System.out.println(initiative);
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        initiative.setMail(usuario.getMail());
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

    @RequestMapping(value="/addResult/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String addResult(Model model, HttpSession session,
                                 @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE
        initiative = initiativeDao.getInitiative(nInitiative);
        model.addAttribute("updatedInitiative", initiativeDao.getInitiative(nInitiative));
        model.addAttribute("CONTENT_TITLE", "Añadiendo resultados");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        session.setAttribute("nextUrl", "/myInitiative/addResult/"+nInitiative);
        return "myInitiative/add_result";
    }

    @RequestMapping(value="/addResult", method = RequestMethod.POST)
    public String processAddResultSubmit(
            @ModelAttribute("updatedInitiative") Initiative updatedInitiative,// RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {

        //System.out.println(initiative.getNameIni());
        initiative.setResultados(updatedInitiative.getResultados());
        initiativeDao.updateInitiative(initiative);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    @RequestMapping(value="/view/{nInitiative}")  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String viewInitiative(Model model, HttpSession session,
                                 @PathVariable String nInitiative) {  // RETRIEVE PATH VARIABLE
        Initiative ini = initiativeDao.getInitiative(nInitiative);
        ini.setActions(actionDao.getActions(ini.getNameIni()));
        model.addAttribute("initiative", ini);
        model.addAttribute("CONTENT_TITLE", "Viendo Iniciativa");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        session.setAttribute("nextUrl", "/myInitiative/view/"+ UriUtils.encodePath(nInitiative, "UTF-8"));
        session.setAttribute("prevUrl2", "/myInitiative/view/"+nInitiative);

        return "myInitiative/view_user";
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    public static Initiative getOldInitiative(){
        return initiative;
    }

    @Nullable
    private static String validateUser(HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/add");
            return "redirect:/login";
        }
        return null;
    }
}
