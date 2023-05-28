package es.uji.ei1027.clubesportiu.controller.old;


import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/action")
public class ActionController {

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private ActionDao actionDao;

    @Autowired
    public void setActionDao(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping("/list")
    public String listTarget(Model model) {
        model.addAttribute("allActionn", actionDao.getAllAction());
        return "old/action/list";
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    @RequestMapping(value="/add")
    public String addTarget(Model model) {
        model.addAttribute("action", new Action());  // SET MODEL ATTRIBUTE
        return "/old/action/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("action") Action action,  // RETRIEVE MODEL ATTRIBUTE
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "action/add";
        actionDao.addActionn(action);
        return "redirect:list";
    }

//    // -----------------------------------------------------------------------------------------------------------------
//    // -----------------------------------------------------------------------------------------------------------------

}
