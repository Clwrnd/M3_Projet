/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;
import java.sql.Connection;
import java.sql.SQLException;

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
        try {
            this.TableUser = new ListeUtilisateur(MainContent.GetUser(this.getMain().getInfoSess().getCon()));
            this.add(Hl, this.TableUser);;
        } catch (SQLException ex) {
            //
        }

        this.setAlignSelf(Alignment.CENTER, Hl);

        //Actions des composants:
        deleteButton.addClickListener((e) -> {
            try {
                this.controlleur.DeleteUser();
                refreshTable(this.main.getInfoSess().getCon());
            } catch (SQLException ex) {
                //
            }
        });
    }

    // Méthodes :
    public void refreshTable(Connection con) throws SQLException {
        this.remove(this.TableUser);
        this.TableUser = new ListeUtilisateur(MainContent.GetUser(con));
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
