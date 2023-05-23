package es.uji.ei1027.clubesportiu.dao.action_participation;

import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActionParticipationRowMapper implements RowMapper<ActionParticipation> {
    @Override
    public ActionParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActionParticipation actionParticipation = new ActionParticipation();
        actionParticipation.setNameIni(rs.getString("name_ini"));
        actionParticipation.setNameAct(rs.getString("name_ini"));
        actionParticipation.setStartDate(rs.getObject("startdate", LocalDate.class));
        actionParticipation.setEndDate(rs.getObject("enddate", LocalDate.class));
        actionParticipation.setStat(rs.getString("stat"));
        actionParticipation.setMail(rs.getString("mail"));
        actionParticipation.setCommentary(rs.getString("commentary"));
        return actionParticipation;
    }
}
