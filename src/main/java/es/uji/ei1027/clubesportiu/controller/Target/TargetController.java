package es.uji.ei1027.clubesportiu.controller.Target;


import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.model.Target;
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
@RequestMapping("/target")
public class TargetController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private TargetDao targetDao;
    private OdsDao odsDao;
    @Autowired
    public void setOdsDao(OdsDao odsDao){this.odsDao = odsDao;}

    @Autowired
    public void setTargetDao(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listTarget(Model model, HttpSession session) {
        model.addAttribute("allTarget", targetDao.getAllTarget());
        model.addAttribute("CONTENT_TITLE","Viendo Targets");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/list_public";
        }
        else {
            return "targets/list_staff";
        }
    }

    @RequestMapping("/view/by_ods/{nODS}")
    public String listTarget(Model model, HttpSession session, @PathVariable String nODS) {
        model.addAttribute("allTarget", targetDao.getTargetByOds(nODS));
       // model.addAttribute("ods",nODS);
        model.addAttribute("CONTENT_TITLE","Viendo Targets");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/list_public";
        }
        else {
            return "targets/list_staff";
        }
    }
    @RequestMapping("/view/{nODS}/{nTarg}")
    public String viewTarget(Model model, HttpSession session, @PathVariable String nODS, @PathVariable String nTarg) {
        model.addAttribute("target", targetDao.getTarget(nODS, nTarg));
        // model.addAttribute("ods",nODS);
        model.addAttribute("CONTENT_TITLE","Viendo Target");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "targets/view_public";
        }
        else {
            return "targets/view_staff";
        }
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add/{nODS}")
    public String addTarget(Model model, HttpSession session, @PathVariable String nODS) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");

        if (usuario == null || !usuario.isAdmin()) {
            session.setAttribute("nextUrl","/target/add");
            return "redirect:/login";

        }

        model.addAttribute("CONTENT_TITLE","Añadiendo Target");
        model.addAttribute("odsList", odsDao.getAllOds());  // SET MODEL ATTRIBUTE
        Target newTarget = new Target();
        newTarget.setNameOds(nODS);
        model.addAttribute("target", newTarget);  // SET MODEL ATTRIBUTE
        return "targets/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("target") Target target,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "targets/add";
        targetDao.addTarget(target);
        return "redirect:/target/view/by_ods/" + target.getNameOds();
    }

/*        @RequestMapping(value="/update/{nNadador}/{nProva}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editClassificacio(Model model,
                                    @PathVariable String nNadador,
                                    @PathVariable String nProva) {  // RETRIEVE PATH VARIABLE
        model.addAttribute("classificacio", classificacioDao.getClassificacio(nNadador, nProva));  // SET MODEL ATTRIBUTE
        return "classificacio/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }*/
/*
    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("classificacio") Classificacio classificacio, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "classificacio/update";    // TRY AGAIN, HAD ERRORS
        System.out.println(classificacio);
        classificacioDao.updateClassificacio(classificacio);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }*/

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @RequestMapping(value="/update/{nNadador}/{nProva}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
//    public String editClassificacio(Model model,
//                                    @PathVariable String nNadador,
//                                    @PathVariable String nProva) {  // RETRIEVE PATH VARIABLE
//        model.addAttribute("classificacio", classificacioDao.getClassificacio(nNadador, nProva));  // SET MODEL ATTRIBUTE
//        return "classificacio/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
//    }
//
//    @RequestMapping(value="/update", method = RequestMethod.POST)
//    public String processUpdateSubmit(
//            @ModelAttribute("classificacio") Classificacio classificacio, // RETRIEVE MODEL ATTRIBUTE
//            BindingResult bindingResult) {
//        if (bindingResult.hasErrors())
//            return "classificacio/update";    // TRY AGAIN, HAD ERRORS
//        System.out.println(classificacio);
//        classificacioDao.updateClassificacio(classificacio);  // UPDATE
//        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @RequestMapping(value = "/delete/{nNadador}/{nProva}")
//    public String processDeleteClassif(@PathVariable String nNadador,
//                                       @PathVariable String nProva) {
//        classificacioDao.deleteClassificacio(nNadador, nProva);
//        return "redirect:../../list";
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

}
