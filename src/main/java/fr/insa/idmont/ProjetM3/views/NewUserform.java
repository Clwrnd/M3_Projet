/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cidmo
 */
public class NewUserform extends VerticalLayout {
    private TextField EntryUsername;
    private TextField EntryPw;
    private Button valide;
    private MainView main;
    private Connexion controlleur; 
    
    public NewUserform(MainView main){       
       this.main = main;
       this.controlleur = new Connexion(this);
       this.EntryUsername = new TextField("Username");
       this.EntryPw = new TextField("Password");
       this.valide= new Button("Let's start!");
       this.valide.addThemeVariants(ButtonVariant.LUMO_PRIMARY);              
       
       this.add(new H3("Registration"),EntryUsername,EntryPw,valide);
       this.setAlignItems(Alignment.CENTER);
       this.main.setAlignSelf(Alignment.CENTER, this);
       
       this.valide.addClickListener((e)->{          
           try {
               String username = this.EntryUsername.getValue();
               String pw = this.EntryPw.getValue();
               if(this.controlleur.CreationCompte(username, pw)){
               };
           } catch (SQLException ex) {
           }
       });
      
       
    }


    public MainView getMain() {
        return main;
    }
    
}
