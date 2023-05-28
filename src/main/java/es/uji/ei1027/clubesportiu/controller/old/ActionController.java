package es.uji.ei1027.clubesportiu.controller.old;


import es.uji.ei1027.clubesportiu.controller.Initiative.ActionValidator;
import es.uji.ei1027.clubesportiu.controller.Initiative.InitiativeValidator;
import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/action")
public class ActionController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private ActionDao actionDao;
    private TargetDao targetDao;
    private InitiativeDao initiativeDao;

    @Autowired
    public void setActionDao(ActionDao actionDao) {
        this.actionDao = actionDao;
    }
    @Autowired
    public void setInitiativeDao(InitiativeDao initiativeDao) {
        this.initiativeDao = initiativeDao;
    }
    @Autowired
    public void setActionDao(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list/{nIni}")
    public String listAction(Model model, HttpSession session, @PathVariable String nIni) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/action/list");
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Mostrando Acciones 📋");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        model.addAttribute("actions", actionDao.getActions(nIni));
        session.setAttribute("nIni", nIni);
        return "action/list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addAction(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/add");
            return "redirect:/login";
        }

        String nIni = (String) session.getAttribute("nIni");
        model.addAttribute("CONTENT_TITLE","Creando una Accion 📝"); // page data
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        Initiative initiative = initiativeDao.getInitiative(nIni);
        session.setAttribute("tmp_initiative", initiative);

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        model.addAttribute("action", new Action());

//        System.out.println("Ejecuta add controller");

        return "action/add";
    }


    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("action") Action action,
            @SessionAttribute("tmp_initiative") Initiative initiative,
            BindingResult bindingResult, Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/action/add");
            return "redirect:/login";
        }

        model.addAttribute("SELECTED_NAVBAR","Área privada");

        // validate new action
        ActionValidator actionValidator = new ActionValidator(initiative);
        actionValidator.validate(action, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Creando una Accion 📝");
            model.addAttribute("SELECTED_NAVBAR","Área privada");
            model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data
            session.setAttribute("tmp_initiative", initiative);
            return "action/add";
        }

        // complete & add action to persistent initiative
        action.setNameInitiative(initiative.getNameIni());
        action.setNameOds(initiative.getNameOds());
        actionDao.addActionn(action);

        model.addAttribute("CONTENT_TITLE","Mostrando Acciones 📋");
        model.addAttribute("SELECTED_NAVBAR","Área privada");
        String nIni = initiative.getNameIni();
        model.addAttribute("actions", actionDao.getActions(nIni));
        session.setAttribute("nIni", nIni);

        return "action/list";
    }

    @RequestMapping(value="/update/{nAct}", method = RequestMethod.GET)
    public String editAction(Model model, HttpSession session, @PathVariable String nAct) {

        String nIni = (String) session.getAttribute("nIni");
        Action action = actionDao.getAction(nAct, nIni);
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null){
            session.setAttribute("nextUrl", "/myInitiative/list");
            return "redirect:/login";
        }


        model.addAttribute("CONTENT_TITLE","Editando una Accion 📝"); // page data
        model.addAttribute("SELECTED_NAVBAR","Área privada");

        Initiative initiative = initiativeDao.getInitiative(nIni);
        session.setAttribute("tmp_initiative", initiative);

        model.addAttribute("targList", targetDao.getTargetByOds(initiative.getNameOds()));  // needed data

        model.addAttribute("action", action);
        session.setAttribute("nextUrl", "/action/update/"+nAct);
        return "action/update";
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

}
