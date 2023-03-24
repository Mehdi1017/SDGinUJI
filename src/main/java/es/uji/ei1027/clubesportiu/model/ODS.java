package es.uji.ei1027.clubesportiu.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

public class ODS {

    private String nameOds;
    private Integer relevance;
    private String axis;
    private String description;

    public ODS() {
    }


    public String getNameOds() {
        return nameOds;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public String getAxis() {
        return axis;
    }

    public String getDescription() {
        return description;
    }

    public void setNameOds(String nameOds) {
        this.nameOds = nameOds;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }

    public void setAxis(String axis) {
        this.axis = axis;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ODS{" +
                "nameOds='" + nameOds + '\'' +
                ", relevance=" + relevance +
                ", axis=" + axis +
                ", description='" + description + '\'' +
                '}';
    }
}
