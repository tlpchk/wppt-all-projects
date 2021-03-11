package main;

import javafx.beans.property.SimpleStringProperty;

public class Password{
    private SimpleStringProperty Login;
    private SimpleStringProperty Password;


    public String getLogin() {
        return Login.get();
    }

    public void setLogin(String Login) {
        this.Login.set(Login);
    }

    public String getPassword() {
        return Password.get();
    }

    public void setPassword(String name) {
        this.Password.set(name);
    }

    public Password(String Login, String Password) {
        this.Login = new SimpleStringProperty( Login );
        this.Password = new SimpleStringProperty( Password );
    }
}
