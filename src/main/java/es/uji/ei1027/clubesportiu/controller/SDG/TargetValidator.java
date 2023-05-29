package es.uji.ei1027.clubesportiu.controller.SDG;

import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.Ods;
import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;


public class TargetValidator implements Validator {

    private List<Target> targets;
    private Target currentSdg;

    public TargetValidator(List<Target> targets, Target t) {
        this.targets = targets;
        this.currentSdg = t;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Target targ = (Target) obj;
        if (targ.getNameTarg().isBlank()){
            errors.rejectValue("nameTarg", "obligatori", "Tienes que poner un nombre");
        }

        if (targets != null && !targets.isEmpty() && (currentSdg == null || !targ.getNameTarg().equals(currentSdg.getNameTarg()))) {
            for (Target iniciativa : targets) {
                if (targ.getNameTarg().trim().equals(iniciativa.getNameTarg()))
                    errors.rejectValue("nameTarg", "obligatori", "Nombre ya en uso");
            }

        }
    }

}
