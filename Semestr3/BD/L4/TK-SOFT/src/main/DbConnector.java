package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {

    public Connection connect() {
        String username = "root";
        String password = "00000000";
        String conectionURL = "jdbc:mysql://localhost/tk-soft";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = (DriverManager.getConnection(conectionURL, username, password));
            return connection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
