/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class GestionUser extends VerticalLayout{
    MainView main;
    MainContent controlleur;

    public GestionUser(MainView main) {
        this.main = main;
        try {
            this.add(new H3("User Lsit"),new ListeUtilisateur(MainContent.GetUser(this.main.getInfoSess().getCon())));
        } catch (SQLException ex) {

        }
    }
    
  
    
    
}
