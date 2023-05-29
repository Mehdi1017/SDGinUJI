package es.uji.ei1027.clubesportiu.services;

import org.springframework.stereotype.Service;

@Service
public class FakeMailManager implements MailManager {
    @Override
    public void sendMail(String mail, String header, String message) {
        System.out.println("****************** PRINTING MAIL ******************");
        System.out.println("<<" + mail + ">> inbox:");
        System.out.println(header);
        System.out.println(message);
        System.out.println("***************************************************");
    }
}
