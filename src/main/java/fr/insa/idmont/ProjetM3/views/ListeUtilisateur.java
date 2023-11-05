/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import fr.insa.idmont.ProjetM3.controlleur.Utilisateur;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class ListeUtilisateur extends Grid<Utilisateur> {

    public ListeUtilisateur(List<Utilisateur> data) {
        this.setSelectionMode(Grid.SelectionMode.MULTI);

        this.addColumn(Utilisateur::getId).setHeader("Id");
        this.addColumn(Utilisateur::getNom).setHeader("Username");
        this.addColumn(Utilisateur::getPass).setHeader("Passwrod");
        this.addColumn(Utilisateur::getAutorisation).setHeader("Permissions");
        
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
        
        this.getEditor().setBinder(new Binder<>(Utilisateur.class));
        this.getEditor().setBuffered(true);

        TextField userField = new TextField();
        userField.setWidthFull();;
        this.getEditor().getBinder().forField(userField).bind(Utilisateur::getNom,Utilisateur::setNom);
        this.getColumns().get(1).setEditorComponent(userField);
        

        this.getColumns().get(0).setSortable(true);
        this.getColumns().get(1).setSortable(true);
        this.getColumns().get(3).setSortable(true);

        this.setItems(data);

    }

}
