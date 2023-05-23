package es.uji.ei1027.clubesportiu.dao.initiative_participation;

import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InitiativeParticipationRowMapper implements RowMapper<InitiativeParticipation> {
    @Override
    public InitiativeParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
        InitiativeParticipation initiativeParticipation = new InitiativeParticipation();
        initiativeParticipation.setMail(rs.getString("mail"));
        initiativeParticipation.setNameIni(rs.getString("name_ini"));
        initiativeParticipation.setRequestMessage(rs.getString("request_message"));
        initiativeParticipation.setCreationDate(rs.getObject("creation_date", LocalDate.class));
        initiativeParticipation.setStat(rs.getString("stat"));
        initiativeParticipation.setStartDate(rs.getObject("startdate", LocalDate.class));
        initiativeParticipation.setEndDate(rs.getObject("enddate", LocalDate.class));
        return initiativeParticipation;
    }
}
