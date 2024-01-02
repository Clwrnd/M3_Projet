/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.ExtendedGrid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import static fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart.GetTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Simon
 */
public class ListeRealise extends Grid<Realise> {
    
    private Connection con;
    private TextField idTypeOperation;
    private TextField idMachine;
    private TextField duree;
    private ComboBox selecTO;
    
    public ListeRealise(Connection con, List<Realise> data) throws SQLException {
        
        this.con = con;        
        
        this.setSelectionMode(Grid.SelectionMode.MULTI);
        
        this.addColumn(Realise::getIdMachine).setHeader("Machine");
        this.addColumn(Realise::getIdTypeOperation).setHeader("Operation");
        this.addColumn(Realise::getDuree).setHeader("Durée");
        
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
        
        // Initialisation de l'éditeur et associations des composants avec ce dernier:
        this.getEditor().setBinder(new Binder<>(Realise.class));
        this.getEditor().setBuffered(true);
        
        this.idMachine = new TextField();
        this.idMachine.setWidthFull();
        this.idMachine.setClassName("error");
        this.getColumns().get(1).setEditorComponent(idMachine);
        
        this.selecTO = new ComboBox<>();
        this.selecTO.setItems(GetTO(con));
        this.selecTO.setWidthFull();
        this.getEditor().getBinder().forField(idTypeOperation).bind(Realise::getIdStringTO, Realise::setIdStringTO);
        this.getColumns().get(0).setEditorComponent(idTypeOperation);
        
        this.duree = new TextField();
        this.duree.setWidthFull();
        this.duree.setClassName("error");
        this.getEditor().getBinder().forField(duree).bind(Realise::getStringDuree, Realise::setStringDuree);
        this.getColumns().get(1).setEditorComponent(duree);

        Button saveBut = new Button(VaadinIcon.CHECK.create(), e -> {try {
            save();
            } catch (SQLException ex) {
                Logger.getLogger(ListeRealise.class.getName()).log(Level.SEVERE, null, ex);
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
        this.getColumns().get(3).setSortable(true);

        this.setItems(data);

    }
        
        
    private void save() throws SQLException {
        int i = SqlQueryMainPart.TestRealise(con, this.idMachine.getValue());
        if (i == Integer.valueOf(this.idMachine.getValue()) || i == -1) {
            SqlQueryMainPart.EditRealise(con, Integer.parseInt(this.idTypeOperation.getValue()), Float.valueOf(this.duree.getValue()), Integer.parseInt(this.idMachine.getValue()));
            this.getEditor().save();
        } else {
            this.idMachine.setHelperText("Machine already exists");
        }
    }
     
    }
    

