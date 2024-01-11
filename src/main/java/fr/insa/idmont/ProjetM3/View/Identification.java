/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.View;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
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
public class Identification extends VerticalLayout {

    private Connexion controlleur;
    private MainView main;

    // Constructeur du formulaire de connexion
    public Identification(MainView main) {

        this.main = main;
        this.controlleur = new Connexion(this);

        // Création des composants:
        VerticalLayout Vlay = new VerticalLayout();

        Button validerBut = new Button("Se connecter");
        validerBut.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button newUserBut = new Button("S'inscrire");
        newUserBut.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        PasswordField pwEntry = new PasswordField();
        pwEntry.setLabel("Mot de passe");
        pwEntry.setClassName("erreur");

        TextField EntryUsername = new TextField("Nom d'utilisateur");

        Vlay.add(new Paragraph("Première connection ? Cliquer ici : "), newUserBut);
        Vlay.setAlignItems(Alignment.CENTER);
        Vlay.setSpacing(false);
        this.add(new H1("Gestionnaire de ligne de production"), EntryUsername, pwEntry, validerBut, new H1(""), Vlay);
        this.setAlignItems(Alignment.CENTER);
        this.main.setAlignSelf(Alignment.CENTER, this);

        // Actions des composants
        validerBut.addClickListener((e) -> {
            pwEntry.setHelperText(null);
            String username = EntryUsername.getValue();
            String pw = pwEntry.getValue();
            try {
                Optional<Utilisateur> user = this.controlleur.TestIdentifiants(username, pw);
                if (user.isPresent()) {
                    this.main.getInfoSess().setUtilActuel(user);
                    this.controlleur.goMainContentLog();
                } else {
                    pwEntry.setHelperText("Nom d'utilisateur et/ou mot de passe incorrect ");
                }
            } catch (SQLException ex) {
                Notification.show("Error data base");
            }
        });

        newUserBut.addClickListener((e) -> {
            this.controlleur.GotoLoginForm();
        });

    }

    //Get() and Set()
    public MainView getMain() {
        return main;
    }

}
