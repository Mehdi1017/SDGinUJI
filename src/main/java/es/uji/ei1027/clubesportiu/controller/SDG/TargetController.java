package es.uji.ei1027.clubesportiu.controller.SDG;


import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.model.Target;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/target")
public class TargetController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private TargetDao targetDao;
    private OdsDao odsDao;

    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }

    @Autowired
    public void setTargetDao(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listTarget(Model model, HttpSession session) {
        model.addAttribute("allTarget", targetDao.getAllTarget());
        model.addAttribute("CONTENT_TITLE", "Viendo Targets");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/list_public";
        } else {
            return "targets/list_staff";
        }
    }

    @RequestMapping("/view/by_ods/{nODS}")
    public String listTarget(Model model, HttpSession session, @PathVariable String nODS) {
        model.addAttribute("allTarget", targetDao.getTargetByOds(nODS));
        // model.addAttribute("ods",nODS);
        model.addAttribute("CONTENT_TITLE", "Viendo Targets");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/list_public";
        } else {
            return "targets/list_staff";
        }
    }

    @RequestMapping("/view/{nODS}/{nTarg}")
    public String viewTarget(Model model, HttpSession session, @PathVariable String nODS, @PathVariable String nTarg) {
        model.addAttribute("target", targetDao.getTarget(nODS, nTarg));
        // model.addAttribute("ods",nODS);
        model.addAttribute("CONTENT_TITLE", "Viendo Target");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/view_public";
        } else {
            return "targets/view_staff";
        }
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/add/{nODS}")
    public String addTarget(Model model, HttpSession session, @PathVariable String nODS) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");

        if (usuario == null || !usuario.isAdmin()) {
            session.setAttribute("nextUrl", "/target/add");
            return "redirect:/login";

        }

        model.addAttribute("CONTENT_TITLE", "Añadiendo Target");
        model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
        Target newTarget = new Target();
        newTarget.setNameOds(nODS);
        model.addAttribute("target", newTarget);  // SET MODEL ATTRIBUTE
        return "targets/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("target") Target target, HttpSession session, // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult, Model model) {


        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }

        TargetValidator targetValidator = new TargetValidator(targetDao.getAllTarget(), null);
        targetValidator.validate(target, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Modificando Target");
            model.addAttribute("SELECTED_NAVBAR","SDGs");
            return "targets/add";    // TRY AGAIN, HAD ERRORS
        }

        targetDao.addTarget(target);

        model.addAttribute("allTarget", targetDao.getTargetByOds(target.getNameOds()));
        // model.addAttribute("ods",nODS);
        model.addAttribute("CONTENT_TITLE", "Viendo Targets");
        model.addAttribute("nODS", target.getNameOds());
        return "targets/list_staff";
    }

    @RequestMapping(value="/update/{nODS}/{nTarg}", method = RequestMethod.GET)
    public String updateTarget(Model model, HttpSession session, @PathVariable String nODS, @PathVariable String nTarg) {
        model.addAttribute("target", targetDao.getTarget(nODS, nTarg));
        model.addAttribute("CONTENT_TITLE", "Modificando Target");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        } else {
            session.setAttribute("prevTarget",null);
            return "targets/update_staff";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("target") Target target, HttpSession session, // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult, Model model) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }

        TargetValidator targetValidator = new TargetValidator(targetDao.getAllTarget(), (Target) session.getAttribute("prevTarget"));
        targetValidator.validate(target, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Modificando Target");
            model.addAttribute("SELECTED_NAVBAR","SDGs");
            return "targets/update_staff";    // TRY AGAIN, HAD ERRORS
        }

        targetDao.updateTarget(target);
        return "redirect:/target/view/" + target.getNameOds() + "/" + target.getNameTarg();
    }

    @RequestMapping("/delete/confirm/{nODS}/{nTarg}")
    public String deleteConfirm(Model model, HttpSession session, @PathVariable String nODS, @PathVariable String nTarg){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }
        try {
            targetDao.deleteTarget(targetDao.getTarget(nODS,nTarg));
        } catch (DataAccessException e){
            model.addAttribute("target",targetDao.getTarget(nODS,nTarg));
            return "targets/error_delete";
        }
        return "redirect:/target/view/by_ods/"+nODS;
    }

    @RequestMapping("/delete/{nODS}/{nTarg}")
    public String delete(Model model, HttpSession session, @PathVariable String nODS, @PathVariable String nTarg){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }
        model.addAttribute("target",targetDao.getTarget(nODS,nTarg));
        return "targets/delete_confirm";
    }
}