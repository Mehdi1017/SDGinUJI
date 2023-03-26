package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InitiativeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public Initiative getInitiative(String nameIni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from initiative WHERE name_ini=?",
                    new InitiativeRowMapper(),
                    nameIni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<Initiative> getAllInitiative() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Initiative>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addInitiative(Initiative initiative) {
        jdbcTemplate.update(
                "INSERT INTO initiative VALUES(?, ?, ?, ?, CAST(? AS stat_enum), ?, ?, ?, ?)",
                initiative.getNameIni(),
                initiative.getDescription(),
                initiative.getStartDate(),
                initiative.getEndDate(),
                initiative.getStat().name(),
                initiative.getLastModified(),
                initiative.getProgress(),
                initiative.getMail(),
                initiative.getNameOds());
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
     //TODO delete
        public void deleteInitiative(Initiative initiative) {
            jdbcTemplate.update("DELETE FROM initiative WHERE nom_ini = ? ",
                    initiative.getNameIni());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------
     //TODO update
        public void updateInitiative(Initiative initiative) {
            jdbcTemplate.update("UPDATE initiative " +
                            "SET " +
                            "       description = ? ," +
                            "       startDate = ? ," +
                            "       enddate = ? ," +
                            "       stat = ? ," +
                            "       lastmodified = ? ," +
                            "       progress = ? ," +
                            "       mail = ? ," +
                            "       name_ods = ?" +
                            "WHERE  name_ini = ? ",
                    initiative.getDescription(),
                    initiative.getStartDate(),
                    initiative.getEndDate(),
                    initiative.getStat(),
                    initiative.getLastModified(),
                    initiative.getProgress(),
                    initiative.getMail(),
                    initiative.getNameOds(),
                    initiative.getNameIni());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------


}
