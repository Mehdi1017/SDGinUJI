package es.uji.ei1027.clubesportiu.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class InitiativeParticipation {

    private String mail;
    private String nameIni;
    private String requestMessage;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate creationDate;
    private String stat;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;

    public InitiativeParticipation() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameIni() {
        return nameIni;
    }

    public void setNameIni(String nameIni) {
        this.nameIni = nameIni;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
