/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;
import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cidmo
 */
public class GestionUser extends VerticalLayout {

    private MainView main;
    private MainContent controlleur;
    private ListeUtilisateur TableUser;

    // Constructeur de l'interface de gestion des utilisateurs
    public GestionUser(MainView main) {
        this.main = main;
        this.controlleur = new MainContent(this);

        // Création des composants       
        H2 titre = new H2("User Management");
        Button deleteButton = new Button(VaadinIcon.TRASH.create());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout Hl = new HorizontalLayout(deleteButton, titre);
        Hl.setAlignSelf(Alignment.END, deleteButton);
        Hl.setAlignSelf(Alignment.CENTER, titre);
        Hl.setSpacing(false);
        Hl.getThemeList().add("spacing-xl");
        
        TextField RechercheUserField= new TextField("Search a user");
        RechercheUserField.setValue("Press enter");        
        try {
            this.TableUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(),MainContent.GetUser(this.getMain().getInfoSess().getCon()));
            this.add(Hl, RechercheUserField,this.TableUser);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }

        this.setAlignSelf(Alignment.CENTER, Hl);

        //Actions des composants:
        deleteButton.addClickListener((e) -> {
            try {
                this.controlleur.DeleteUser();
                refreshTable(this.main.getInfoSess().getCon(),MainContent.GetUser(this.getMain().getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");
            }
        });
        
        RechercheUserField.addKeyPressListener(Key.ENTER, (e)->{
            try {
                refreshTable(this.main.getInfoSess().getCon(),this.controlleur.searchUser(RechercheUserField.getValue()));
            } catch (SQLException ex) {
                
            }
;
                });
    }

    // Méthodes :
    public void refreshTable(Connection con,List<Utilisateur> data) throws SQLException {
        this.remove(this.TableUser);
        this.TableUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(),data);
        this.add(this.TableUser);

    }

    //Get() and Set(): 
    
    public ListeUtilisateur getTableUser() {
        return TableUser;
    }

    public MainView getMain() {
        return main;
    }

}
