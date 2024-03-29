/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.Affichage.AffichOperation;
import fr.insa.idmont.ProjetM3.DataBase_Model.Produits;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
// Création du tableau affichant les données
public class ListeProduits extends Grid<Produits> {

    private Connection con;
    private TextField refField;
    private TextField desField;
    private TextField idField;

    // Constructeur du GRID affichant la liste des produits;
    public ListeProduits(Connection con, List<Produits> data, boolean editAble) throws SQLException {
        this.con = con;

        this.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Produits::getId).setHeader("Id");
        this.addColumn(Produits::getRef).setHeader("Référence");
        this.addColumn(Produits::getDes).setHeader("Description");

            this.addComponentColumn(produit -> {
                Button editButton = new Button("Modifier");
                Button planButton = new Button(VaadinIcon.TASKS.create());
                editButton.addClickListener(e -> {
                    if (this.getEditor().isOpen()) {
                        this.getEditor().cancel();
                    }
                    this.getEditor().editItem(produit);

                });
                planButton.addClickListener((e) -> {
                    if (this.getEditor().isOpen()) {
                        this.getEditor().cancel();
                    }
                    planFabrication(produit.getId(), produit.getRef(), editAble);

                });
                if (!editAble) {
                    editButton.setEnabled(false);
                }
                return new HorizontalLayout(editButton, planButton);
            });
        
        // Initialisation de l'éditeur et associations des composants avec ce dernier:
        this.getEditor().setBinder(new Binder<>(Produits.class));
        this.getEditor().setBuffered(true);

        this.idField = new TextField();
        this.idField.setReadOnly(true);
        this.idField.setSizeFull();
        this.getEditor().getBinder().forField(idField).bind(Produits::getIdString, Produits::setIdString);
        this.getColumns().get(0).setEditorComponent(idField);

        this.refField = new TextField();
        this.refField.setWidthFull();
        this.refField.setClassName("error");
        this.getEditor().getBinder().forField(refField).bind(Produits::getRef, Produits::setRef);
        this.getColumns().get(1).setEditorComponent(refField);

        this.desField = new TextField();
        this.desField.setWidthFull();
        this.desField.setClassName("error");
        this.getEditor().getBinder().forField(desField).bind(Produits::getDes, Produits::setDes);
        this.getColumns().get(2).setEditorComponent(desField);

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
        // Contrôle de saisie.
        this.refField.setHelperText(null);
        this.desField.setHelperText(null);
        if (this.refField.getValue().length() > 30 || this.refField.getValue().length() == 0) {
            this.refField.setHelperText("1-30 caractères demandés");
        } else if (this.desField.getValue().length() > 30 || this.desField.getValue().length() == 0) {
            this.desField.setHelperText("1-30 caractères demandés");
        } else {
            try {
                int i = SqlQueryMainPart.TestProduit(con, this.refField.getValue());
                if (i == Integer.valueOf(this.idField.getValue()) || i == -1) {
                    SqlQueryMainPart.EditProduct(con, this.refField.getValue(), this.desField.getValue(), Integer.parseInt(this.idField.getValue()));
                    this.getEditor().save();
                } else {
                    this.refField.setHelperText("Le produit existe déjà");
                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }
        }

    }

    //Méthode:
    private void planFabrication(int id, String ref, boolean editAble) {
        Dialog diagPlan = new Dialog();
        diagPlan.add(new AffichOperation(con, id, ref, editAble));
        diagPlan.open();
    }

}
