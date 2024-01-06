/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.insa.idmont.ProjetM3.Controleur.AlgoGestionPrecedence;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Henry Adèle
 */
public class ListeOperations extends Grid<Operations> {

    Connection con;
    List<Operations> data;
    private Button upButton;
    private Button downButton;

    // Constructeur du GRID affichant la liste des opérations;
    public ListeOperations(Connection con, List<Operations> data) throws SQLException {
        this.con = con;
        this.data = data;

        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Operations::getDesTO).setHeader("Description");

        this.addComponentColumn(operation -> {
            this.upButton = new Button(VaadinIcon.ANGLE_UP.create());
            this.upButton.addClassName("error");
            upButton.addClickListener((e) -> {
                try {
                    PrecedenceUp(operation);
                } catch (SQLException ex) {
                }
            });
            this.downButton = new Button(VaadinIcon.ANGLE_DOWN.create());
            this.downButton.addClassName("error");
            downButton.addClickListener((e) -> {
                try {
                    PrecedenceDown(operation);
                } catch (SQLException ex) {
                }
            });

            return new HorizontalLayout(upButton, downButton);
        });

        this.getColumns().get(0).setSortable(true);

        this.setItems(data);

    }

    private void PrecedenceUp(Operations op) throws SQLException {
        if (this.data.indexOf(op) == 0) {
            Notification.show("Vous êtes arrivés en haut de liste");
        } else if (this.data.indexOf(op) == this.data.size() - 1) {
            SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.size() - 2).getId(), op.getId(), this.data.get(this.data.size() - 2).getId(), op.getId());
            if (this.data.indexOf(op) != 1) {
                SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.data.get(this.data.size() - 3).getId(), this.data.get(this.data.size() - 3).getId(), this.data.get(this.data.size() - 2).getId());
            }
            Collections.swap(data, this.data.size() - 2, this.data.indexOf(op));
            this.setItems(data);
        } else {
            SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.indexOf(op) - 1).getId(), op.getId(), this.data.get(this.data.indexOf(op) - 1).getId(), op.getId());
            SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.indexOf(op) + 1).getId(), this.data.get(this.data.indexOf(op) -1).getId(),op.getId(),this.data.get(this.data.indexOf(op) + 1).getId());
            if (this.data.indexOf(op) != 1) {
                SqlQueryMainPart.EditPrecedence2(con, op.getId(), this.data.get(this.data.indexOf(op) - 2).getId(), this.data.get(this.data.indexOf(op) - 2).getId(), this.data.get(this.data.indexOf(op) - 1).getId());
            }

            Collections.swap(data, this.data.indexOf(op) - 1, this.data.indexOf(op));
            this.setItems(data);
        }

    }

    
    private void PrecedenceDown(Operations op) throws SQLException {
        if (this.data.indexOf(op) == this.data.size() - 1) {
            Notification.show("Vous êtes arrivés en bas de liste");
        } else if (this.data.indexOf(op) ==0) {
            SqlQueryMainPart.EditPrecedence2(con,op.getId(),this.data.get(1).getId(), op.getId(), this.data.get(1).getId());
            if (this.data.indexOf(op) !=this.data.size() - 2) {
                SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.indexOf(op)+2).getId(),op.getId(), this.data.get(this.data.indexOf(op) + 1).getId(), this.data.get(this.data.indexOf(op) + 2).getId());
            }
            Collections.swap(data, 0, 1);
            this.setItems(data);
        } else {
             SqlQueryMainPart.EditPrecedence2(con,op.getId(),this.data.get(data.indexOf(op)+1).getId(), op.getId(), this.data.get(data.indexOf(op)+1).getId());
             SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.indexOf(op) + 1).getId(), this.data.get(this.data.indexOf(op) -1).getId(),this.data.get(this.data.indexOf(op) - 1).getId(),op.getId());
             if (this.data.indexOf(op) !=this.data.size() - 2) {
             SqlQueryMainPart.EditPrecedence2(con, this.data.get(this.data.indexOf(op)+2).getId(),op.getId(), this.data.get(this.data.indexOf(op) + 1).getId(), this.data.get(this.data.indexOf(op) + 2).getId());
            }

            Collections.swap(data, this.data.indexOf(op) + 1, this.data.indexOf(op));
            this.setItems(data);       }
    }

}
