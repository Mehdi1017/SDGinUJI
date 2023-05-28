package es.uji.ei1027.clubesportiu.controller.subscription;

import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.subscription.SubscriptionDao;
import es.uji.ei1027.clubesportiu.model.Subscription;
import es.uji.ei1027.clubesportiu.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@Controller
@RequestMapping("/member_subscription")
public class MemberSubscriptionController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private SubscriptionDao subscriptionDao;
    @Autowired
    public void setSubscriptionDao(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    private OdsDao odsDao;
    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/list_sdg")
    public String listSdg(Model model, HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            session.setAttribute("nextUrl", "/member_subscription/list_sdg");
            return "redirect:/login";
        }

        model.addAttribute("CONTENT_TITLE","Viendo SDGs y Subscripciones");
        model.addAttribute("SELECTED_NAVBAR","SDGs");

        model.addAttribute("allOds", odsDao.getAllOds());
        model.addAttribute("myOds", subscriptionDao.getAllSubscriptionByMem(usuario.getMail()));
        return "member_subscription/list_sdg";

    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add/{nOds}")
    public String addSubscription(Model model,
                                  @PathVariable String nOds,
                                  HttpSession session) {
        UserDetails usuario = (UserDetails) session.getAttribute("user");
        if (usuario == null) {
            session.setAttribute("nextUrl", "/member_subscription/list_sdg");
            return "redirect:/login";
        }

        // validate subs

        // create subs
        Subscription subscription = new Subscription();
        subscription.setMail(usuario.getMail());
        subscription.setNameOds(nOds);
        subscription.setInitialDate(LocalDate.now());
        subscription.setIdSub(1);
        subscriptionDao.addSubscription(subscription);

        // redirect to list again
        model.addAttribute("CONTENT_TITLE","Viendo SDGs y Subscripciones");
        model.addAttribute("allOds", odsDao.getAllOds());
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        model.addAttribute("myOds", subscriptionDao.getAllSubscriptionByMem(usuario.getMail()));

        return "member_subscription/list_sdg";
    }

   /* @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("subscription") Subscription subscription,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "subscription/add";
        subscriptionDao.addSubscription(subscription);
        return "redirect:list";
    }
*/
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