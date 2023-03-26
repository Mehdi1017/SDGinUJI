package es.uji.ei1027.clubesportiu.model;

import java.time.LocalDate;

public class Action {
    private String nameAction;
    private String nameInitiative;
    private String nameOds;
    private String nameTarget;
    private LocalDate creationDate;
    private LocalDate endDate;
    private String description;
    private Float progress;


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

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
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
                ", description='" + description + '\'' +
                ", progress=" + progress +
                '}';
    }
}
