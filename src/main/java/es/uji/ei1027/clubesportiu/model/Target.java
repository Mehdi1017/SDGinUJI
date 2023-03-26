package es.uji.ei1027.clubesportiu.model;

import java.time.LocalDate;

public class Target {
    private String nameOds;
    private String nameTarg;
    private String description;

    public String getNameOds() {
        return nameOds;
    }

    public void setNameOds(String nameOds) {
        this.nameOds = nameOds;
    }

    public String getNameTarg() {
        return nameTarg;
    }

    public void setNameTarg(String nameTarg) {
        this.nameTarg = nameTarg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Target{" +
                "nameOds='" + nameOds + '\'' +
                ", nameTarg='" + nameTarg + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
