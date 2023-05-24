package es.uji.ei1027.clubesportiu.dao.initiative;

import es.uji.ei1027.clubesportiu.model.Initiative;
import es.uji.ei1027.clubesportiu.model.StatEnum;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InitiativeRowMapper implements RowMapper<Initiative> {
    @Override
    public Initiative mapRow(ResultSet rs, int rowNum) throws SQLException {
        Initiative initiative = new Initiative();
        initiative.setNameIni(rs.getString("name_ini"));
        initiative.setDescription(rs.getString("description"));
        initiative.setStartDate(rs.getObject("startdate", LocalDate.class));
        initiative.setEndDate(rs.getObject("enddate", LocalDate.class));
        initiative.setStat(rs.getString("stat"));
        initiative.setLastModified(rs.getObject("lastmodified", LocalDate.class));
        initiative.setProgress(rs.getString("progress"));
        initiative.setMail(rs.getString("mail"));
        initiative.setNameOds(rs.getString("name_ods"));
        initiative.setMotivacion(rs.getString("motivacion"));
        initiative.setUrl(rs.getString("url"));
        initiative.setResultados(rs.getString("resultados"));
        return initiative;
    }
}
