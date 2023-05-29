package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Action {
    private String nameAction;
    private String nameInitiative;
    private String nameOds;
    private String nameTarget;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate creationDate;
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
    private LocalDate endDate;
    private String description;
    private String progress;
    private String resultados;
    private String valoracion;
    private String stat;


    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getNameAction() {
        return nameAction;
    }

    public void setNameAction(String nameAction) {
        this.nameAction = nameAction;
    }

    public String getNameInitiative() {
        return nameInitiative;
    }

    public void setNameInitiative(String nameInitiative) {
        this.nameInitiative = nameInitiative;
    }

    public String getNameOds() {
        return nameOds;
    }

    public void setNameOds(String nameOds) {
        this.nameOds = nameOds;
    }

    public String getNameTarget() {
        return nameTarget;
    }

    public void setNameTarget(String nameTarget) {
        this.nameTarget = nameTarget;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String toString() {
        return "Action{" +
                "nameAction='" + nameAction + '\'' +
                ", nameInitiative='" + nameInitiative + '\'' +
                ", nameOds='" + nameOds + '\'' +
                ", nameTarget='" + nameTarget + '\'' +
                ", creationDate=" + creationDate +
                ", endDate=" + endDate +
                ", stat=" + stat +
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';
    }

    public String getResultados() {
        return resultados;
    }

    public void setResultados(String resultados) {
        this.resultados = resultados;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }
}
