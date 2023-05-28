package es.uji.ei1027.clubesportiu.services;

import es.uji.ei1027.clubesportiu.model.Initiative;

import java.util.List;
import java.util.Map;

public interface InitiativeFilter {
    Map<String, List<Initiative>> getActualInitiativesByODS();
    Map<String, List<Initiative>> getActualInitiativesByTarget();

    Map<String, List<Initiative>> getEndedInitiativesByODS();
    Map<String, List<Initiative>> getEndedInitiativesByTarget();

    Map<String, List<Initiative>> getRejectedInitiativesByOds();
    Map<String, List<Initiative>> getRejectedInitiativesByTarget();

    Map<String, List<Initiative>> getPendingInitiativesByOds();
    Map<String, List<Initiative>> getPendingInitiativesByTarget();

}
