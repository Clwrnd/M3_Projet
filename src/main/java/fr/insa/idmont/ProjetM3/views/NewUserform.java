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
import fr.insa.idmont.ProjetM3.controlleur.Autorisation;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import fr.insa.idmont.ProjetM3.controlleur.Utilisateur;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author cidmo
 */
public class NewUserform extends VerticalLayout {

    private MainView main;
    private Connexion controlleur;

    // Constructeur du formulaire de première connexion
    public NewUserform(MainView main) {

        this.main = main;
        this.controlleur = new Connexion(this);

        // Création des composants:
        TextField EntryUsername = new TextField("Username");
        EntryUsername.addClassName("error");
        PasswordField EntryPw = new PasswordField("Password");
        EntryPw.addClassName("error");
        Button valide = new Button("Let's start!");
        valide.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button retour = new Button("Retour", new Icon(VaadinIcon.ARROW_LEFT));
        H3 reg = new H3("Registration");

        this.add(retour, reg, EntryUsername, EntryPw, valide);
        this.setAlignSelf(Alignment.CENTER, reg, EntryUsername, EntryPw, valide);
        this.setAlignSelf(Alignment.START, retour);

        // Action bouton valider: 
        valide.addClickListener((e) -> {
            //Contrôle de saisie:
            EntryPw.setHelperText(null);
            EntryUsername.setHelperText(null);
            String username = EntryUsername.getValue();
            String pw = EntryPw.getValue();
            if (username.length() > 30 || username.length() == 0) {
                EntryUsername.setHelperText("Username must contain 1 to 30 characters");
            } else if (pw.length() < 6 || pw.length() > 50) {
                EntryPw.setHelperText("Password must contain 6 to 50 characters ");
            } else {
                try {
                    if (this.controlleur.TestUsername(username)) {
                        EntryUsername.setHelperText("Username already exists");
                    } else if (this.controlleur.CreationCompte(username, pw)) {
                        this.main.getInfoSess().setUtilActuel(Optional.of(new Utilisateur(this.controlleur.getID(username, pw), username, pw, Autorisation.CONSULTATION)));
                        this.controlleur.goMainContentSig();
                    } else {
                        Notification.show("Server error, try again.");
                    }
                } catch (SQLException ex) {
                }
            }
        });

        // Action bouton retour:
        retour.addClickListener((e) -> {
            this.controlleur.goBackIden();
        });

    }

    // Get() and Set():
    public MainView getMain() {
        return main;
    }

}
