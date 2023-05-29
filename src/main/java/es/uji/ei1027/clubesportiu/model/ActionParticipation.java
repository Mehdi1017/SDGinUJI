package es.uji.ei1027.clubesportiu.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ActionParticipation {

    private String nameAct;
    private String nameIni  ;
    private String mail;
    private StatEnum stat;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String commentary;

    public ActionParticipation() {
    }


    public String getNameAct() {
        return nameAct;
    }

    public void setNameAct(String nameAct) {
        this.nameAct = nameAct;
    }

    public String getNameIni() {
        return nameIni;
    }

    public void setNameIni(String nameIni) {
        this.nameIni = nameIni;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public StatEnum getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = StatEnum.valueOf(stat);
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

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public String toString() {
        return "ActionParticipation{" +
                "nameAct='" + nameAct + '\'' +
                ", nameIni='" + nameIni + '\'' +
                ", mail='" + mail + '\'' +
                ", stat=" + stat +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", commentary='" + commentary + '\'' +
                '}';
    }
}
