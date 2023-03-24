package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.ODS;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

public class ODSRowMapper implements RowMapper<ODS> {
    @Override
    public ODS mapRow(ResultSet rs, int rowNum) throws SQLException {
        ODS ods = new ODS();
        ods.setNameOds(rs.getString("name_ods"));
        ods.setRelevance(rs.getInt("relevance"));
        ods.setDescription(rs.getString("description"));
        ods.setAxis(rs.getString("axis"));
        return ods;
    }
}
