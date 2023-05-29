package es.uji.ei1027.clubesportiu.model;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Initiative {

    private String nameIni;
    private String description;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate startDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String stat;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate lastModified;
    private String progress;
    private String mail;
    private String nameOds;
    private String motivacion;
    private String url;
    private String resultados;

    // coleccion util
    private List<Action> actions;

    public Initiative() {
        actions = new ArrayList<>();
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
    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
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

    public String getMotivacion() {
        return motivacion;
    }

    public void setMotivacion(String motivacion) {
        this.motivacion = motivacion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
