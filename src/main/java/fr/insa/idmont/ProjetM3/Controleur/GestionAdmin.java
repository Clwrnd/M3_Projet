/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Controleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import fr.insa.idmont.ProjetM3.DataBase_Model.Autorisation;
import fr.insa.idmont.ProjetM3.Affichage.GestionUser;
import fr.insa.idmont.ProjetM3.View.InterfacePrinc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cidmo
 */
// Controleurs de la partie gestion des administrateur :
public class GestionAdmin {

    GestionUser viewGest;

    public static List<Utilisateur> GetPreUser(Connection con) {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM pre_connexion ");
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1), res.getString(2), res.getString(3), Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public GestionAdmin(GestionUser vienGest) {
        this.viewGest = vienGest;
    }

    public static List<Utilisateur> GetUser(Connection con) throws SQLException {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Identifiant ");
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1), res.getString(2), res.getString(3), Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public static void EditUser(Connection con, String newUsername, String newPassword, String newAutorisation, int id) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Identifiant  "
                + " set username=?,password=?,autorisation=?"
                + " where id = ?")) {
            pst.setString(1, newUsername);
            pst.setString(2, newPassword);
            pst.setString(3, newAutorisation);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static void EditPreUser(Connection con, String newUsername, String newPassword, String newAutorisation, int id) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update pre_connexion  "
                + " set username=?,password=?,autorisation=?"
                + " where id = ?")) {
            pst.setString(1, newUsername);
            pst.setString(2, newPassword);
            pst.setString(3, newAutorisation);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public void DeleteUser() throws SQLException {
        int i;
        Iterator<Utilisateur> iteraeur = this.viewGest.getTableUser().getSelectedItems().iterator();
        while (iteraeur.hasNext()) {
            try (PreparedStatement pst = this.viewGest.getMain().getInfoSess().getCon().prepareStatement(
                    "delete "
                    + " from Identifiant"
                    + " where id = ?")) {
                pst.setInt(1, iteraeur.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static int TestUsername(Connection con, String username) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username = ?")) {
            pst.setString(1, username);
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

    public static int TestPreUsername(Connection con, String username) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from pre_connexion"
                + " where username = ?")) {
            pst.setString(1, username);
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

    public List<Utilisateur> searchUser(String User) throws SQLException {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (PreparedStatement st = this.viewGest.getMain().getInfoSess().getCon().prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username like ?")) {
            st.setString(1, "%" + User + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1), res.getString(2), res.getString(3), Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public void DeletePreUser() throws SQLException {
        int i;
        Iterator<Utilisateur> iteraeur = this.viewGest.getTablePreUser().getSelectedItems().iterator();
        while (iteraeur.hasNext()) {
            try (PreparedStatement pst = this.viewGest.getMain().getInfoSess().getCon().prepareStatement(
                    "delete "
                    + " from pre_connexion"
                    + " where id = ?")) {
                pst.setInt(1, iteraeur.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public List<Utilisateur> searchPreUser(String value) {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (PreparedStatement st = this.viewGest.getMain().getInfoSess().getCon().prepareStatement(
                "select *"
                + " from pre_connexion"
                + " where username like ?")) {
            st.setString(1, "%" + value + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1), res.getString(2), res.getString(3), Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }

    public void AddPreUser(String username, String pw, String Autorisation) throws SQLException {
        Connection con = this.viewGest.getMain().getInfoSess().getCon();
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO pre_connexion (username,password,autorisation)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setString(1, username);
            pt.setString(2, pw);
            pt.setString(3, Autorisation);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    public void goMainContent() {
        this.viewGest.getMain().removeAll();
        this.viewGest.getMain().add(new InterfacePrinc(this.viewGest.getMain()));
    }

}
