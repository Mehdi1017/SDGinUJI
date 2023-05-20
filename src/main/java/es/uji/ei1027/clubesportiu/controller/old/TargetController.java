package es.uji.ei1027.clubesportiu.controller.old;


import es.uji.ei1027.clubesportiu.dao.TargetDao;
import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/target")
public class TargetController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private TargetDao targetDao;

    @Autowired
    public void setTargetDao(TargetDao targetDao) {
        this.targetDao = targetDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listTarget(Model model) {
        model.addAttribute("allTarget", targetDao.getAllTarget());
        return "target/list";
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addTarget(Model model) {
        model.addAttribute("target", new Target());  // SET MODEL ATTRIBUTE
        return "target/add";
    }
/*
    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("target") Target target,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "target/add";
        targetDao.addTarget(target);
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
