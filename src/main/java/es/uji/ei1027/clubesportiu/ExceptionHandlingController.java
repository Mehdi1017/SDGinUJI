package es.uji.ei1027.clubesportiu;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({ DataAccessException.class,
            CannotGetJdbcConnectionException.class, TransientDataAccessException.class })
    public String handleDatabaseException(Exception ex, Model model) {

        model.addAttribute("CONTENT_TITLE","DB error");
        model.addAttribute("msg", "Error accediendo a la base de datos");
        return "errorDB";
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class, IncorrectResultSizeDataAccessException.class })
    public String handleDataNotFoundException(Exception ex, Model model) {

        model.addAttribute("CONTENT_TITLE","DB error");
        model.addAttribute("msg", "No encontramos tus datos");
        return "errorDB";
    }

    // Add more exception handlers if needed

}