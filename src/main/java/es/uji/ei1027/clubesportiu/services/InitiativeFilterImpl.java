package es.uji.ei1027.clubesportiu.services;

import es.uji.ei1027.clubesportiu.dao.action.ActionDao;
import es.uji.ei1027.clubesportiu.dao.initiative.InitiativeDao;
import es.uji.ei1027.clubesportiu.dao.ods.OdsDao;
import es.uji.ei1027.clubesportiu.dao.target.TargetDao;
import es.uji.ei1027.clubesportiu.model.Action;
import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InitiativeFilterImpl implements InitiativeFilter{

    @Autowired
    InitiativeDao initiativeDao;

    @Autowired
    OdsDao odsDao;

    @Autowired
    TargetDao targetDao;

    @Autowired
    ActionDao actionDao;


    @Override
    public Map<String, List<Initiative>> getActualInitiativesByODS() {

        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByODS =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            if (ini.getStat().equals("Approved")){
                List<Initiative> listaIni = InitiativesByODS.computeIfAbsent(ini.getNameOds(), k -> new LinkedList<>());
                listaIni.add(ini);
            }
        }

        return InitiativesByODS;
    }

    @Override
    public Map<String, List<Initiative>> getActualInitiativesByTarget() {
        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByTarget =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){

            for (Action a: actionDao.getActions(ini.getNameIni())){
                List<Initiative> listaIni = InitiativesByTarget.computeIfAbsent(a.getNameTarget(), k -> new LinkedList<>());

                if (ini.getStat().equals("Approved")){
                    listaIni.add(ini);
                }
            }
        }
        return InitiativesByTarget;
    }

    @Override
    public Map<String, List<Initiative>> getEndedInitiativesByODS() {

        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByODS =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            if (ini.getStat().equals("Ended")){
                List<Initiative> listaIni = InitiativesByODS.computeIfAbsent(ini.getNameOds(), k -> new LinkedList<>());
                listaIni.add(ini);
            }
        }

        return InitiativesByODS;
    }

    @Override
    public Map<String, List<Initiative>> getEndedInitiativesByTarget() {
        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByTarget =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            for (Action a: actionDao.getActions(ini.getNameIni())){
                List<Initiative> listaIni = InitiativesByTarget.computeIfAbsent(a.getNameTarget(), k -> new LinkedList<>());

                if (ini.getStat().equals("Ended")){
                    listaIni.add(ini);
                }
            }
        }
        return InitiativesByTarget;
    }

    @Override
    public Map<String, List<Initiative>> getRejectedInitiativesByTarget() {
        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByTarget =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            for (Action a: actionDao.getActions(ini.getNameIni())){
                List<Initiative> listaIni = InitiativesByTarget.computeIfAbsent(a.getNameTarget(), k -> new LinkedList<>());

                if (ini.getStat().equals("Rejected")){
                    listaIni.add(ini);
                }
            }
        }
        return InitiativesByTarget;
    }

    @Override
    public Map<String, List<Initiative>> getPendingInitiativesByOds() {
        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByODS =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            if (ini.getStat().equals("Pending")){
                List<Initiative> listaIni = InitiativesByODS.computeIfAbsent(ini.getNameOds(), k -> new LinkedList<>());
                listaIni.add(ini);
            }
        }

        return InitiativesByODS;
    }

    @Override
    public Map<String, List<Initiative>> getPendingInitiativesByTarget() {
        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByTarget =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            for (Action a: actionDao.getActions(ini.getNameIni())){
                List<Initiative> listaIni = InitiativesByTarget.computeIfAbsent(a.getNameTarget(), k -> new LinkedList<>());

                if (ini.getStat().equals("Pending")){
                    listaIni.add(ini);
                }
            }
        }
        return InitiativesByTarget;
    }

    @Override
    public Map<String, List<Initiative>> getRejectedInitiativesByOds() {

        List<Initiative> allInitiative =
                initiativeDao.getAllInitiative();

        HashMap<String,List<Initiative>> InitiativesByODS =
                new HashMap<String,List<Initiative>>();

        for (Initiative ini : allInitiative){
            if (ini.getStat().equals("Rejected")){
                List<Initiative> listaIni = InitiativesByODS.computeIfAbsent(ini.getNameOds(), k -> new LinkedList<>());
                listaIni.add(ini);
            }
        }

        return InitiativesByODS;
    }
}
