package es.uji.ei1027.clubesportiu.dao.target;

import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TargetRowMapper implements RowMapper<Target> {
    @Override
    public Target mapRow(ResultSet rs, int rowNum) throws SQLException {
        Target target = new Target();
        target.setNameOds(rs.getString("name_ods"));
        target.setNameTarg(rs.getString("name_targ"));
        target.setDescription(rs.getString("description"));
        return target;
    }
}