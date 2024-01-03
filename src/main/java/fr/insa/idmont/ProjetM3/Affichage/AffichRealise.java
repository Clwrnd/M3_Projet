/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.Key;
import static com.vaadin.flow.component.Tag.A;

import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeRealise;
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
import static fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart.SearchRealise;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import static fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart.SearchRealise;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeRealise;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cidmo
 */
public class AffichRealise extends VerticalLayout {

    private MainView main;
    private ListeRealise TableOpM;

    public AffichRealise(MainView main) throws SQLException {
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
        RechercheRealise.setPlaceholder("Press enter");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New machine operations");
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);
        
        this.TableOpM = new ListeRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()) );
        this.add(Hl1, RechercheRealise, this.TableOpM);
        ;
        this.setAlignSelf(Alignment.CENTER, Hl1);
      
        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteRealise(this.main.getInfoSess().getCon(), this.TableOpM.getSelectedItems().iterator());
            } catch (SQLException ex) {
                Logger.getLogger(AffichRealise.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Logger.getLogger(AffichRealise.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        /*
        RechercheRealise.addKeyPressListener(Key.ENTER, (e) -> {
            refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchMachine(this.main.getInfoSess().getCon(), SearchRealise.getValue()));
        });
        */
    }
    
    
    private void refreshTableRealise(Connection con, List<Realise> data) throws SQLException {

        this.remove(this.TableOpM);
        this.TableOpM = new ListeRealise(con, data);
        this.add(this.TableOpM);
    }

}
