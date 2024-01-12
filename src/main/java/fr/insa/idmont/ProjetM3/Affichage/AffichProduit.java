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
// Composant gérant l'affichage effectif des produits et des sous-composant le permettant.
public class AffichProduit extends VerticalLayout {

    private MainView main;
    private ListeProduits TableProduit;

    public AffichProduit(MainView main, boolean editAble) {
        this.main = main;

        // Création des composant et mise en place de leurs dispositions.
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
        TextField RechercheProduit = new TextField("Rechercher un produit");
        RechercheProduit.setPlaceholder("Appuyez sur entrer");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nouveau produit");
        TextField refAdd = new TextField("Référence");
        refAdd.addClassName("error");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        Button save = new Button("Confirmer");
        Button cancelButton = new Button("Annuler", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        VerticalLayout Hl2 = new VerticalLayout(refAdd, desAdd);
        dialog.add(Hl2);

        try {
            this.TableProduit = new ListeProduits(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()), editAble);
            this.add(Hl1, RechercheProduit, this.TableProduit);;
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, veuillez réessayer");
        }
        this.setAlignSelf(Alignment.CENTER, Hl1);

        if (!editAble) {
            addButton.setEnabled(false);
            deleteButton1.setEnabled(false);
        }

        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteProd(this.main.getInfoSess().getCon(), this.TableProduit.getSelectedItems().iterator());
                refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()), editAble);
            } catch (SQLException ex) {
                Notification.show("Réessayez et vérifiez qu'il n'y ait pas de contrainte sur cet objet");
            }
        });

        RechercheProduit.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchProd(this.main.getInfoSess().getCon(), RechercheProduit.getValue()), editAble);
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());
        save.addClickListener((e) -> {
            // Controle de saisi
            refAdd.setHelperText(null);
            desAdd.setHelperText(null);
            if (refAdd.getValue().length() > 30 || refAdd.getValue().length() == 0) {
                refAdd.setHelperText("1-30 caractères demandés");
            } else if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 caractères demandés ");
            } else {
                try {
                    int i = SqlQueryMainPart.TestProduit(this.main.getInfoSess().getCon(), refAdd.getValue());
                    if (i == -1) {
                        SqlQueryMainPart.addProduct(this.main.getInfoSess().getCon(), refAdd.getValue(), desAdd.getValue());
                        dialog.close();
                        refreshTableProduct(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetProduit(this.main.getInfoSess().getCon()), editAble);
                    } else {
                        refAdd.setHelperText("Le produit existe déjà");
                    }
                } catch (SQLException ex) {
                    Notification.show("Erreur serveur, veuillez réessayer ");
                }
            }

        });

    }

    // Méthodes.
    private void refreshTableProduct(Connection con, List<Produits> data, boolean editAble) throws SQLException {
        this.remove(this.TableProduit);
        this.TableProduit = new ListeProduits(con, data, editAble);
        this.add(this.TableProduit);
    }

}
