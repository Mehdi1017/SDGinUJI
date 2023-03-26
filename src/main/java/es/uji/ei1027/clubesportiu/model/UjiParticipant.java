package es.uji.ei1027.clubesportiu.model;

public class UjiParticipant {

    private String mail;
    private String nameMem;
    private String usrType;
    private String gender;
    private String phone;

    public UjiParticipant() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNameMem() {
        return nameMem;
    }

    public void setNameMem(String nameMem) {
        this.nameMem = nameMem;
    }

    public String getUsrType() {
        return usrType;
    }

    public void setUsrType(String usrType) {
        this.usrType = usrType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UjiParticipant{" +
                "mail='" + mail + '\'' +
                ", nameMem='" + nameMem + '\'' +
                ", usrType='" + usrType + '\'' +
                ", gender='" + gender + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
