package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SubscriptionDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public Subscription getSubscription(int idSub) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from subscription WHERE id_sub=?",
                    new SubscriptionRowMapper(),
                    idSub);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public List<Subscription> getAllSubscription() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM subscription",
                    new SubscriptionRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<Subscription>();
        }
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    public void addSubscription(Subscription subscription) {
        jdbcTemplate.update(
                "INSERT INTO subscription VALUES(?, ?, ?, ?, ?)",
                subscription.getMail(),
                subscription.getIdSub(),
                subscription.getNameOds(),
                subscription.getInitialDate(),
                subscription.getEndDate());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // TODO delete
//    public void deleteNadador(Nadador nadador) {
//        jdbcTemplate.update("DELETE FROM Nadador WHERE nom = ? ",
//                nadador.getNom());
//    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------
    // TODO update
//    public void updateNadador(Nadador nadador) {
//        jdbcTemplate.update("UPDATE Nadador " +
//                        "SET    num_federat = ? ," +
//                        "       edat = ? ," +
//                        "       pais = ? ," +
//                        "       genere = ?" +
//                        "WHERE  nom = ? ",
//                nadador.getNumFederat(),
//                nadador.getEdat(),
//                nadador.getPais(),
//                nadador.getGenere(),
//                nadador.getNom());
//    }

    // -----------------------------------------------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

}
