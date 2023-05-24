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
        System.out.println("valor mal");
        Initiative initiative = (Initiative)obj;
        System.out.println("valor mal");
        List<Initiative> iniciativas = MyInitiativeController.iniciativas;
        System.out.println("valor mal");
        if (iniciativas != null && iniciativas.size() >0) {
            System.out.println("valor mal");
            for (Initiative iniciativa : iniciativas) {
                System.out.println(errors.getAllErrors());

                if (initiative.getNameIni().trim().equals(iniciativa.getNameIni())){
                    System.out.println("valor mal");
                    errors.rejectValue("nameIni", "obligatori",
                            "Nombre no correcto");
                }
            }
        }
        // Afegeix ací la validació per a Edat > 15 anys
    }
}
