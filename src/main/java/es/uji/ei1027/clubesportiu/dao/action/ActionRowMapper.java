package es.uji.ei1027.clubesportiu.dao.action;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActionRowMapper implements RowMapper<Action> {
    @Override
    public Action mapRow(ResultSet rs, int rowNum) throws SQLException {
        Action action = new Action();
        action.setNameAction(rs.getString("name_act"));
        action.setNameInitiative(rs.getString("name_ini"));
        action.setNameOds(rs.getString("name_ods"));
        action.setNameTarget(rs.getString("name_targ"));
        action.setCreationDate(rs.getObject("creation_date", LocalDate.class));
        action.setEndDate(rs.getObject("end_date", LocalDate.class));
        action.setDescription(rs.getString("description"));
        action.setProgress(rs.getString("progress"));
        action.setResultados(rs.getString("resultados"));
        action.setValoracion(rs.getString("valoracion"));
        action.setStat(rs.getString("stat"));
        return action;
    }
}