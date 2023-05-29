package es.uji.ei1027.clubesportiu.controller.SDG;

import es.uji.ei1027.clubesportiu.controller.Initiative.MyInitiativeController;
import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.Ods;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;


public class SDGValidator implements Validator {

    private List<Ods> sdgs;
    private Ods currentSdg;

    public SDGValidator(List<Ods> sdgs, Ods sdg) {
        this.sdgs = sdgs;
        this.currentSdg = sdg;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return Initiative.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Ods sdg = (Ods) obj;
        if (sdg.getNameOds().isBlank()){
            errors.rejectValue("nameOds", "obligatori", "Tienes que poner un nombre");
        }

        if (sdgs != null && !sdgs.isEmpty() && (currentSdg == null || !sdg.getNameOds().equals(currentSdg.getNameOds()))) {
            for (Ods iniciativa : sdgs) {
                if (sdg.getNameOds().trim().equals(iniciativa.getNameOds()))
                    errors.rejectValue("nameOds", "obligatori", "Nombre ya en uso");

                if (Objects.equals(sdg.getIndex(), iniciativa.getIndex()))
                    errors.rejectValue("index", "obligatori", "Índice ya en uso");
        }

            }
        }

}
