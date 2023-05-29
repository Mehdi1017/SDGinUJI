package es.uji.ei1027.clubesportiu.controller.SDG;

import es.uji.ei1027.clubesportiu.controller.Initiative.MyInitiativeController;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;


public class TargetValidator implements Validator {

    private List<Target> targets;

    public TargetValidator(List<Target> targets) {
        this.targets = targets;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Target target = (Target) obj;


        if (targets != null && !targets.isEmpty()) {
            for (Target t : targets) {
                if (target.getNameTarg().trim().equals(t.getNameTarg()))
                    errors.rejectValue("nameTarg", "obligatori", "Nombre ya en uso");
            }
        }
    }
}
