/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeProduits;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.DataBase_Model.Produits;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AffichProduit extends VerticalLayout {

    private MainView main;
    private ListeProduits TableProduit;

    public AffichProduit(MainView main) {
        this.main = main;

        H2 titre1 = new H2("Produits");
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteButton1, titre1, addButton);
        Hl1.setAlignSelf(Alignment.END, deleteButton1);
        Hl1.setAlignSelf(Alignment.CENTER, titre1);
        Hl1.setAlignSelf(Alignment.START, addButton);
        TextField RechercheProduit = new TextField("Search a product");
        RechercheProduit.setPlaceholder("Press enter");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Product");
        TextField refAdd = new TextField("Référence");
        refAdd.addClassName("error");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        VerticalLayout Hl2 = new VerticalLayout(refAdd, desAdd);
        dialog.add(Hl2);

        try {
            this.TableProduit = new ListeProduits(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()));
            this.add(Hl1, RechercheProduit, this.TableProduit);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }
        this.setAlignSelf(Alignment.CENTER, Hl1);

        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteProd(this.main.getInfoSess().getCon(), this.TableProduit.getSelectedItems().iterator());
                refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("Try again and verify there is no constraint on this item");
            }
        });

        RechercheProduit.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchProd(this.main.getInfoSess().getCon(), RechercheProduit.getValue()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());
        save.addClickListener((e) -> {
            refAdd.setHelperText(null);
            desAdd.setHelperText(null);

            if (refAdd.getValue().length() > 30 || refAdd.getValue().length() == 0) {
                refAdd.setHelperText("1-30 characters exiged");
            } else if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 characters exiged ");
            } else {
                try {
                    int i = SqlQueryMainPart.TestProduit(this.main.getInfoSess().getCon(), refAdd.getValue());
                    if (i == -1) {
                        SqlQueryMainPart.addProduct(this.main.getInfoSess().getCon(), refAdd.getValue(), desAdd.getValue());
                        dialog.close();
                        refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()));
                    } else {
                        refAdd.setHelperText("Product already exists");
                    }
                } catch (SQLException ex) {
                    Notification.show("Server error, try again");
                }
            }

        });

    }

    private void refreshTableProduct(Connection con, List<Produits> data) throws SQLException {
        this.remove(this.TableProduit);
        this.TableProduit = new ListeProduits(con, data);
        this.add(this.TableProduit);
    }

}
