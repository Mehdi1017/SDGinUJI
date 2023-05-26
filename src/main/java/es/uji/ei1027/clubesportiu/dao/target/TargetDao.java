package es.uji.ei1027.clubesportiu.dao.target;

import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TargetDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public Target getTarget(String nameOds, String nameTarg) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Target WHERE name_ods=? AND name_targ=?",
                    new TargetRowMapper(),
                    nameOds,
                    nameTarg);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<Target> getAllTarget() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Target",
                    new TargetRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Target>();
        }
    }

    public List<Target> getTargetByOds(String nODS) {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM Target WHERE name_ods=?",
                    new TargetRowMapper(),
                    nODS);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Target>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addTarget(Target target) {
        jdbcTemplate.update(
                "INSERT INTO Target VALUES(?, ?, ?)",
                target.getNameOds(),
                target.getNameTarg(),
                target.getDescription());
    }


    // -----------------------------------------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------------------------------------
    //TODO delete
    public void deleteTarget(Target target) {
        jdbcTemplate.update("DELETE FROM Target WHERE name_ods=? AND name_targ=? ",
                target.getNameOds(), target.getNameTarg());
    }
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // TODO update
    public void updateTarget(Target target) {
        jdbcTemplate.update("UPDATE Target " +
                        "SET    description = ? " +
                        "WHERE  name_ods = ?"      +
                        "AND  name_targ = ?",
                target.getDescription(),
                target.getNameOds(),
                target.getNameTarg());
    }
    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------


}
