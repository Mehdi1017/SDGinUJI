package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
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

    /*public Initiative getMytiative(String ) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from initiative WHERE name_ini=?",
                    new InitiativeRowMapper(),
                    nameIni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }*/

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
                "INSERT INTO initiative VALUES(?, ?, ?, ?, 'Pending', ?, 0.0, ?, ?)",
                initiative.getNameIni(),
                initiative.getDescription(),
                initiative.getStartDate(),
                initiative.getEndDate(),
                initiative.getStartDate(),
                initiative.getMail(),
                initiative.getNameOds());
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
     //TODO delete
        public void deleteInitiative(String nameIni) {
            jdbcTemplate.update("DELETE FROM initiative WHERE name_ini = ? ",
                    nameIni);
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
                            "       stat = CAST(? AS stat_enum) ," +
                            "       lastmodified = ? ," +
                            "       progress = ? ," +
                            "       mail = ? ," +
                            "       name_ods = ?" +
                            "WHERE  name_ini = ? ",
                    initiative.getDescription(),
                    initiative.getStartDate(),
                    initiative.getEndDate(),
                    initiative.getStat(),
                    LocalDate.now(),
                    initiative.getProgress(),
                    initiative.getMail(),
                    initiative.getNameOds(),
                    initiative.getNameIni());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------


}
