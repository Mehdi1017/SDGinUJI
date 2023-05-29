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
            return jdbcTemplate.query("SELECT DISTINCT i.* from initiative AS i " +
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

    public List<Initiative> getAllActualInitiative() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE stat = 'Approved'",
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
                "INSERT INTO initiative (name_ini, description, startdate, enddate, mail, name_ods, motivacion, url)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                initiative.getNameIni(),
                initiative.getDescription(),
                initiative.getStartDate(),
                initiative.getEndDate(),
                initiative.getMail(),
                initiative.getNameOds(),
                initiative.getMotivacion(),
                initiative.getUrl());
    }

    public void addInitiative(Initiative initiative) {
        jdbcTemplate.update(
                "INSERT INTO initiative (name_ini, description, startdate, enddate, mail, name_ods, motivacion, url)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                initiative.getNameIni(),
                initiative.getDescription(),
                initiative.getStartDate(),
                initiative.getEndDate(),
                initiative.getMail(),
                initiative.getNameOds(),
                initiative.getMotivacion(),
                initiative.getUrl());
    }



    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
     //TODO delete
        public void deleteInitiative(String nameIni) {
            jdbcTemplate.update("UPDATE initiative " +
                            "SET " +
                            "stat = 'Ended'" +
                            " WHERE name_ini = ? ",
                    nameIni);
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------
     //TODO update
        public void updateInitiative(Initiative initiative) {
            //System.out.println(initiative.getMail()+initiative.getResultados()+ initiative.getNameIni());
            jdbcTemplate.update("UPDATE initiative " +
                            "SET " +
                            "       description = ? ," +
                            "       startDate = ? ," +
                            "       enddate = ? ," +
                            "       stat = CAST(? AS stat_enum)," +
                            "       lastmodified = ? ," +
                            "       progress = ? ," +
                            "       mail = ? ," +
                            "       name_ods = ?," +
                            "       motivacion = ?," +
                            "       url = ?, " +
                            "resultados = ? " +
                            "WHERE  name_ini = ? ",
                    initiative.getDescription(),
                    initiative.getStartDate(),
                    initiative.getEndDate(),
                    initiative.getStat(),
                    LocalDate.now(),
                    initiative.getProgress(),
                    initiative.getMail(),
                    initiative.getNameOds(),
                    initiative.getMotivacion(),
                    initiative.getUrl(),
                    initiative.getResultados(),
                    initiative.getNameIni());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------


}
