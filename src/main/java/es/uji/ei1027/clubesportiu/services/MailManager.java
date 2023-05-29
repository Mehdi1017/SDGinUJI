package es.uji.ei1027.clubesportiu.services;

public interface MailManager {
    void sendMail(String mail, String header, String message);
}
