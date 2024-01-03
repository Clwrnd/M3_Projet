/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
public class ListeOperations extends Grid<Operations> {

    Connection con;

    // Constructeur du GRID affichant la liste des opérations;
    public ListeOperations(Connection con, List<Operations> data) throws SQLException {
        this.con = con;

        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Operations::getDesTO).setHeader("Description");

        this.addComponentColumn(user -> {
            Button upButton = new Button(VaadinIcon.ANGLE_UP.create());
            Button downButton = new Button(VaadinIcon.ANGLE_DOWN.create());

            return new HorizontalLayout(upButton, downButton);
        });

        this.getColumns().get(0).setSortable(true);

        this.setItems(data);

    }

}
