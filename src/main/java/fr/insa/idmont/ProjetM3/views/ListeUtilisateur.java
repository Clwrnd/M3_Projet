/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.controlleur.Autorisation;
import fr.insa.idmont.ProjetM3.controlleur.Utilisateur;
import java.util.List;

/**
 *
 * @author cidmo
 */
// Affichage des utilisateurs
public class ListeUtilisateur extends Grid<Utilisateur> {

    // Constructeur du GRID affichant la liste des utilisateurs;
    public ListeUtilisateur(List<Utilisateur> data) {

        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des collonnes et des composants d'éditions:
        this.addColumn(Utilisateur::getId).setHeader("Id");
        this.addColumn(Utilisateur::getNom).setHeader("Username");
        this.addColumn(Utilisateur::getPass).setHeader("Passwrod");
        this.addColumn(Utilisateur::getAutorisation).setHeader("Permissions");

        this.addComponentColumn(user -> {
            Button editButton = new Button("Edit");
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

        TextField userField = new TextField();
        userField.setWidthFull();
        this.getEditor().getBinder().forField(userField).bind(Utilisateur::getNom, Utilisateur::setNom);
        this.getColumns().get(1).setEditorComponent(userField);

        PasswordField pwField = new PasswordField();
        pwField.setWidthFull();
        this.getEditor().getBinder().forField(pwField).bind(Utilisateur::getPass, Utilisateur::setPass);
        this.getColumns().get(2).setEditorComponent(pwField);

        ComboBox<Autorisation> selecAutori = new ComboBox<>();
        selecAutori.setItems(Autorisation.values());
        selecAutori.setWidthFull();
        this.getEditor().getBinder().forField(selecAutori).bind(Utilisateur::getAutorisation, Utilisateur::setAutorisation);
        this.getColumns().get(3).setEditorComponent(selecAutori);

        Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> this.getEditor().save());
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

}
