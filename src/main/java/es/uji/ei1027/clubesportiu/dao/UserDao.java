package es.uji.ei1027.clubesportiu.dao;

import java.util.Collection;
import es.uji.ei1027.clubesportiu.model.UserDetails;

public interface UserDao {
    UserDetails loadUserByUsername(String username, String password);
    Collection<UserDetails> listAllUsers();
}
