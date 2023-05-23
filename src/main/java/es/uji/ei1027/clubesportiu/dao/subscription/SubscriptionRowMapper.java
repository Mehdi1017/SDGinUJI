package es.uji.ei1027.clubesportiu.dao.subscription;

import es.uji.ei1027.clubesportiu.model.Subscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SubscriptionRowMapper implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subscription subscription = new Subscription();
        subscription.setMail(rs.getString("mail"));
        subscription.setIdSub(rs.getInt("id_sub"));
        subscription.setNameOds(rs.getString("name_ods"));
        subscription.setInitialDate(rs.getObject("initialdate", LocalDate.class));
        subscription.setEndDate(rs.getObject("enddate", LocalDate.class));
        return subscription;
    }
}
