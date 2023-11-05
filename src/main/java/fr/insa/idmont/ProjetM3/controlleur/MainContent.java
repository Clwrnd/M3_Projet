/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.GestionUser;
import fr.insa.idmont.ProjetM3.views.ListeUtilisateur;
import fr.insa.idmont.ProjetM3.views.InterfacePrinc;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class MainContent {
    InterfacePrinc view;
    
    public MainContent(InterfacePrinc view) {
        this.view=view;        
    }

    public void GoToAdminOp() {
        this.view.getMain().removeAll();
        this.view.getMain().add(new GestionUser(this.view.getMain()));
    }
    
    public static List<Utilisateur> GetUser(Connection con)throws SQLException{
        ArrayList<Utilisateur> liste = new ArrayList<>();
         try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Identifiant ");
            while (res.next()) {
                liste.add(new Utilisateur(res.getInt(1),res.getString(2),res.getString(3),Autorisation.valueOf(res.getString(4))));
            }
            return liste;
        } catch (SQLException ex) {
            return null;
        }
    }
    
            
    
}
