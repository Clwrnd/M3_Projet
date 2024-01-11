/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.View;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.Controleur.Connexion;
import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import java.sql.SQLException;
import java.util.Optional;

/**
 *
 * @author cidmo
 */
// Interface de premières connexion d'un utilisateur
public class FirstConnexionForm extends VerticalLayout {

    private MainView main;
    private Connexion controlleur;

    // Constructeur du formulaire de première connexion
    public FirstConnexionForm(MainView main) {

        this.main = main;
        this.controlleur = new Connexion(this);

        // Création des composants:
        TextField EntryUsername = new TextField("Nom d'utilisateur");
        EntryUsername.addClassName("error");
        PasswordField EntryPw = new PasswordField("Mot de passe par défault");
        EntryPw.addClassName("error");
        PasswordField newPassField = new PasswordField("Nouveau mot de passe");
        newPassField.addClassName("error");
        PasswordField confirmPass = new PasswordField("Confirmer le mot de passe");
        confirmPass.addClassName("error");
        Button valide = new Button("C'est parti !");
        valide.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button retour = new Button("Retour", new Icon(VaadinIcon.ARROW_LEFT));
        H2 reg = new H2("Première connection");
        Paragraph info = new Paragraph("Entrez vos données de connection fournis par votre administrateur et personalisez votre mot de passe");
        HorizontalLayout DownSideHl = new HorizontalLayout(retour, valide);

        this.add(reg, info, EntryUsername, EntryPw, newPassField, confirmPass, DownSideHl);
        this.setAlignSelf(Alignment.CENTER, reg, info, EntryUsername, EntryPw, newPassField, confirmPass, DownSideHl);
        this.setAlignSelf(Alignment.START, retour);

        // Action bouton valider: 
        valide.addClickListener((e) -> {
            //Contrôle de saisie:
            EntryPw.setHelperText(null);
            newPassField.setHelperText(null);
            String username = EntryUsername.getValue();
            String pw = EntryPw.getValue();
            String newpw = newPassField.getValue();
            String confirmPassSt = confirmPass.getValue();
            Optional<Utilisateur> user = null;
            try {
                user = this.controlleur.TestPreIdentifiants(username, pw);
            } catch (SQLException ex) {
            }
            if (user.isPresent() && newpw.length() >= 6 && newpw.length() < 50) {
                if (newpw.equals(confirmPassSt)) {
                    try {
                        this.controlleur.CreationCompte(username, newpw, user.get().getAutorisation().toString(), user.get().getId());
                        this.main.getInfoSess().setUtilActuel(Optional.of(new Utilisateur(this.controlleur.getID(username, newpw), username, newpw, user.get().getAutorisation())));
                        this.controlleur.goMainContentSig();
                    } catch (SQLException ex) {
                        Notification.show("Erreur serveur, réeesayer");
                    }
                } else {
                    confirmPass.setHelperText("Les mots de passe ne correspondent pas");
                }
            } else if (user.isEmpty()) {
                EntryPw.setHelperText("Nom d'utilisateur ou mot de passe incorrect");
            } else {
                newPassField.setHelperText("Le mot de passe doit contenir entre 6 et 50 caractères");
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
