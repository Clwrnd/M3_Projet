/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import static fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart.GetTO;
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Simon
 */
// Création du tableau affichant les données
public class ListeRealise extends Grid<Realise> {

    private Connection con;
    private ComboBox<TypeOperations> TypeOperation;
    private ComboBox<Machines> Machine;
    private NumberField duree;

    public ListeRealise(Connection con, List<Realise> data, boolean editAble) throws SQLException {

        this.con = con;
        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Constructeur du GRID affichant la liste des opérations;
        this.addColumn(Realise::getMachine).setHeader("Machine");
        this.addColumn(Realise::getTO).setHeader("Operation");
        this.addColumn(Realise::getDuree).setHeader("Durée");

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

        this.addComponentColumn(realise -> {
            Button info = new Button(VaadinIcon.INFO.create());
            info.addThemeVariants(ButtonVariant.LUMO_ICON,
                    ButtonVariant.LUMO_TERTIARY);
            info.addClickListener((e) -> {

            });
            return info;
        });

        // Initialisation de l'éditeur et associations des composants avec ce dernier:
        this.getEditor().setBinder(new Binder<>(Realise.class));
        this.getEditor().setBuffered(true);

        this.Machine = new ComboBox<>();
        this.Machine.setItems(SqlQueryMainPart.GetMachine(con));
        this.Machine.setWidthFull();
        this.Machine.setClassName("error");
        this.getEditor().getBinder().forField(Machine).bind(Realise::getMachine, Realise::setMachine);
        this.getColumns().get(0).setEditorComponent(Machine);

        this.TypeOperation = new ComboBox<>();
        this.TypeOperation.setItems(GetTO(con));
        this.TypeOperation.setWidthFull();
        this.getEditor().getBinder().forField(TypeOperation).bind(Realise::getTO, Realise::setTypeOperation);
        this.getColumns().get(1).setEditorComponent(TypeOperation);

        this.duree = new NumberField();
        this.duree.setWidthFull();
        this.duree.setClassName("error");
        this.getEditor().getBinder().forField(duree).bind(Realise::getDuree, Realise::setDuree);
        this.getColumns().get(2).setEditorComponent(duree);

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
            this.getColumns().get(3).setEditorComponent(actions);
        }

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);
        this.getColumns().get(2).setSortable(true);

        this.setItems(data);

    }

    private void save() {
        // Controle de saisie:
        this.Machine.setHelperText(null);
        this.TypeOperation.setHelperText(null);
        this.duree.setHelperText(null);

        if (this.Machine.isEmpty()) {
            this.Machine.setHelperText("Entrez une valeur");
        } else if (this.TypeOperation.isEmpty()) {
            this.TypeOperation.setHelperText("Entrez une valeur");
        } else if (this.duree.isEmpty()) {
            this.duree.setHelperText("Entrez une valeur");
        } else {
            try {
                SqlQueryMainPart.EditRealise(con, this.TypeOperation.getValue().getId(), this.duree.getValue(), this.Machine.getValue().getId(), this.getEditor().getItem().getTO().getId(), this.getEditor().getItem().getDuree(), this.getEditor().getItem().getMachine().getId());
                this.getEditor().save();
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }

        }

    }

}
