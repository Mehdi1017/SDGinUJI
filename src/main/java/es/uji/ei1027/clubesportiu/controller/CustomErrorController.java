package es.uji.ei1027.clubesportiu.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {



    @RequestMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        // Retrieve error details from the request and add them to the model if needed
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");

        // Add custom attributes to the model
        model.addAttribute("CONTENT_TITLE","Error " + statusCode + " 🤧🤒");


        // Add statusCode and exception attributes to the model

        // Return the name of your modified error.html template
        return "error";
    }
}