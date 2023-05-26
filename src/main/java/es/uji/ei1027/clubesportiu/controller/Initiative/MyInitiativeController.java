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
import java.util.*;

@Controller
@RequestMapping("/myInitiative")
public class MyInitiativeController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private InitiativeDao initiativeDao;
    private static Initiative initiative;
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
            session.setAttribute("nextUrl", "/myInitiative/list");
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
            session.setAttribute("nextUrl", "/myInitiative/add");
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝"); // page data
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        model.addAttribute("odsList", odsDao.getAllOds());  // needed data

        model.addAttribute("initiative", new Initiative());  // data to fill
        model.addAttribute("action", new Action());

//        System.out.println("Ejecuta add controller");

        return "myInitiative/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("initiative") Initiative initiative,
                                   BindingResult bindingResult, Model model, HttpSession session) {

        model.addAttribute("SELECTED_NAVBAR","Área privada");

        // validate basic initiative data
        InitiativeValidator initiativeValidator = new InitiativeValidator(initiativeDao.getAllInitiative());
        initiativeValidator.validate(initiative, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝");
            return "myInitiative/add";
        }

//        // validate actions - inside initiative
//        if (initiative.getActions().isEmpty()) {
//            model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
//            model.addAttribute("CONTENT_TITLE","Creando una Iniciativa 📝 - Faltan Acciones");
//            return "myInitiative/add";
//        }

        // complete initiative with user
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        initiative.setMail(usuario.getMail());

        // set initiative as session parameter for persistance
        session.setAttribute("tmp_initiative", initiative);
        model.addAttribute("action", new Action());

//        initiativeDao.addInitiative(initiative);
//        model.addAttribute("CONTENT_TITLE","Iniciativa Enviada! 😁📤");

        // redirect to action creation - page with tmp info + form for action creation
        model.addAttribute("CONTENT_TITLE","Creando una Iniciativa - Añadiendo Acciones 📝");
        return "myInitiative/addAction";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------



    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/update/{nInitiative}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editInitiative(Model model, @PathVariable String nInitiative, HttpSession session) {
        initiative = initiativeDao.getInitiative(nInitiative); // RETRIEVE PATH VARIABLE
        model.addAttribute("initiative", initiative);
        model.addAttribute("CONTENT_TITLE", "Editando iniciativa 📝");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("odsList", odsDao.getAllOds());  // needed data
        session.setAttribute("nextUrl", "/myInitiative/update/"+nInitiative);// SET MODEL ATTRIBUTE
        return "myInitiative/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("initiative") Initiative initiative, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult, Model model, HttpSession session) {
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        // validate basic initiative data
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
            Model model,
            @ModelAttribute("updatedInitiative") Initiative updatedInitiative,// RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "myInitiative/addResult";    // TRY AGAIN, HAD ERRORS

        System.out.println(initiative.getNameIni());
        initiative.setResultados(updatedInitiative.getResultados());
        initiativeDao.updateInitiative(initiative);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    public static Initiative getOldInitiative(){
        return initiative;
    }
}
