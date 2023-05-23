package es.uji.ei1027.clubesportiu.dao.ods;

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
            return jdbcTemplate.queryForObject("SELECT * from ods WHERE name_ods=? ",
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
                    "SELECT * FROM ods ORDER BY ind",
                    new OdsRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Ods>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addOds(Ods ods) {
        jdbcTemplate.update(
                "INSERT INTO ods VALUES(?, ?, CAST(? AS sector_enum), ?, ?)",
                ods.getNameOds(),
                ods.getRelevance(),
                ods.getAxis(),
                ods.getDescription(),
                ods.getIndex());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void deleteOds(String nOds) {
        jdbcTemplate.update("DELETE FROM ods WHERE name_ods = ? ",
                    nOds);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void updateOds(Ods ods) {
        jdbcTemplate.update("UPDATE ods " +
                        "SET    relevance = ? ," +
                        "       axis = CAST(? AS sector_enum) ," +
                        "       description = ?, " +
                        "       ind = ? " +
                        "WHERE  name_ods = ? ",
                ods.getRelevance(),
                ods.getAxis(),
                ods.getDescription(),
                ods.getIndex(),
                ods.getNameOds());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
}
