package es.uji.ei1027.clubesportiu.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Initiative {

    private String nameIni;
    private String description;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private String stat;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate lastModified;
    private double progress;
    private String mail;
    private String nameOds;

    public Initiative() {
    }


    public String getNameIni() {
        return nameIni;
    }

    public void setNameIni(String nameIni) {
        this.nameIni = nameIni;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameOds() {
        return nameOds;
    }

    public void setNameOds(String nameOds) {
        this.nameOds = nameOds;
    }

    @Override
    public String toString() {
        return "Initiative{" +
                "nameIni='" + nameIni + '\'' +
                ", description=" + description +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", stat=" + stat +
                ", lastModified=" + lastModified +
                ", progress=" + progress +
                ", mail='" + mail + '\'' +
                ", nameOds='" + nameOds + '\'' +
                '}';
    }
}
