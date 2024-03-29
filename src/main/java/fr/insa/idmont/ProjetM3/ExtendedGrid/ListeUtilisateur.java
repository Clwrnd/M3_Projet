/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.DataBase_Model.Autorisation;
import fr.insa.idmont.ProjetM3.Controleur.GestionAdmin;
import fr.insa.idmont.ProjetM3.DataBase_Model.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
// Création du tableau affichant les données
public class ListeUtilisateur extends Grid<Utilisateur> {

    private TextField userField;
    private PasswordField pwField;
    private ComboBox<Autorisation> selecAutori;
    private TextField id;
    private Connection con;
    private boolean mode; // True -> User, False -> PreUser

    // Constructeur du GRID affichant la liste des utilisateurs;
    public ListeUtilisateur(Connection con, List<Utilisateur> data, boolean mode) throws SQLException {
        this.con = con;
        this.mode = mode;

        this.setSelectionMode(Grid.SelectionMode.MULTI);
        this.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Utilisateur::getId).setHeader("Id");
        this.addColumn(Utilisateur::getNom).setHeader("Nom d'utilisateur");
        this.addColumn(Utilisateur::getPass).setHeader("Mot de passe");
        this.addColumn(Utilisateur::getAutorisation).setHeader("Permissions");

        this.addComponentColumn(user -> {
            Button editButton = new Button("Modifier");
            editButton.addClickListener(e -> {
                if (this.getEditor().isOpen()) {
                    this.getEditor().cancel();
                }
                this.getEditor().editItem(user);
            });
            return editButton;
        });

        // Initialisation de l'éditeur et associations des composants avec ce dernier:
        this.getEditor().setBinder(new Binder<>(Utilisateur.class));
        this.getEditor().setBuffered(true);

        this.userField = new TextField();
        this.userField.setWidthFull();
        this.userField.setClassName("error");
        this.getEditor().getBinder().forField(userField).bind(Utilisateur::getNom, Utilisateur::setNom);
        this.getColumns().get(1).setEditorComponent(userField);

        this.pwField = new PasswordField();
        this.pwField.setWidthFull();
        this.pwField.setClassName("error");
        this.getEditor().getBinder().forField(pwField).bind(Utilisateur::getPass, Utilisateur::setPass);
        this.getColumns().get(2).setEditorComponent(pwField);

        this.selecAutori = new ComboBox<>();
        this.selecAutori.setItems(Autorisation.values());
        this.selecAutori.setWidthFull();
        this.selecAutori.setAllowCustomValue(false);
        this.selecAutori.addClassName("error");
        this.getEditor().getBinder().forField(selecAutori).bind(Utilisateur::getAutorisation, Utilisateur::setAutorisation);
        this.getColumns().get(3).setEditorComponent(selecAutori);

        this.id = new TextField();
        this.id.setReadOnly(true);
        this.id.setSizeFull();
        this.getEditor().getBinder().forField(id).bind(Utilisateur::getIdString, Utilisateur::setIdString);
        this.getColumns().get(0).setEditorComponent(id);

        Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> {
            try {
                save();
            } catch (SQLException ex) {

            }
        });

        Button cancelBut = new Button(VaadinIcon.CLOSE.create(), e -> this.getEditor().cancel());
        cancelBut.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        saveBut.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout actions = new HorizontalLayout(saveBut, cancelBut);
        actions.setPadding(false);
        this.getColumns().get(4).setEditorComponent(actions);

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);
        this.getColumns().get(3).setSortable(true);

        this.setItems(data);

    }

    private void save() throws SQLException {
        // Contrôle de saisie.
        this.userField.setHelperText(null);
        this.pwField.setHelperText(null);
        this.selecAutori.setHelperText(null);
        if (this.userField.getValue().length() > 30 || this.userField.getValue().length() == 0) {
            this.userField.setHelperText("1-30 caractères demandés");
        } else if (this.pwField.getValue().length() < 6 || this.pwField.getValue().length() > 50) {
            this.pwField.setHelperText("6-50 caractères demandés");
        } else if (this.selecAutori.isEmpty()) {
            this.selecAutori.setHelperText("Entrer une valeur");
        } else {
            try {
                int i = GestionAdmin.TestUsername(con, this.userField.getValue());
                int j = GestionAdmin.TestPreUsername(con, this.userField.getValue());
                if (i == Integer.valueOf(this.id.getValue()) || (i == -1&&j==-1)) {
                    this.getEditor().save();
                    if (this.mode) {
                        GestionAdmin.EditUser(con, this.userField.getValue(), this.pwField.getValue(), this.selecAutori.getValue().toString(), Integer.valueOf(id.getValue()));
                    } else {
                        GestionAdmin.EditPreUser(con, this.userField.getValue(), this.pwField.getValue(), this.selecAutori.getValue().toString(), Integer.valueOf(id.getValue()));
                    }
                } else {
                    this.userField.setHelperText("Nom déjà existant");
                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }
        }

    }

}
