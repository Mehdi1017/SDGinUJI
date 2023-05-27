package es.uji.ei1027.clubesportiu.dao.initiative_participation;

import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InitiativeParticipationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public InitiativeParticipation getInitiativeParticipation(String mail, String nameIni) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from initiative_participation WHERE mail=? AND name_ini=?",
                    new InitiativeParticipationRowMapper(),
                    mail,
                    nameIni);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<InitiativeParticipation> getAllInitiativeParticipations(String nameIni) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative_participation " +
                            "WHERE name_ini = ?",
                    new InitiativeParticipationRowMapper(),
                    nameIni);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<InitiativeParticipation>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addInitiativeParticipation(InitiativeParticipation initiativeParticipation) {
        jdbcTemplate.update(
                "INSERT INTO initiative_participation VALUES(?, ?, ?, ?, 'Approved', ?, ?)",
                initiativeParticipation.getMail(),
                initiativeParticipation.getNameIni(),
                initiativeParticipation.getRequestMessage(),
                LocalDate.now(),
                initiativeParticipation.getStartDate(),
                initiativeParticipation.getEndDate());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

        public void deleteInitiativeParticipation(InitiativeParticipation participation) {
            System.out.println("se borra");
            jdbcTemplate.update("DELETE FROM initiative_participation WHERE mail = ? AND name_ini = ?",
                    participation.getMail(),
                    participation.getNameIni());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------

        public void updateInitiativeParticipation(InitiativeParticipation initiativeParticipation) {
            jdbcTemplate.update("UPDATE initiative_participation " +
                            "SET " +
                            "       request_message = ? ," +
                            "       creation_date = ? ," +
                            "       stat = CAST(? AS stat_enum) ," +
                            "       startdate = ? ," +
                            "       enddate = ? " +
                            "WHERE  mail = ? AND " +
                            "       name_ini = ? ",
                    initiativeParticipation.getRequestMessage(),
                    initiativeParticipation.getCreationDate(),
                    initiativeParticipation.getStat(),
                    initiativeParticipation.getStartDate(),
                    initiativeParticipation.getEndDate(),
                    initiativeParticipation.getMail(),
                    initiativeParticipation.getNameIni());
        }

     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------

}
