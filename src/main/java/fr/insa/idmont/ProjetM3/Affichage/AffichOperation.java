/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeOperations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AffichOperation extends VerticalLayout {

    private Connection con;
    private ListeOperations TableOp;

    public AffichOperation(Connection con, int idproduit,String ref) {
        this.con = con;

        H2 titre1 = new H2("Plan de fabrication: "+ref);
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        Button deleteAllButton = new Button(VaadinIcon.EXCLAMATION_CIRCLE_O.create());
        deleteAllButton.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        deleteButton1.setTooltipText("Supprimer un élément");
        deleteAllButton.setTooltipText("Supprimer tout !");

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteAllButton,deleteButton1, titre1, addButton);
        Hl1.setAlignSelf(FlexComponent.Alignment.END, deleteAllButton,deleteButton1);
        Hl1.setAlignSelf(FlexComponent.Alignment.CENTER, titre1);
        Hl1.setAlignSelf(FlexComponent.Alignment.START, addButton);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Add an operation");
        ComboBox<TypeOperations> ToChoix = new ComboBox<>("Type d'opération:");
        ToChoix.addClassName("error");
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.add(ToChoix);
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        try {
            this.TableOp = new ListeOperations(this.con, SqlQueryMainPart.GetOp(this.con, idproduit));
            this.add(Hl1, this.TableOp);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }
        this.setAlignSelf(Alignment.CENTER, Hl1);

        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deletePrece(con, this.TableOp.getSelectedItems().iterator(), this.TableOp.getData());
                SqlQueryMainPart.deleteOp(this.con, this.TableOp.getSelectedItems().iterator());
                refreshTableOp(this.con, SqlQueryMainPart.GetOp(this.con, idproduit));
            } catch (SQLException ex) {
                Notification.show("Try again and verify there is no constraint on this item");
            }
        });
        deleteAllButton.addClickListener((e) -> {
            try {
                SqlQueryMainPart.DeletePreceAll(con,this.TableOp.getData());
                SqlQueryMainPart.deleteOpAll(this.con, idproduit);
                refreshTableOp(this.con, SqlQueryMainPart.GetOp(this.con, idproduit));
            } catch (SQLException ex) {
                Notification.show("Try again and verify there is no constraint on this item");
            }
        });

        
        addButton.addClickListener((e) -> {
            dialog.open();
            try {
                ToChoix.setItems(SqlQueryMainPart.GetTO(this.con));
            } catch (SQLException ex) {
                Notification.show("Server error, retry");
            }
        });

        save.addClickListener((e) -> {
            if (ToChoix.isEmpty()) {
                ToChoix.setHelperText("Choose a value");
            } else {
                List<Operations> existant = SqlQueryMainPart.GetOp(con, idproduit);
                int id =SqlQueryMainPart.addOp(con, ToChoix.getValue().getId(), idproduit);
                AddPrecedence(existant,id);
                dialog.close();
                try {
                    refreshTableOp(con, SqlQueryMainPart.GetOp(con, idproduit));
                } catch (SQLException ex) {
                    Notification.show("Server error, retry");
                }
            }

        });

    }

    private void refreshTableOp(Connection con, List<Operations> data) throws SQLException {
        this.remove(this.TableOp);
        this.TableOp = new ListeOperations(con, data);
        this.add(this.TableOp);
    }

    private void AddPrecedence(List<Operations> existant,int id) {
        int taille = existant.size();
        if(taille==0){
            // Ne rien faire
        } else {
            SqlQueryMainPart.addPrecedence(con,existant.get(taille-1).getId(),id );
        }
    }

}
