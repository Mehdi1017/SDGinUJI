package es.uji.ei1027.clubesportiu.controller.Initiative;

import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import es.uji.ei1027.clubesportiu.model.UjiParticipant;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;


public class InitiativeParticiapantValidator implements Validator {

    private List<UjiParticipant> ujiParticipants;
    private List<InitiativeParticipation> participations;
    private String owner;

    public InitiativeParticiapantValidator(List<UjiParticipant> ujiParticipants, List<InitiativeParticipation> participations, String owner) {
        this.ujiParticipants = ujiParticipants;
        this.participations = participations;
        this.owner = owner;
    }

    @Override
    public boolean supports(Class<?> cls) {
        return InitiativeParticipation.class.equals(cls);
        // o, si volguérem tractar també les subclasses:
    }

    @Override
    public void validate(Object obj, Errors errors) {
        InitiativeParticipation participation = (InitiativeParticipation) obj;

        boolean mail_error = false;

        if (participation.getMail().equals(owner)) {
            errors.rejectValue("mail", "erroneo", "El responsable no se puede inscribir como participante");
            mail_error = true;
        }

        if (!mail_error) {
            for (InitiativeParticipation participation1 : participations){
                if (participation1.getMail().equals(participation.getMail())) {
                    errors.rejectValue("mail", "erroneo", "El participante ya existe");
                    mail_error = true;
                    break;
                }
            }
        }

        if (!mail_error) {
            boolean encontrado = false;
            for (UjiParticipant ujiParticipant : ujiParticipants)
                if (ujiParticipant.getMail().equals(participation.getMail())) {
                    encontrado = true;
                    break;
                }
            if (!encontrado) errors.rejectValue("mail", "erroneo", "El mail introducido no pertenece a un miembro de la UJI");
        }

    }
}
