package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActionParticipationDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public ActionParticipation getActionParticipation(ActionParticipation actionParticipation) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from action_participation WHERE name_act=? AND name_ini=? AND mail=?",
                    new ActionParticipationRowMapper(),
                    actionParticipation.getNameAct(),
                    actionParticipation.getNameIni(),
                    actionParticipation.getMail());
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<ActionParticipation> getAllActionParticipation() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM action_participation",
                    new ActionParticipationRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<ActionParticipation>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addActionParticipation(ActionParticipation actionParticipation) {
        jdbcTemplate.update(
                "INSERT INTO action_participation VALUES(?, ?, ?, ?, CAST(? AS stat_enum), ?, ?, ?, ?)",
                actionParticipation.getNameAct(),
                actionParticipation.getNameIni(),
                actionParticipation.getMail(),
                actionParticipation.getStat().name(),
                actionParticipation.getStartDate(),
                actionParticipation.getEndDate(),
                actionParticipation.getCommentary());
    }


    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
     //TODO delete
        public void deleteActionParticipation(ActionParticipation actionParticipation) {
            jdbcTemplate.update("DELETE FROM action_participation WHERE name_ini = ? AND name_act=? AND mail=?",
                    actionParticipation.getNameIni(),
                    actionParticipation.getNameAct(),
                    actionParticipation.getMail());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------
     //TODO update
        public void updateActionParticipation(ActionParticipation actionParticipation) {
            jdbcTemplate.update("UPDATE action_participation " +
                            "SET " +
                            "       stat = ? ," +
                            "       startDate = ? ," +
                            "       enddate = ? ," +
                            "       commentary = ? ," +
                            "WHERE  name_act = ? AND name_ini=? AND mail = ?",
                    actionParticipation.getStat().name(),
                    actionParticipation.getStartDate(),
                    actionParticipation.getEndDate(),
                    actionParticipation.getCommentary(),
                    actionParticipation.getNameAct(),
                    actionParticipation.getNameIni(),
                    actionParticipation.getMail());
        }
     //-----------------------------------------------------------------------------------------------------------------
     //-----------------------------------------------------------------------------------------------------------------


}
