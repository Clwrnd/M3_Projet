/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import fr.insa.idmont.ProjetM3.controlleur.Utilisateur;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author cidmo
 */
public class Identification extends VerticalLayout {

    private TextField EntryUsername;
    private PasswordField pwEntry;
    private Button validate;
    private Button newUser;
    private Connexion controlleur;
    private MainView main;

    public Identification(MainView main) {

        this.main=main;
        this.controlleur = new Connexion(this);

        Paragraph state = new Paragraph("Pilot found \n Successful connection");
        state.getStyle().set("white-space", "pre-line");

        HorizontalLayout Hlay = new HorizontalLayout();

        this.validate = new Button("Log in");
        this.validate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.newUser = new Button("Sign in");
        this.newUser.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.pwEntry = new PasswordField();
        this.pwEntry.setLabel("Password");
        this.pwEntry.setClassName("error");

        EntryUsername = new TextField("Username");

        Hlay.add(this.newUser, this.validate);
        this.add(new H1("Gestionnaire de ligne de production"), EntryUsername, pwEntry, Hlay, state);
        this.setAlignItems(Alignment.CENTER);
        this.main.setAlignSelf(Alignment.CENTER,this);

        this.validate.addClickListener((e) -> {
            this.pwEntry.setHelperText(null);
            String username = this.EntryUsername.getValue();
            String pw = this.pwEntry.getValue();
            try {
                Optional<Utilisateur> user = this.controlleur.TestiD(username,pw);
                if (user.isPresent()) {
                    this.main.getInfoSess().setUtilActuel(user);
                    this.controlleur.goMainContentLog();
                } else {
                   this.pwEntry.setHelperText("Incorect Username or Password");
                }
            } catch (SQLException ex) {
            }
        });
        
        this.newUser.addClickListener((e)->{
           this.controlleur.GotoLoginForm();
        });
        
    }
        
    
    
    
    
    
    
    
    
    
    public TextField getUsername() {
        return EntryUsername;
    }


    public PasswordField getPwEntry() {
        return pwEntry;
    }



    /**
     * @return the main
     */
    public MainView getMain() {
        return main;
    }

}
