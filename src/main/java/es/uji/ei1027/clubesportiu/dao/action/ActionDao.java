package es.uji.ei1027.clubesportiu.dao.action;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public Action getAction(String nameAction, String nameInitiative) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Action WHERE name_act=? AND name_ini=?",
                    new ActionRowMapper(),
                    nameAction,
                    nameInitiative);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<Action> getAllAction() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Action",
                    new ActionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addActionn(Action action) {
        jdbcTemplate.update(
                "INSERT INTO Action VALUES(?, ?, ?,?,?,?,?)",
                action.getNameAction(),
                action.getNameInitiative(),
                action.getNameOds(),
                action.getNameTarget(),
                action.getCreationDate(),
                action.getEndDate(),
                action.getDescription());
    }


    // -----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    public void deleteAction(Action action) {
        jdbcTemplate.update("DELETE FROM Action WHERE name_act=? AND name_ini=? ",
                action.getNameAction(), action.getNameInitiative());
    }
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    public void updateAction(Action action) {
        jdbcTemplate.update("UPDATE Action " +
                        "SET    name_ods = ? ," +
                        "name_targ = ? ," +
                        "creation_date = ? ," +
                        "end_ate = ? ," +
                        "description = ? ," +
                        "resultados = ? ," +
                        "valoracion = ? ," +
                        "progress = ? " +
                        "WHERE  name_act = ?",
                action.getNameOds(),
                action.getNameTarget(),
                action.getCreationDate(),
                action.getEndDate(),
                action.getDescription(),
                action.getResultados(),
                action.getValoracion(),
                action.getProgress(),
                action.getNameAction());
    }
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------


}
