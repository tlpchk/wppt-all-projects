package main;

import javafx.beans.property.SimpleStringProperty;

public class Client{
    private SimpleStringProperty mail;
    private SimpleStringProperty name;
    private SimpleStringProperty surname;
    private int number;

    public String getMail() {
        return mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Client(String mail, String name, String surname, int number) {
        this.mail = new SimpleStringProperty( mail );
        this.name = new SimpleStringProperty( name );
        this.surname = new SimpleStringProperty( surname );
        this.number = number;
    }
}
