package es.uji.ei1027.clubesportiu.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.stereotype.Repository;
import es.uji.ei1027.clubesportiu.model.UserDetails;

@Repository
public class FakeUserProvider implements UserDao {
    final Map<String, UserDetails> knownUsers = new HashMap<String, UserDetails>();

    public FakeUserProvider() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        UserDetails userFrancesc = new UserDetails();
        
        // usuario UJI member
        userFrancesc.setUsername("francesc");
        userFrancesc.setMail("francesc@gmail.com");
        userFrancesc.setPassword(passwordEncryptor.encryptPassword("francesc"));
        userFrancesc.setIsAdmin(false);
        knownUsers.put("francesc", userFrancesc);

        // usuario OCDS staff
        UserDetails userMaria = new UserDetails();
        userMaria.setUsername("maria");
        userMaria.setMail("maria@gmail.com");
        userMaria.setPassword(passwordEncryptor.encryptPassword("maria"));
        userMaria.setIsAdmin(true);
        knownUsers.put("maria", userMaria);
    }

    @Override
    public UserDetails loadUserByUsername(String username, String password) {
        UserDetails user = knownUsers.get(username.trim());
        if (user == null)
            return null; // Usuari no trobat
        // Contrasenya
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (passwordEncryptor.checkPassword(password, user.getPassword())) {
            // Es deuria esborrar de manera segura el camp password abans de tornar-lo
            return user;
        }
        else {
            return null; // bad login!
        }
    }

    @Override
    public Collection<UserDetails> listAllUsers() {
        return knownUsers.values();
    }
}