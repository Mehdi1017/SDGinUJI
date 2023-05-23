package es.uji.ei1027.clubesportiu.external_services;

public interface MailManager {
    void sendMail(String mail, String header, String message);
}
