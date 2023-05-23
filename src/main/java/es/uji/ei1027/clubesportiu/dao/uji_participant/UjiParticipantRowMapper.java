package es.uji.ei1027.clubesportiu.dao.uji_participant;

import es.uji.ei1027.clubesportiu.model.UjiParticipant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UjiParticipantRowMapper implements RowMapper<UjiParticipant> {
    @Override
    public UjiParticipant mapRow(ResultSet rs, int rowNum) throws SQLException {
        UjiParticipant ujiParticipant = new UjiParticipant();
        ujiParticipant.setMail(rs.getString("mail"));
        ujiParticipant.setNameMem(rs.getString("name_mem"));
        ujiParticipant.setUsrType(rs.getString("usr_type"));
        ujiParticipant.setGender(rs.getString("gender"));
        ujiParticipant.setPhone(rs.getString("phone"));
        return ujiParticipant;
    }
}