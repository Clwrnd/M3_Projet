/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
// Création du tableau affichant les données
public class ListeTypeOperations extends Grid<TypeOperations> {

    Connection con;
    private TextField desField;
    private IntegerField idField;

    // Constructeur du GRID affichant la liste des types d'opérations;
    public ListeTypeOperations(Connection con, List<TypeOperations> data, boolean editAble) throws SQLException {

        this.con = con;
        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(TypeOperations::getId).setHeader("Id");
        this.addColumn(TypeOperations::getDes).setHeader("Description");

        if (editAble) {
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
        }
        // Initialisation de l'éditeur et associations des composants avec ce dernier:
        this.getEditor().setBinder(new Binder<>(TypeOperations.class));
        this.getEditor().setBuffered(true);

        this.idField = new IntegerField();
        this.idField.setReadOnly(true);
        this.idField.setSizeFull();
        this.getEditor().getBinder().forField(idField).bind(TypeOperations::getId, TypeOperations::setId);
        this.getColumns().get(0).setEditorComponent(idField);

        this.desField = new TextField();
        this.desField.setWidthFull();
        this.desField.setClassName("error");
        this.getEditor().getBinder().forField(desField).bind(TypeOperations::getDes, TypeOperations::setDes);
        this.getColumns().get(1).setEditorComponent(desField);

        Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> {
            save();
        });

        Button cancelBut = new Button(VaadinIcon.CLOSE.create(), e -> this.getEditor().cancel());
        cancelBut.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        saveBut.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout actions = new HorizontalLayout(saveBut, cancelBut);
        actions.setPadding(false);

        if (editAble) {
            this.getColumns().get(2).setEditorComponent(actions);
        }

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);

        this.setItems(data);

    }

    private void save() {
        // Contrôle de saisie.
        this.desField.setHelperText(null);
        if (this.desField.getValue().length() > 30 || this.desField.getValue().length() == 0) {
            this.desField.setHelperText("1-30 caractères demandés");
        } else {
            try {
                int i = SqlQueryMainPart.TestTO(con, this.desField.getValue());
                if (i == this.idField.getValue() || i == -1) {
                    SqlQueryMainPart.EditTO(con, this.desField.getValue(), this.idField.getValue());
                    this.getEditor().save();
                } else {
                    this.desField.setHelperText("L'opération existe déjà");
                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }
        }

    }

}
