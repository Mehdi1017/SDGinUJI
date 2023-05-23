package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.model.Ods;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    private OdsDao odsDao;
    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }
    @GetMapping("/")
    public String listSdg(Model model, HttpSession session) {

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        model.addAttribute("CONTENT_TITLE","Viendo SDGs");
        model.addAttribute("allOds", odsDao.getAllOds());
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        if (usuario == null || !usuario.isAdmin()) {
            return "sdg/list_public";
        }
        else {
            return "sdg/list_staff";
        }
    }

    @RequestMapping(value="/update/{nOds}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editOds(Model model, HttpSession session,
                          @PathVariable String nOds) {
        model.addAttribute("CONTENT_TITLE","Editando SDG");
        model.addAttribute("SELECTED_NAVBAR","SDGs");

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "sdg/list_public";
        }

        model.addAttribute("ods", odsDao.getOds(nOds));  // SET MODEL ATTRIBUTE
        return "sdg/update_staff";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("ods") Ods ods, HttpSession session, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors())
            return "sdg/update_staff";    // TRY AGAIN, HAD ERRORS

        odsDao.updateOds(ods);  // UPDATE
        String prevUrl = (String) session.getAttribute("prevURL");
        if (prevUrl != null){

            // return "redirect:"+prevUrl; TODO arreglar problemas con acentos en url
            return "redirect:/";
        }
        return "redirect:/";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    @RequestMapping(value="/view/{nOds}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String viewOds(Model model, HttpSession session,
                          @PathVariable String nOds) {
        System.out.println("VIEW ODS: " + nOds);
        Ods ods = odsDao.getOds(nOds);
        model.addAttribute("CONTENT_TITLE","Visualizando SDG: "+ods.getNameOds());
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        model.addAttribute("ods", ods);  // SET MODEL ATTRIBUTE

        session.setAttribute("prevURL","/view/"+nOds);


        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "sdg/view_public";
        }

        return "sdg/view_staff";
    }

    // TODO
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addOds(Model model, HttpSession session) {
        model.addAttribute("CONTENT_TITLE","Añadiendo SDG");
        model.addAttribute("SELECTED_NAVBAR","SDGs");

        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/";
        }

        model.addAttribute("ods", new Ods());  // SET MODEL ATTRIBUTE
        return "sdg/add";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String processAddSubmit(
            @ModelAttribute("ods") Ods ods, HttpSession session,
            BindingResult bindingResult) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors())
            return "sdg/add";

        odsDao.addOds(ods);
        return "redirect:/";
    }

}
