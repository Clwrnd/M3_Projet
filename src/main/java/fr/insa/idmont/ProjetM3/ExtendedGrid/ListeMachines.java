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
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
// Création du tableau affichant les données
public class ListeMachines extends Grid<Machines> {

    private Connection con;
    private TextField refField;
    private TextField desField;
    private TextField idField;
    private IntegerField puisField;
    private Button LocaFiedl;

    // Constructeur du GRID affichant la liste des opérations;
    public ListeMachines(Connection con, List<Machines> data) throws SQLException {
        this.con = con;

        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Machines::getId).setHeader("Id");
        this.addColumn(Machines::getRef).setHeader("Ref");
        this.addColumn(Machines::getDes).setHeader("Des");
        this.addColumn(Machines::getPuissance).setHeader("Puissance (W)");
        this.addColumn(Machines::getLoc).setHeader("Désignation plan: X-Y");

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
        this.getEditor().setBinder(new Binder<>(Machines.class));
        this.getEditor().setBuffered(true);

        this.idField = new TextField();
        this.idField.setReadOnly(true);
        this.idField.setSizeFull();
        this.getEditor().getBinder().forField(idField).bind(Machines::getIdString, Machines::setIdString);
        this.getColumns().get(0).setEditorComponent(idField);

        this.refField = new TextField();
        this.refField.setWidthFull();
        this.refField.setClassName("error");
        this.getEditor().getBinder().forField(refField).bind(Machines::getRef, Machines::setRef);
        this.getColumns().get(1).setEditorComponent(refField);

        this.desField = new TextField();
        this.desField.setWidthFull();
        this.desField.setClassName("error");
        this.getEditor().getBinder().forField(desField).bind(Machines::getDes, Machines::setDes);
        this.getColumns().get(2).setEditorComponent(desField);

        this.puisField = new IntegerField();
        this.puisField.setMax(999999999);
        this.puisField.setWidthFull();
        this.puisField.setClassName("error");
        this.getEditor().getBinder().forField(puisField).bind(Machines::getPuissance, Machines::setPuissance);
        this.getColumns().get(3).setEditorComponent(puisField);

        this.LocaFiedl = new Button(VaadinIcon.BULLSEYE.create());
        this.LocaFiedl.setWidthFull();
        this.getColumns().get(4).setEditorComponent(LocaFiedl);

        Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> {
            save();
        });
        Button cancelBut = new Button(VaadinIcon.CLOSE.create(), e -> this.getEditor().cancel());
        cancelBut.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        saveBut.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout actions = new HorizontalLayout(saveBut, cancelBut);
        actions.setPadding(false);
        this.getColumns().get(5).setEditorComponent(actions);

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);
        this.getColumns().get(2).setSortable(true);
        this.getColumns().get(3).setSortable(true);

        this.setItems(data);

    }

    private void save() {
        // Contrôle de saisie.
        this.refField.setHelperText(null);
        this.desField.setHelperText(null);
        this.puisField.setHelperText(null);
        if (this.refField.getValue().length() > 30 || this.refField.getValue().length() == 0) {
            this.refField.setHelperText("1-30 caractères demandés");
        } else if (this.desField.getValue().length() > 30 || this.desField.getValue().length() == 0) {
            this.desField.setHelperText("1-30 caractères demandés ");
        } else if (this.puisField.isEmpty()) {
            this.puisField.setHelperText("Entrer une valeur valide");
        } else {
            try {
                int i = SqlQueryMainPart.TestMachine(con, this.refField.getValue());
                if (i == Integer.valueOf(this.idField.getValue()) || i == -1) {
                    SqlQueryMainPart.EditMachine(con, this.refField.getValue(), this.desField.getValue(), Integer.parseInt(this.idField.getValue()), this.puisField.getValue());
                    this.getEditor().save();
                } else {
                    this.refField.setHelperText("La machine existe déjà");
                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }
        }

    }

}
