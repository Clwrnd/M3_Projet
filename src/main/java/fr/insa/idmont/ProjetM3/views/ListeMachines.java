/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Henry Adèle
 */
public class ListeMachines extends Grid<Machines> {
    
    Connection con;
    boolean mode; // True -> User, False -> PreUser
    
    
    
    
    // Constructeur du GRID affichant la liste des opérations;
    public ListeMachines(Connection con, List<Machines> data, boolean mode) throws SQLException {
        this.con = con;
        this.mode = mode;

        this.setSelectionMode(Grid.SelectionMode.MULTI);

        // Ajout des colonnes et des composants d'éditions:
        this.addColumn(Machines::getId).setHeader("Id");
        this.addColumn(Machines::getRef).setHeader("Ref");
        this.addColumn(Machines::getDes).setHeader("Des");
        this.addColumn(Machines::getPuissance).setHeader("Puissance");
        

        this.addComponentColumn(user -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (this.getEditor().isOpen()) {
                    this.getEditor().cancel();
                }
                this.getEditor().editItem(user);
            });
            return editButton;
        });
    
    
    Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> {
            try {
                 save();
            } catch (SQLException ex) {

            }
        });
        Button cancelBut = new Button(VaadinIcon.CLOSE.create(), e -> this.getEditor().cancel());
        cancelBut.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);
        saveBut.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);

        HorizontalLayout actions = new HorizontalLayout(saveBut, cancelBut);
        actions.setPadding(false);
        this.getColumns().get(4).setEditorComponent(actions);

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);
        this.getColumns().get(2).setSortable(true);
        this.getColumns().get(3).setSortable(true);

        this.setItems(data);

    
    
    
    
    
    
}
}