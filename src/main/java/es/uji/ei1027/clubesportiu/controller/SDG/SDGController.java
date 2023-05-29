package es.uji.ei1027.clubesportiu.controller.SDG;

import es.uji.ei1027.clubesportiu.controller.Initiative.InitiativeValidator;
import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.model.Ods;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class SDGController {
    private OdsDao odsDao;
    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }
    @GetMapping("/")
    public String listSdg(Model model, HttpSession session) {
        session.setAttribute("prevUrl","/");
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        model.addAttribute("CONTENT_TITLE","Viendo SDGs");
        model.addAttribute("allOds", odsDao.getAllOds());
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        session.setAttribute("nextUrl", "/");
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
            BindingResult bindingResult,Model model) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/";
        }

        SDGValidator sdgValidator = new SDGValidator(odsDao.getAllOds(), null);
        sdgValidator.validate(ods, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Modificando SDG");
            model.addAttribute("SELECTED_NAVBAR","SDGs");

            return "sdg/update_staff";    // TRY AGAIN, HAD ERRORS
        }


        odsDao.updateOds(ods);  // UPDATE
        String prevUrl = (String) session.getAttribute("prevURL");
       // if (prevUrl != null){

            // return "redirect:"+prevUrl; TODO arreglar problemas con acentos en url
         //   return "redirect:/";
        //}
        return "redirect:/view/" + ods.getNameOds();     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    @RequestMapping(value="/view/{nOds}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String viewOds(Model model, HttpSession session,
                          @PathVariable String nOds) {
        Ods ods = odsDao.getOds(nOds);
        model.addAttribute("CONTENT_TITLE","Visualizando SDG");
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        model.addAttribute("ods", ods);  // SET MODEL ATTRIBUTE

        session.setAttribute("prevURL","/view/"+nOds);
        session.setAttribute("nOds", nOds);

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
            BindingResult bindingResult, Model model) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/";
        }

        SDGValidator sdgValidator = new SDGValidator(odsDao.getAllOds(), null);
        sdgValidator.validate(ods, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("CONTENT_TITLE","Añadiendo SDG");
            model.addAttribute("SELECTED_NAVBAR","SDGs");
            return "sdg/add";

        }

        odsDao.addOds(ods);
        return "redirect:/";
    }
    @RequestMapping("/delete/confirm/{nODS}")
    public String deleteConfirm(Model model, HttpSession session, @PathVariable String nODS){
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null || !usuario.isAdmin()) {
            return "redirect:/login";
        }


        try {
            odsDao.deleteOds(nODS);
        } catch (DataAccessException e){
            model.addAttribute("ods",odsDao.getOds(nODS));
            return "sdg/error_delete";
        }
        return "redirect:/";
    }

        @RequestMapping("/delete/{nODS}")
    public String delete(Model model, HttpSession session, @PathVariable String nODS){
            UserDetails usuario = (UserDetails) session.getAttribute("user");
            if (usuario == null || !usuario.isAdmin()) {
                return "redirect:/login";
            }

        model.addAttribute("ods",odsDao.getOds(nODS));
        return "sdg/delete_confirm";
    }

}
