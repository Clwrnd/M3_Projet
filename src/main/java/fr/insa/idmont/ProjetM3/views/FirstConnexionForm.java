/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author cidmo
 */
public class FirstConnexionForm extends VerticalLayout {

    private MainView main;
    private Connexion controlleur;

    // Constructeur du formulaire de première connexion
    public FirstConnexionForm(MainView main) {

        this.main = main;
        this.controlleur = new Connexion(this);

        // Création des composants:
        TextField EntryUsername = new TextField("Username");
        EntryUsername.addClassName("error");
        PasswordField EntryPw = new PasswordField("Default password");
        EntryPw.addClassName("error");
        PasswordField newPassField = new PasswordField("New Password");
        newPassField.addClassName("error");
        Button valide = new Button("Let's start!");
        valide.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button retour = new Button("Retour", new Icon(VaadinIcon.ARROW_LEFT));
        H2 reg = new H2("First connexion");
        Paragraph info = new Paragraph("Enter the login details provided by your administrator and personalize your password.");

        this.add(retour, reg, info, EntryUsername, EntryPw, newPassField, valide);
        this.setAlignSelf(Alignment.CENTER, reg, info, EntryUsername, EntryPw, newPassField, valide);
        this.setAlignSelf(Alignment.START, retour);

        // Action bouton valider: 
        valide.addClickListener((e) -> {
            //Contrôle de saisie:
            EntryPw.setHelperText(null);
            newPassField.setHelperText(null);
            String username = EntryUsername.getValue();
            String pw = EntryPw.getValue();
            String newpw = newPassField.getValue();
            Optional<Utilisateur> user = null;
            try {
                user = this.controlleur.TestPreIdentifiants(username, pw);
            } catch (SQLException ex) {
            }
            if (user.isPresent() && newpw.length() >= 6 && newpw.length() < 50) {
                try {
                    this.controlleur.CreationCompte(username, newpw, user.get().getAutorisation().toString(), user.get().getId());
                    this.main.getInfoSess().setUtilActuel(Optional.of(new Utilisateur(this.controlleur.getID(username, newpw), username, newpw, user.get().getAutorisation())));
                    this.controlleur.goMainContentSig();
                } catch (SQLException ex) {
                }
            } else if (user.isEmpty()) {
                EntryPw.setHelperText("Incorect Username or Password");
            } else {
                newPassField.setHelperText("Password must contain 6 to 50 characters ");
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
