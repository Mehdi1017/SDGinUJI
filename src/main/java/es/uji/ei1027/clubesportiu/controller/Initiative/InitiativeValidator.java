package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.clubesportiu.model.Initiative;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class InitiativeValidator implements Validator {

    private List<Initiative> initiatives;

    public InitiativeValidator(List<Initiative> initiatives) {
        this.initiatives = initiatives;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Initiative initiative = (Initiative) obj;

        if (initiative.getStartDate().compareTo(initiative.getEndDate()) >= 0)
            errors.rejectValue("endDate", "erroneo", "La fecha de fin no puede ser inferior ni igual a la de inicio");

        if (initiatives != null && !initiatives.isEmpty()) {
            for (Initiative iniciativa : initiatives) {
                System.out.println(errors.getAllErrors());

                if (initiative.getNameIni().trim().equals(iniciativa.getNameIni()))
                    errors.rejectValue("nameIni", "obligatori", "Nombre no correcto");
            }
        }
    }
}
