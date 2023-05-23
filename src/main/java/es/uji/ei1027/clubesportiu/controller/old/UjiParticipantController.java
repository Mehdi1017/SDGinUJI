package es.uji.ei1027.clubesportiu.controller.old;

import es.uji.ei1027.clubesportiu.dao.uji_participant.UjiParticipantDao;
import es.uji.ei1027.clubesportiu.model.UjiParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/uji_participant")
public class UjiParticipantController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private UjiParticipantDao ujiParticipantDao;

    @Autowired
    public void setUjiParticipantDao(UjiParticipantDao ujiParticipantDao) {
        this.ujiParticipantDao = ujiParticipantDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listUjiParticipants(Model model) {
        model.addAttribute("allUjiParticipants", ujiParticipantDao.getAllUjiParticipants());
        return "old/uji_participant/list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addUjiParticipant(Model model) {
        model.addAttribute("ujiParticipant", new UjiParticipant());  // SET MODEL ATTRIBUTE
        return "old/uji_participant/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("ujiParticipant") UjiParticipant ujiParticipant,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "old/uji_participant/add";
        ujiParticipantDao.addUjiParticipant(ujiParticipant);
        return "redirect:list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/update/{mail}", method = RequestMethod.GET)  // DEFINE MAPPIGN WITH PATH VARIABLE
    public String editUjiParticipant(Model model,
                          @PathVariable String mail) {  // RETRIEVE PATH VARIABLE
        model.addAttribute("ujiParticipant", ujiParticipantDao.getUjiParticipant(mail));  // SET MODEL ATTRIBUTE
        return "old/uji_participant/update";    // REDIRECT TO NEW VIEW WITH SET VALUES
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("ujiParticipant") UjiParticipant ujiParticipant, // RETRIEVE MODEL ATTRIBUTE
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "old/uji_participant/update";    // TRY AGAIN, HAD ERRORS
        System.out.println(ujiParticipant);
        ujiParticipantDao.updateUjiParticipant(ujiParticipant);  // UPDATE
        return "redirect:list";     // REDIRECT SO MODEL ATTRIBUTES ARE RESTARTED
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value = "/delete/{mail}")
    public String processDeleteUjiParticipant(@PathVariable String mail) {
        ujiParticipantDao.deleteUjiParticipant(mail);
        return "redirect:../list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

}
