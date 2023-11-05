package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.Identification;
import fr.insa.idmont.ProjetM3.views.InterfacePrinc;
import fr.insa.idmont.ProjetM3.views.NewUserform;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
   

    public Optional<Utilisateur> TestiD(String username,String pw) throws SQLException {
        Connection con = this.viewLog.getMain().getInfoSess().getCon();
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username = ? and password = ?")) {
            pst.setString(1, username);
            pst.setString(2, pw);
            ResultSet res = pst.executeQuery();
            if (res.next()) {              
                return Optional.of(new Utilisateur(res.getInt(1),username,pw,Autorisation.valueOf(res.getString(4)))) ;
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            return Optional.empty();
        }
    }
    public int getID(String username,String pw) throws SQLException {
        Connection con = this.viewSig.getMain().getInfoSess().getCon();
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username = ? and password = ?")) {
            pst.setString(1, username);
            pst.setString(2, pw);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }
    
    public boolean CreationCompte(String username,String pw) throws SQLException{
        Connection con = this.viewSig.getMain().getInfoSess().getCon();
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Identifiant (username,password,autorisation)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setString(1, username);
            pt.setString(2, pw);
            pt.setString(3, "CONSULTATION");
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
        Connection con = this.viewSig.getMain().getInfoSess().getCon();
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
    
    public void goMainContentLog(){
        this.viewLog.getMain().removeAll();
        this.viewLog.getMain().add(new InterfacePrinc(this.viewLog.getMain()));
    }
    
    public void goMainContentSig(){
        this.viewSig.getMain().removeAll();
        this.viewSig.getMain().add(new InterfacePrinc(this.viewSig.getMain()));
    }

}
