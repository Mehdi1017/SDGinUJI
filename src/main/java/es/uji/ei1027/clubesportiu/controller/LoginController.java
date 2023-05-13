package es.uji.ei1027.clubesportiu.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.clubesportiu.dao.UserDao;
import es.uji.ei1027.clubesportiu.model.UserDetails;

class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return UserDetails.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        UserDetails userDetails = (UserDetails) obj;

        // Check if username is empty
        if (userDetails.getUsername().isEmpty()) {
            errors.rejectValue("username", "empty.username", "Username cannot be empty");
        }

        // Check if password is empty
        if (userDetails.getPassword().isEmpty()) {
            errors.rejectValue("password", "empty.password", "Password cannot be empty");
        }
    }
}

@Controller
public class LoginController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/login")
    public String login(Model model, HttpSession session) {

        model.addAttribute("CONTENT_TITLE","Inicia Sesión");
        model.addAttribute("SELECTED_NAVBAR","none");

        model.addAttribute("user", new UserDetails());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") UserDetails user,
                             BindingResult bindingResult, HttpSession session, Model model) {
        UserValidator userValidator = new UserValidator();
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        // Comprova que el login siga correcte
        // intentant carregar les dades de l'usuari
        user = userDao.loadUserByUsername(user.getUsername(), user.getPassword());
        if (user == null) {
            bindingResult.rejectValue("password", "badpw", "Contrasenya incorrecta");
            return "login";
        }
        // Autenticats correctament.
        // Guardem les dades de l'usuari autenticat a la sessió
        session.setAttribute("user", user);

        // Torna a la pàgina principal
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (nextUrl == null){
            nextUrl = "/";
        }
        session.removeAttribute("nextUrl");
        return "redirect:" + nextUrl;

    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
