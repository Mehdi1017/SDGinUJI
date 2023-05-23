package es.uji.ei1027.clubesportiu.dao.initiative;

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

    public List<Initiative> getMyInitiative(String mail) {
        try {
            return jdbcTemplate.query("SELECT * from initiative AS i " +
                                            "LEFT JOIN initiative_participation AS p USING(name_ini) " +
                                            "WHERE ? IN (p.mail, i.mail)",
                    new InitiativeRowMapper(),
                    mail);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Initiative>();
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

    public List<Initiative> getPendingInitiatives() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE stat = 'Pending' ",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Initiative>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addInitiativeNaif(Initiative initiative) {
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

    public void addInitiative(Initiative initiative) {
        jdbcTemplate.update(
                "INSERT INTO initiative VALUES(?, ?, null, null, 'Pending', ?, 0.0, ?, ?)",
                initiative.getNameIni(),
                initiative.getDescription(),
                LocalDate.now(),
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