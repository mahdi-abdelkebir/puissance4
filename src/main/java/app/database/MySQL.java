package app.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQL {
    private static Connection cnx;
    
    public static void connect(){
		
		String serveurBD = "jdbc:mysql://localhost:3306/puissance4";
		String login = "root";
		String motPasse = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            cnx = DriverManager.getConnection(serveurBD, login, motPasse);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    // Static method
    // Static method to create instance of Singleton class
    public static Connection getConnection() {
        return cnx;
    }
}
