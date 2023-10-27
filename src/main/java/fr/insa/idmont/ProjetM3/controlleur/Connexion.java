package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.Identification;
import fr.insa.idmont.ProjetM3.views.NewUserform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class Connexion {

    Identification viewLog;
    NewUserform viewSig;
    
    public Connexion(Identification view) {
        this.viewLog = view;
    }
   public Connexion(NewUserform view) {
        this.viewSig = view;
    }

    public boolean TestiD(String username,String pw) throws SQLException {
        Connection con = this.viewLog.getMain().getCon();
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
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean CreationCompte(String username,String pw) throws SQLException{
        Connection con = this.viewSig.getMain().getCon();
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Identifiant (username,password)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setString(1, username);
            pt.setString(2, pw);
            pt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        } 
    }
    
    
    
    public void GotoLoginForm(){
       this.viewLog.getMain().removeAll();
       this.viewLog.getMain().add(new NewUserform(this.viewLog.getMain()));
    }

public boolean TestUsername(String username) throws SQLException {
        Connection con = this.viewSig.getMain().getCon();
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username = ?")) {
            pst.setString(1, username);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }   

    public void goBackIden(){
        this.viewSig.getMain().removeAll();
        this.viewSig.getMain().add(new Identification(this.viewSig.getMain()));
    }

}
