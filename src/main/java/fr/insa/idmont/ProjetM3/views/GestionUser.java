/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.DataBase_Model.Autorisation;
import fr.insa.idmont.ProjetM3.controlleur.GestionAdmin;
import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class GestionUser extends VerticalLayout {

    private MainView main;
    private GestionAdmin controlleur;
    private ListeUtilisateur TableUser;
    private ListeUtilisateur TablePreUser;

    // Constructeur de l'interface de gestion des utilisateurs
    public GestionUser(MainView main) {
        this.main = main;
        this.controlleur = new GestionAdmin(this);

        // Création des composants       
        H2 titre1 = new H2("User Management");
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteButton1, titre1);
        Hl1.setAlignSelf(Alignment.END, deleteButton1);
        Hl1.setAlignSelf(Alignment.CENTER, titre1);

        H2 titre2 = new H2("New User Management");
        Button deleteButton2 = new Button(VaadinIcon.TRASH.create());
        deleteButton2.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl2 = new HorizontalLayout(deleteButton2, titre2, addButton);

        TextField RechercheUserField = new TextField("Search a user");
        RechercheUserField.setValue("Press enter");
        TextField RecherchePreUserField = new TextField("Search a user");
        RecherchePreUserField.setValue("Press enter");

        TextField UserAdd = new TextField("Username");
        UserAdd.addClassName("error");
        PasswordField passAdd = new PasswordField("Password");
        passAdd.addClassName("error");
        ComboBox<Autorisation> autoAdd = new ComboBox<>("Autorisation");
        autoAdd.setItems(Autorisation.values());
        autoAdd.setValue(Autorisation.CONSULTATION);
        
        HorizontalLayout hl3 = new HorizontalLayout(UserAdd, passAdd, autoAdd);

        try {
            this.TableUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(), GestionAdmin.GetUser(this.getMain().getInfoSess().getCon()), true);
            this.TablePreUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(), GestionAdmin.GetPreUser(this.getMain().getInfoSess().getCon()), false);
            this.add(Hl1, RechercheUserField, this.TableUser, new Hr(), Hl2, hl3, RecherchePreUserField, this.TablePreUser);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }

        this.setAlignSelf(Alignment.CENTER, Hl1, Hl2, hl3); 
        
        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                this.controlleur.DeleteUser();
                refreshTableUser(this.main.getInfoSess().getCon(), GestionAdmin.GetUser(this.getMain().getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");
            }
        });

        deleteButton2.addClickListener((e) -> {
            try {
                this.controlleur.DeletePreUser();
                refreshTablePreUser(this.main.getInfoSess().getCon(), GestionAdmin.GetPreUser(this.getMain().getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");
            }
        });

        RechercheUserField.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableUser(this.main.getInfoSess().getCon(), this.controlleur.searchUser(RechercheUserField.getValue()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");

            }
            ;
        });
        RecherchePreUserField.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTablePreUser(this.main.getInfoSess().getCon(), this.controlleur.searchPreUser(RecherchePreUserField.getValue()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");
            }
            ;
        });        

        addButton.addClickListener((e) -> {
            // Contrôle de saisie.
            UserAdd.setHelperText(null);
            passAdd.setHelperText(null);
            if (UserAdd.getValue().length() > 30 || UserAdd.getValue().length() == 0) {
                UserAdd.setHelperText("1-30 characters exiged");
            } else if (passAdd.getValue().length() < 6 || passAdd.getValue().length() > 50) {
                passAdd.setHelperText("6-50 characters exiged ");
            } else {
                try {
                    if (GestionAdmin.TestUsername(this.main.getInfoSess().getCon(), UserAdd.getValue()) != -1 || GestionAdmin.TestPreUsername(this.main.getInfoSess().getCon(), UserAdd.getValue()) != -1) {
                        UserAdd.setHelperText("Username already exists");
                    } else {
                        this.controlleur.AddPreUser(UserAdd.getValue(), passAdd.getValue(), autoAdd.getValue().toString());
                        refreshTablePreUser(this.main.getInfoSess().getCon(), GestionAdmin.GetPreUser(this.getMain().getInfoSess().getCon()));
                    }
                } catch (SQLException ex) {
                    Notification.show("Server error, try again");
                }
            }
        });
    }

    
    
    // Méthodes :
    public void refreshTableUser(Connection con, List<Utilisateur> data) throws SQLException {
        this.remove(this.TableUser);
        this.TableUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(), data, true);
        this.addComponentAtIndex(2, this.TableUser);

    }

    public void refreshTablePreUser(Connection con, List<Utilisateur> data) throws SQLException {
        this.remove(this.TablePreUser);
        this.TablePreUser = new ListeUtilisateur(this.getMain().getInfoSess().getCon(), data, false);
        this.addComponentAtIndex(6, this.TablePreUser);
    }

    //Get() and Set():     
    public ListeUtilisateur getTableUser() {
        return TableUser;
    }

    public MainView getMain() {
        return main;
    }

    /**
     * @return the TablePreUser
     */
    public ListeUtilisateur getTablePreUser() {
        return TablePreUser;
    }

}
