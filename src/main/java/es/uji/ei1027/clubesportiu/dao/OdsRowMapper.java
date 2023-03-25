package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Ods;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OdsRowMapper implements RowMapper<Ods> {
    @Override
    public Ods mapRow(ResultSet rs, int rowNum) throws SQLException {
        Ods ods = new Ods();
        ods.setNameOds(rs.getString("name_ods"));
        ods.setRelevance(rs.getInt("relevance"));
        ods.setDescription(rs.getString("description"));
        ods.setAxis(rs.getString("axis"));
        return ods;
    }
}
