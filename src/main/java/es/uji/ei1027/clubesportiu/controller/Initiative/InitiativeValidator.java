package es.uji.ei1027.clubesportiu.controller.Initiative;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.clubesportiu.model.Initiative;

import java.util.List;


public class InitiativeValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
        // return Initiative.class.isAssignableFrom(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Initiative initiative = (Initiative) obj;

        if (initiative.getStartDate().compareTo(initiative.getEndDate()) >= 0)
            errors.rejectValue("endDate", "erroneo", "La fecha de fin no puede ser inferior ni igual a la de inicio");



        List<Initiative> iniciativas = MyInitiativeController.iniciativas;
        if (iniciativas != null && !iniciativas.isEmpty()) {
            for (Initiative iniciativa : iniciativas) {
                System.out.println(errors.getAllErrors());

                if (initiative.getNameIni().trim().equals(iniciativa.getNameIni()))
                    errors.rejectValue("nameIni", "obligatori", "Nombre no correcto");
            }
        }
    }
}
