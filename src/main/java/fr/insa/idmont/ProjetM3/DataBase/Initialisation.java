package fr.insa.idmont.ProjetM3.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class Initialisation {

    public static boolean pilotCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static Connection connectionMySQLdirect() {
        try {
            String host = "92.222.25.165";
            String db = "m3_cidmont01";
            String user = "m3_cidmont01";
            int port = 3306;
            String pw = "52686b49";
            Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pw);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            System.out.println("Réussite de la connexcion");
            return con;
        } catch (SQLException ex) {
            System.out.println("Echec de la connexcion");
            return null;
        }
    }

}
