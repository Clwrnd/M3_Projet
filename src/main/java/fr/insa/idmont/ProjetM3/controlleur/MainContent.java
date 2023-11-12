/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import fr.insa.idmont.ProjetM3.DataBase_Model.Autorisation;
import fr.insa.idmont.ProjetM3.views.GestionUser;
import fr.insa.idmont.ProjetM3.views.InterfacePrinc;
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
public class MainContent {

    InterfacePrinc viewPrinc;
    GestionUser viewGest;

    //Différents costructeurs des controleurs de la partie principale:
    public MainContent(InterfacePrinc view) {
        this.viewPrinc = view;
    }

    public MainContent(GestionUser vienGest) {
        this.viewGest = vienGest;
    }

    public void GoToAdminInterface() {
        this.viewPrinc.getMain().removeAll();
        this.viewPrinc.getMain().add(new GestionUser(this.viewPrinc.getMain()));
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
                
            }
        }
    }

    public static boolean TestUsername(Connection con, String username) throws SQLException {
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

    public  List<Utilisateur> searchUser(String User) throws SQLException {
        ArrayList<Utilisateur> liste = new ArrayList<>();
        try (PreparedStatement st = this.viewGest.getMain().getInfoSess().getCon().prepareStatement(
                "select *"
                + " from Identifiant"
                + " where username like ?")) {
            st.setString(1, "%"+User+"%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1), res.getString(2), res.getString(3), Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

}
