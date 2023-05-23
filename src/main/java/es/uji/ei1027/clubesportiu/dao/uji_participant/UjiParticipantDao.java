package es.uji.ei1027.clubesportiu.dao.uji_participant;

import es.uji.ei1027.clubesportiu.model.UjiParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UjiParticipantDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public UjiParticipant getUjiParticipant(String mail) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from uji_participant WHERE mail=?",
                    new UjiParticipantRowMapper(),
                    mail);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<UjiParticipant> getAllUjiParticipants() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM uji_participant",
                    new UjiParticipantRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<UjiParticipant>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addUjiParticipant(UjiParticipant ujiParticipant) {
        jdbcTemplate.update(
                "INSERT INTO uji_participant VALUES(?, ?, CAST(? AS type_enum), CAST(? AS gender_enum), ?)",
                ujiParticipant.getMail(),
                ujiParticipant.getNameMem(),
                ujiParticipant.getUsrType(),
                ujiParticipant.getGender(),
                ujiParticipant.getPhone());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void deleteUjiParticipant(String mail) {
        jdbcTemplate.update("DELETE FROM uji_participant WHERE mail = ? ",
                    mail);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void updateUjiParticipant(UjiParticipant ujiParticipant) {
        jdbcTemplate.update("UPDATE uji_participant " +
                        "SET    name_mem = ? ," +
                        "       usr_type = CAST(? AS type_enum) ," +
                        "       gender = CAST(? AS gender_enum) ," +
                        "       phone = ? " +
                        "WHERE  mail = ? ",
                ujiParticipant.getNameMem(),
                ujiParticipant.getUsrType(),
                ujiParticipant.getGender(),
                ujiParticipant.getPhone(),
                ujiParticipant.getMail());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

}
