/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.idmont.bdbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class Initialisation {
     public static void pilotCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Pilote trouvé");
        } catch (ClassNotFoundException ex) {
            throw new Error("Pilote introuvable");
        }
    }

    public static Connection connectionMySQL(String host, int port,
            String db, String user, String pw) {
        try {
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
