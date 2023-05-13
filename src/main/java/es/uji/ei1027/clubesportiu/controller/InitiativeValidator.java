package es.uji.ei1027.clubesportiu.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.clubesportiu.model.Initiative;

public class InitiativeValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Initiative.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Initiative initiative = (Initiative)obj;
        if (initiative.getNameIni().trim().equals(""))
            errors.rejectValue("nameIni", "obligatori",
                    "Nombre no correcto");
        // Afegeix ací la validació per a Edat > 15 anys
    }
}
