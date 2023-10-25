package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.Identification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author cidmo
 */
public class Connexion {

    Identification view;

    public Connexion(Identification view) {
        this.view = view;
    }

    public boolean TestiD() throws SQLException {
        String username = this.view.getUsername().getValue();
        String pw = this.view.getPwEntry().getValue();
        Connection con = this.view.getCon();
         try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"             
                + " where username = ? and password = ?")) {
            pst.setString(1, username);
            pst.setString(2, pw);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return true;
            } else {
                return false;
            }           
         }catch (SQLException ex) {
             return  false;
        }
    }

}
