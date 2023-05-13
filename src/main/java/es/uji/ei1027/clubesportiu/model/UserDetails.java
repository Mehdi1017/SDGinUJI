package es.uji.ei1027.clubesportiu.model;

public class UserDetails {
    Boolean isAdmin = false;
    String username;
    String password;
    String mail;

    public boolean isAdmin(){
        return isAdmin;
    }

    public void setIsAdmin(boolean isadmin){
        isAdmin = isadmin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
