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
// Composant gérant l'affichage effectif des types d'opérations et des sous-composant le permettant.
public class AffichTypeOp extends VerticalLayout {

    private MainView main;
    private ListeTypeOperations tableTO;

    public AffichTypeOp(MainView main, boolean editAble) {
        this.main = main;

        // Création des composant et mise en place de leurs dispositions.
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
        TextField RechercheTO = new TextField("Rechercher TO");
        RechercheTO.setPlaceholder("Appuyer sur entrer");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nouveau type d'opération");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        Button save = new Button("Confirmer");
        Button cancelButton = new Button("Annuler", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        dialog.add(desAdd);

        try {
            this.tableTO = new ListeTypeOperations(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()), editAble);
            this.add(Hl1, RechercheTO, this.tableTO);;
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, veuillez réessayer");
        }
        this.setAlignSelf(FlexComponent.Alignment.CENTER, Hl1);

        if (!editAble) {
            addButton.setEnabled(false);
            deleteButton1.setEnabled(false);
        }
        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteTO(this.main.getInfoSess().getCon(), this.tableTO.getSelectedItems().iterator());
                refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()), editAble);
            } catch (SQLException ex) {
                Notification.show("Réessayez et vérifiez qu'il n'y ait pas de contrainte sur cet objet");
            }
        });

        RechercheTO.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchTO(this.main.getInfoSess().getCon(), RechercheTO.getValue()), editAble);
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());
        save.addClickListener((e) -> {
            desAdd.setHelperText(null);

            if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 caractères demandés");
            } else {
                try {
                    int i = SqlQueryMainPart.TestTO(this.main.getInfoSess().getCon(), desAdd.getValue());
                    if (i == -1) {
                        SqlQueryMainPart.addTO(this.main.getInfoSess().getCon(), desAdd.getValue());
                        dialog.close();
                        refreshTableTO(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()), editAble);
                    } else {
                        desAdd.setHelperText("Le type d'opération existe déjà");
                    }
                } catch (SQLException ex) {
                    Notification.show("Erreur serveur, veuillez réessayer");
                }
            }

        });

    }

    //Méthode:
    private void refreshTableTO(Connection con, List<TypeOperations> data, boolean editAble) throws SQLException {
        this.remove(this.tableTO);
        this.tableTO = new ListeTypeOperations(con, data, editAble);
        this.add(this.tableTO);
    }

}
