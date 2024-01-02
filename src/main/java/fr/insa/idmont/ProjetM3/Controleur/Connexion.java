package fr.insa.idmont.ProjetM3.Controleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import fr.insa.idmont.ProjetM3.DataBase_Model.Autorisation;
import fr.insa.idmont.ProjetM3.View.Identification;
import fr.insa.idmont.ProjetM3.View.InterfacePrinc;
import fr.insa.idmont.ProjetM3.View.FirstConnexionForm;
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
    FirstConnexionForm viewSig;

    // Différents constructeurs des controleurs de la partie connexion :
    public Connexion(Identification view) {
        this.viewLog = view;
    }

    public Connexion(FirstConnexionForm view) {
        this.viewSig = view;
    }

    // Existence de l'utilisateur lors de la connexion et envoie de ce dernier
    public Optional<Utilisateur> TestIdentifiants(String username, String pw) throws SQLException {
        Connection con = this.viewLog.getMain().getInfoSess().getCon();
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username = ? and password = ?")) {
            pst.setString(1, username);
            pst.setString(2, pw);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return Optional.of(new Utilisateur(res.getInt(1), username, pw, Autorisation.valueOf(res.getString(4))));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            return Optional.empty();
        }
    }

    // Existence des pré-identifiants de l'utilisateur lors de la connexion et envoie de ce dernier
    public Optional<Utilisateur> TestPreIdentifiants(String username, String pw) throws SQLException {
        Connection con = this.viewSig.getMain().getInfoSess().getCon();
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from pre_connexion"
                + " where username = ? and password = ?")) {
            pst.setString(1, username);
            pst.setString(2, pw);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return Optional.of(new Utilisateur(res.getInt(1), username, pw, Autorisation.valueOf(res.getString(4))));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            return Optional.empty();
        }
    }

    // Retourne l'id (DB) de l'utilisateur lors de la première connexion:
    public int getID(String username, String pw) throws SQLException {
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

    public void CreationCompte(String username, String pw, String auto, int id) throws SQLException {
        Connection con = this.viewSig.getMain().getInfoSess().getCon();
        con.setAutoCommit(false);
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Identifiant (username,password,autorisation)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setString(1, username);
            pt.setString(2, pw);
            pt.setString(3, auto);
            pt.executeUpdate();
            DeletePreId(id);
            con.commit();
        } catch (SQLException ex) {
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }
    }

    public void GotoLoginForm() {
        this.viewLog.getMain().removeAll();
        this.viewLog.getMain().add(new FirstConnexionForm(this.viewLog.getMain()));
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

    // Suppresion de l'utilisateur dans la table pre_connexion lors de la création du vrai compte
    public void DeletePreId(int id) throws SQLException {
        try (PreparedStatement pst = this.viewSig.getMain().getInfoSess().getCon().prepareStatement(
                "delete "
                + " from pre_connexion"
                + " where id = ?")) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void goBackIden() {
        this.viewSig.getMain().removeAll();
        this.viewSig.getMain().add(new Identification(this.viewSig.getMain()));
    }

    public void goMainContentLog() {
        this.viewLog.getMain().removeAll();
        this.viewLog.getMain().add(new InterfacePrinc(this.viewLog.getMain()));
    }

    public void goMainContentSig() {
        this.viewSig.getMain().removeAll();
        this.viewSig.getMain().add(new InterfacePrinc(this.viewSig.getMain()));
    }

}
