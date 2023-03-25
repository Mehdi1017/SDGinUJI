package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Ods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OdsDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public Ods getOds(String nameOds) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from ods WHERE name_ods=?",
                    new OdsRowMapper(),
                    nameOds);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<Ods> getAllOds() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM ods",
                    new OdsRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ods>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------



    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

}
