package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.servlet.http.HttpSession;
import java.util.List;


public class ActionValidator implements Validator {

    private Initiative initiative;
    private HttpSession session;

    public ActionValidator(Initiative initiative, HttpSession session) {

        this.initiative = initiative;
        this.session = session;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Action.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Action action = (Action) obj;

        if (action.getCreationDate().compareTo(initiative.getStartDate()) < 0)
            errors.rejectValue("creationDate", "erroneo", "Una accion no puede ser anterior a su iniciativa");

        if (action.getEndDate().compareTo(initiative.getEndDate()) > 0)
            errors.rejectValue("endDate", "erroneo", "Una accion no puede ser posterior a su iniciativa");
        else if (action.getCreationDate().compareTo(action.getEndDate()) >= 0)
            errors.rejectValue("endDate", "erroneo", "La fecha de fin no puede ser inferior ni igual a la de inicio");

        Object accion = session.getAttribute("nAct");
        if (accion == null || !accion.toString().equals(action.getNameAction())) {
            if (initiative.getActions() != null && !initiative.getActions().isEmpty()) {
                for (Action action1 : initiative.getActions()) {
                    if (action1.getNameAction().trim().equals(action.getNameAction().trim()))
                        errors.rejectValue("nameAction", "obligatori", "Nombre no correcto");
                }
            }
        }
    }
}
