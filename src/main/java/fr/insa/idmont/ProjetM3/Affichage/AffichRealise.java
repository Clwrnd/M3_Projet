/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeProduits;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeRealise;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AffichRealise extends VerticalLayout {

    private MainView main;
    private ListeRealise TableOpM;

    public AffichRealise(MainView main) {
        this.main = main;
      
        H2 titre1 = new H2("Machine operations");
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteButton1, titre1, addButton);
        Hl1.setAlignSelf(Alignment.END, deleteButton1);
        Hl1.setAlignSelf(Alignment.CENTER, titre1);
        Hl1.setAlignSelf(Alignment.START, addButton);
        TextField RechercheRealise = new TextField("Search a product");
        RechercheRealise.setValue("Press enter");
        
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New machine operations");
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);
        
        
        
   /*
        try {
            this.TableOpM = new ListeRealise(this.main.getInfoSess().getCon(), !!!! A compléter avec la méthode getRealise (à faire) );
            this.add(Hl1, RechercheProduit, this.TableProduit);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }
        this.setAlignSelf(Alignment.CENTER, Hl1);
    */    
  
}

    
    
    private void refreshTableProduct(Connection con, List<Realise> data) throws SQLException {
        this.remove(this.TableOpM);
        this.TableOpM = new ListeRealise(con, data);
        this.add(this.TableOpM);
    }    
    
}
