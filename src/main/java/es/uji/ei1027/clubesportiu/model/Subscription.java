package es.uji.ei1027.clubesportiu.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Subscription {

    private String mail;
    private Integer idSub;
    private String nameOds;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate initialDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endDate;

    public Subscription() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getIdSub() {
        return idSub;
    }

    public void setIdSub(Integer idSub) {
        this.idSub = idSub;
    }

    public String getNameOds() {
        return nameOds;
    }

    public void setNameOds(String nameOds) {
        this.nameOds = nameOds;
    }

    public LocalDate getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDate initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "mail='" + mail + '\'' +
                ", id_sub=" + idSub +
                ", name_ods='" + nameOds + '\'' +
                ", initialdate=" + initialDate +
                ", enddate=" + endDate +
                '}';
    }
}
