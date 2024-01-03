/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeTypeOperations;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AffichTypeOp extends VerticalLayout {

    private MainView main;
    private ListeTypeOperations tableTO;

    public AffichTypeOp(MainView main) {
        this.main = main;

        H2 titre1 = new H2("Operation type");
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteButton1, titre1, addButton);
        Hl1.setAlignSelf(FlexComponent.Alignment.END, deleteButton1);
        Hl1.setAlignSelf(FlexComponent.Alignment.CENTER, titre1);
        Hl1.setAlignSelf(FlexComponent.Alignment.START, addButton);
        TextField RechercheTO = new TextField("Search an operation type");
        RechercheTO.setPlaceholder("Press enter");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New operation type");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        dialog.add(desAdd);

        try {
            this.tableTO = new ListeTypeOperations(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()));
            this.add(Hl1, RechercheTO, this.tableTO);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }
        this.setAlignSelf(FlexComponent.Alignment.CENTER, Hl1);

        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteTO(this.main.getInfoSess().getCon(), this.tableTO.getSelectedItems().iterator());
                refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("Try again and verify there is no constraint on this item");
            }
        });

        RechercheTO.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchTO(this.main.getInfoSess().getCon(), RechercheTO.getValue()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());
        save.addClickListener((e) -> {
            desAdd.setHelperText(null);

            if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 characters exiged");
            } else {
                try {
                    int i = SqlQueryMainPart.TestTO(this.main.getInfoSess().getCon(), desAdd.getValue());
                    if (i == -1) {
                        SqlQueryMainPart.addTO(this.main.getInfoSess().getCon(), desAdd.getValue());
                        dialog.close();
                        refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()));
                    } else {
                        desAdd.setHelperText("Operation type already exists");
                    }
                } catch (SQLException ex) {
                    Notification.show("Server error, try again");
                }
            }

        });

    }

    private void refreshTableTO(Connection con, List<TypeOperations> data) throws SQLException {
        this.remove(this.tableTO);
        this.tableTO = new ListeTypeOperations(con, data);
        this.add(this.tableTO);
    }

}
