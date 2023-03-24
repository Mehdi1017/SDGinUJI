package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.ODS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ODSDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /* Obté la ods amb el nom donat. Torna null si no existeix. */
    public ODS getODS(String nameOds) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from ods WHERE name_ods=?",
                    new ODSRowMapper(), nameOds);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

}
