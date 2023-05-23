package es.uji.ei1027.clubesportiu.controller;

import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.external_services.MailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private OdsDao odsDao;
    @Autowired
    public void setOdsDao(OdsDao odsDao) {
        this.odsDao = odsDao;
    }
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("CONTENT_TITLE","Viendo SDGs");
        model.addAttribute("allOds", odsDao.getAllOds());
        model.addAttribute("SELECTED_NAVBAR","SDGs");
        return "index";
    }
}
