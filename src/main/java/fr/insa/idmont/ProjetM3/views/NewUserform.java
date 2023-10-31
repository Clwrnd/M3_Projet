/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class NewUserform extends VerticalLayout {
    private TextField EntryUsername;
    private PasswordField EntryPw;
    private Button valide;
    private MainView main;
    private Connexion controlleur; 
    private Button retour;
    
    public NewUserform(MainView main){  
        
       this.main = main;
       this.controlleur = new Connexion(this);
       this.EntryUsername = new TextField("Username");
       this.EntryUsername.addClassName("error");
       this.EntryPw = new PasswordField("Password");
       this.EntryPw.addClassName("error");
       this.valide= new Button("Let's start!");
       this.valide.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
       this.retour = new Button("Retour", new Icon(VaadinIcon.ARROW_LEFT));
       H3 reg = new H3("Registration");
       
       this.add(retour,reg,EntryUsername,EntryPw,valide);
       this.setAlignSelf(Alignment.CENTER,reg,EntryUsername,EntryPw,valide);
       this.setAlignSelf(Alignment.START,retour);



       
       this.valide.addClickListener((e)->{       
               this.EntryPw.setHelperText(null);
               this.EntryUsername.setHelperText(null);
               String username = this.EntryUsername.getValue();
               String pw = this.EntryPw.getValue();
               if(username.length()>30||username.length()==0){
                   this.EntryUsername.setHelperText("Username must contain 1 to 30 characters");
               } 
               else if(pw.length()<6 || pw.length()>50) {
                   this.EntryPw.setHelperText("Password must contain 6 to 50 characters ");
              }                
               else {
           try {
               if(this.controlleur.TestUsername(username)){
                   this.EntryUsername.setHelperText("Username already exists");
               }
               else if(this.controlleur.CreationCompte(username, pw)){
                 this.controlleur.goMainContentSig();
               } else {
                   Notification.show("Server error, try again.");
               }
           } catch (SQLException ex) {
           }
       }
       });
       
       this.retour.addClickListener((e)->{
          this.controlleur.goBackIden();
       });
      
       
    }


    public MainView getMain() {
        return main;
    }
    
}
