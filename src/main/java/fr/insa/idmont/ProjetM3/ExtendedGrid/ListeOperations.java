/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
// Création du tableau affichant les données
public class ListeOperations extends Grid<Operations> {

    Connection con;
    private List<Operations> data;
    private Button upButton;
    private Button downButton;

    // Constructeur du GRID affichant la liste des opérations;
    public ListeOperations(Connection con, List<Operations> data, boolean editAble) throws SQLException {
        this.con = con;
        this.data = data;

        this.addThemeVariants(GridVariant.LUMO_NO_BORDER,GridVariant.LUMO_ROW_STRIPES);
        this.setSelectionMode(Grid.SelectionMode.SINGLE);
        Tooltip tooltip = Tooltip.forComponent(this).withText("Cliquez sur une ligne pour la sélectionner").withPosition(Tooltip.TooltipPosition.BOTTOM);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Operations::getDesTO).setHeader("Description");

        if (editAble) {
            this.addComponentColumn(operation -> {
                this.upButton = new Button(VaadinIcon.ANGLE_UP.create());
                this.upButton.addClassName("error");
                upButton.addClickListener((e) -> {
                    PrecedenceUp(operation);
                });
                this.downButton = new Button(VaadinIcon.ANGLE_DOWN.create());
                this.downButton.addClassName("error");
                downButton.addClickListener((e) -> {
                    PrecedenceDown(operation);
                });

                return new HorizontalLayout(upButton, downButton);
            });
        }

        this.getColumns().get(0).setSortable(true);

        this.setItems(data);

    }

    // Méthodes:
    private void PrecedenceUp(Operations op) {
        try {
            if (this.getData().indexOf(op) == 0) {
                Notification.show("Vous êtes arrivés en haut de liste");
            } else if (this.getData().indexOf(op) == this.getData().size() - 1) {
                SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().size() - 2).getId(), op.getId(), this.getData().get(this.getData().size() - 2).getId(), op.getId());
                if (this.getData().indexOf(op) != 1) {
                    SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.getData().get(this.getData().size() - 3).getId(), this.getData().get(this.getData().size() - 3).getId(), this.getData().get(this.getData().size() - 2).getId());
                }
                Collections.swap(getData(), this.getData().size() - 2, this.getData().indexOf(op));
                this.setItems(getData());
            } else {
                SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().indexOf(op) - 1).getId(), op.getId(), this.getData().get(this.getData().indexOf(op) - 1).getId(), op.getId());
                SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().indexOf(op) + 1).getId(), this.getData().get(this.getData().indexOf(op) - 1).getId(), op.getId(), this.getData().get(this.getData().indexOf(op) + 1).getId());
                if (this.getData().indexOf(op) != 1) {
                    SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.getData().get(this.getData().indexOf(op) - 2).getId(), this.getData().get(this.getData().indexOf(op) - 2).getId(), this.getData().get(this.getData().indexOf(op) - 1).getId());
                }

                Collections.swap(getData(), this.getData().indexOf(op) - 1, this.getData().indexOf(op));
                this.setItems(getData());
            }
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, réessayer");
        }

    }

    private void PrecedenceDown(Operations op) {
        try {
            if (this.getData().indexOf(op) == this.getData().size() - 1) {
                Notification.show("Vous êtes arrivés en bas de liste");
            } else if (this.getData().indexOf(op) == 0) {
                SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.getData().get(1).getId(), op.getId(), this.getData().get(1).getId());
                if (this.getData().indexOf(op) != this.getData().size() - 2) {
                    SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().indexOf(op) + 2).getId(), op.getId(), this.getData().get(this.getData().indexOf(op) + 1).getId(), this.getData().get(this.getData().indexOf(op) + 2).getId());
                }
                Collections.swap(getData(), 0, 1);
                this.setItems(getData());
            } else {
                SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.getData().get(getData().indexOf(op) + 1).getId(), op.getId(), this.getData().get(getData().indexOf(op) + 1).getId());
                SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().indexOf(op) + 1).getId(), this.getData().get(this.getData().indexOf(op) - 1).getId(), this.getData().get(this.getData().indexOf(op) - 1).getId(), op.getId());
                if (this.getData().indexOf(op) != this.getData().size() - 2) {
                    SqlQueryMainPart.EditPrecedence2(con, this.getData().get(this.getData().indexOf(op) + 2).getId(), op.getId(), this.getData().get(this.getData().indexOf(op) + 1).getId(), this.getData().get(this.getData().indexOf(op) + 2).getId());
                }

                Collections.swap(getData(), this.getData().indexOf(op) + 1, this.getData().indexOf(op));
                this.setItems(getData());
            }
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, réessayer");
        }

    }

    // Get et Set:
    public List<Operations> getData() {
        return data;
    }

}
