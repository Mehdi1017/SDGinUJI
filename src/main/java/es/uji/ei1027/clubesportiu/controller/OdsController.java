package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.OdsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ods")
public class OdsController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private OdsDao odsDao;

    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listOds(Model model) {
        model.addAttribute("allOds", odsDao.getAllOds());
        return "ods/list";
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @RequestMapping(value="/add")
//    public String addClassificacio(Model model) {
//        model.addAttribute("classificacio", new Classificacio());  // SET MODEL ATTRIBUTE
//        return "classificacio/add";
//    }
//
//    @RequestMapping(value="/add", method= RequestMethod.POST)
//    public String processAddSubmit(@ModelAttribute("classificacio") Classificacio classificacio,  // RETRIEVE MODEL ATTRIBUTE
//                                   BindingResult bindingResult) {
//        if (bindingResult.hasErrors())
//            return "classificacio/add";
//        classificacioDao.addClassificacio(classificacio);
//        return "redirect:list";
//    }
//
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
//
//    private ClassificacioService classificacioService;
//
//    @Autowired
//    public void setClassificacioService(ClassificacioService                                                classificacioService) {
//        this.classificacioService = classificacioService;
//    }
//
//    @RequestMapping("/perpais/{nProva}")
//    public String listClsfPerPais(@PathVariable String nProva,
//                                  Model model) {
//        model.addAttribute("classificacions",
//                classificacioService.getClassificationByCountry(nProva));
//        return "classificacio/perpais";
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------
//
//    @RequestMapping("/perprova/{nPais}")
//    public String listClsfPerProva(@PathVariable String nPais,
//                                   Model model) {
//        model.addAttribute("classificacions",
//                classificacioService.getClassificationByTest(nPais));
//        return "classificacio/perprova";
//    }
//
//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------


}
