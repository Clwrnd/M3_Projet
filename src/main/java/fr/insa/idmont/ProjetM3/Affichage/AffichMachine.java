/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeMachines;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.View.LocateInPlan;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author cidmo
 */

// Composant gérant l'affichage effectif des machines et des sous-composant le permettant.
public class AffichMachine extends VerticalLayout {

    private MainView main;
    private ListeMachines TableMachine;

    private Button save2;
    private Button ButtonLocalisation;
    private TextField desF;
    private NumberField clickXField;
    private NumberField clickYField;
    private HorizontalLayout infoBandereaux;

    private Dialog dialog2;

    private double Xc;
    private double Yc;
    private String desC;

    public AffichMachine(MainView main) {

        // Initialisation des variables permettant la récupération des données.
        this.main = main;
        this.Xc = -1;
        this.Yc = -1;
        this.desC = null;

        // Création des composant et mise en place de leurs dispositions.
        H2 titre1 = new H2("Machine");
        Button deleteButton1 = new Button(VaadinIcon.TRASH.create());
        deleteButton1.addThemeVariants(ButtonVariant.LUMO_ICON,
                ButtonVariant.LUMO_ERROR);

        Button addButton = new Button(VaadinIcon.PLUS.create());
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_SUCCESS);
        HorizontalLayout Hl1 = new HorizontalLayout(deleteButton1, titre1, addButton);
        Hl1.setAlignSelf(FlexComponent.Alignment.END, deleteButton1);
        Hl1.setAlignSelf(FlexComponent.Alignment.CENTER, titre1);
        Hl1.setAlignSelf(FlexComponent.Alignment.START, addButton);
        TextField RechercheMachine = new TextField("Rechercher une machine");
        RechercheMachine.setPlaceholder("Appuyer sur entrée");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nouvelle machine");
        TextField refAdd = new TextField("Référence");
        refAdd.addClassName("error");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        IntegerField puisAdd = new IntegerField("Puissance (W)");
        puisAdd.setMax(999999999);
        puisAdd.addClassName("error");
        this.ButtonLocalisation = new Button("Localisation", VaadinIcon.BULLSEYE.create());
        Button save = new Button("Confirmer");
        Button cancelButton = new Button("Retour", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        this.desF = new TextField("Spécifications");
        this.desF.setReadOnly(true);
        this.clickXField = new NumberField("X:");
        this.clickXField.setWidth(100, Unit.PIXELS);
        this.clickYField = new NumberField("Y:");
        this.clickYField.setWidth(100, Unit.PIXELS);
        this.clickXField.setReadOnly(true);
        this.clickYField.setReadOnly(true);
        this.infoBandereaux = new HorizontalLayout(getDesF(), getClickX(), getClickY());

        VerticalLayout Hl2 = new VerticalLayout(refAdd, desAdd, puisAdd, ButtonLocalisation, getInfo());
        infoBandereaux.setVisible(false);
        Hl2.setAlignSelf(Alignment.CENTER, refAdd, desAdd, puisAdd, ButtonLocalisation);
        dialog.add(Hl2);

        this.dialog2 = new Dialog();
        dialog2.setHeaderTitle("Localiser");
        this.save2 = new Button("Confirmer");
        Button cancelButton2 = new Button("Retour", e -> getDialog2().close());
        dialog2.getFooter().add(cancelButton2);
        dialog2.getFooter().add(save2);

        try {
            this.TableMachine = new ListeMachines(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
            this.add(Hl1, RechercheMachine, this.TableMachine);;
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, réessayer");
        }
        this.setAlignSelf(FlexComponent.Alignment.CENTER, Hl1);
        

        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteMachine(this.main.getInfoSess().getCon(), this.TableMachine.getSelectedItems().iterator());
                refreshTableMachine(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("Réessayer et vérifier qu'il n'y ai pas de contrainte sur l'élément");
            }
        });

        RechercheMachine.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableMachine(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchMachine(this.main.getInfoSess().getCon(), RechercheMachine.getValue()));
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, réessayer");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());

        save.addClickListener((e) -> {
            // Controle de saisie.
            refAdd.setHelperText(null);
            desAdd.setHelperText(null);
            puisAdd.setHelperText(null);

            if (refAdd.getValue().length() > 30 || refAdd.getValue().length() == 0) {
                refAdd.setHelperText("1-30 caractères exigés");
            } else if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 caractères exigés ");
            } else if (puisAdd.isEmpty()) {
                puisAdd.setHelperText("Entrer une valeur valide");
            } else if (this.Xc == -1 || this.Yc == -1 || this.desC == null) {
                Notification.show("Ajouter une localisation");
            } else {
                try {
                    int i = SqlQueryMainPart.TestMachine(this.main.getInfoSess().getCon(), refAdd.getValue());
                    if (i == -1) {
                        SqlQueryMainPart.addMachine(this.main.getInfoSess().getCon(), refAdd.getValue(), desAdd.getValue(), puisAdd.getValue());
                        dialog.close();
                        refreshTableMachine(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
                        this.Xc = -1;
                        this.Yc = -1;
                        this.desC = null;
                        this.infoBandereaux.setVisible(false);
                    } else {
                        refAdd.setHelperText("La machine existe déjà");
                    }
                } catch (SQLException ex) {
                    Notification.show("Erreur serveur, réessayer");
                }
            }

        });

        ButtonLocalisation.addClickListener((e) -> {
            dialog2.removeAll();
            dialog2.add(new LocateInPlan(this.main, this));
            dialog2.open();
        });

    }

    // Méthodes:
    private void refreshTableMachine(Connection con, List<Machines> data) throws SQLException {
        this.remove(this.TableMachine);
        this.TableMachine = new ListeMachines(con, data);
        this.add(this.TableMachine);
    }

    // Getteurs et Setteur.
    public Button getSave2() {
        return save2;
    }

    public TextField getDesF() {
        return desF;
    }

    public NumberField getClickX() {
        return clickXField;
    }

    public NumberField getClickY() {
        return clickYField;
    }

    public Dialog getDialog2() {
        return dialog2;
    }

    public HorizontalLayout getInfo() {
        return infoBandereaux;
    }

    public void setXc(double Xc) {
        this.Xc = Xc;
    }

    public void setYc(double Yc) {
        this.Yc = Yc;
    }

    public void setDesC(String desC) {
        this.desC = desC;
    }

}