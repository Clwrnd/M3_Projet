/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Affichage;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
import fr.insa.idmont.ProjetM3.ExtendedGrid.ListeRealise;
import fr.insa.idmont.ProjetM3.View.MainView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
// Composant gérant l'affichage effectif des "opérations effectuées par les différentes machines" et des sous-composant le permettant.
public class AffichRealise extends VerticalLayout {

    private MainView main;
    private ListeRealise TableOpM;

    public AffichRealise(MainView main, boolean editAble) {
        this.main = main;

        // Création des composant et mise en place de leurs dispositions.
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

        ComboBox<Machines> rechercheViaM = new ComboBox<>("Recherche via Machine");
        ComboBox<TypeOperations> rechercheViaTO = new ComboBox<>("Recherche via TO");
        try {
            rechercheViaM.setItems(SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
            rechercheViaTO.setItems(SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()));
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, recharger la page");
        }

        HorizontalLayout RHl = new HorizontalLayout(rechercheViaM, rechercheViaTO);

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Nouvelle liaison Machine-Opération");
        ComboBox<Machines> Machine = new ComboBox<>("Machine");
        Machine.addClassName("error");
        try {
            Machine.setItems(SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
        } catch (SQLException ex) {
        }
        Button save = new Button("Confirmer");
        ComboBox<TypeOperations> TypeOperation = new ComboBox<>("Type d'opération");
        TypeOperation.addClassName("error");
        try {
            TypeOperation.setItems(SqlQueryMainPart.GetTO(this.main.getInfoSess().getCon()));
        } catch (SQLException ex) {
        }
        NumberField duree = new NumberField("Durée (heure)");
        duree.addClassName("error");
        VerticalLayout vl = new VerticalLayout(Machine, TypeOperation, duree);
        vl.setAlignSelf(Alignment.CENTER, Machine, TypeOperation, duree);
        Button cancelButton = new Button("Annuler", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);
        dialog.add(vl);

        try {
            this.TableOpM = new ListeRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()), editAble);
            this.add(Hl1, RHl, this.TableOpM);
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, veuillez réessayer");
        }

        this.setAlignSelf(Alignment.CENTER, Hl1);

        if (!editAble) {
            addButton.setEnabled(false);
            deleteButton1.setEnabled(false);
        }
        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteRealise(this.main.getInfoSess().getCon(), this.TableOpM.getSelectedItems().iterator());
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, veuillez réessayer");
            }
            try {
                refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()), editAble);
            } catch (SQLException ex) {
            }
        });

        rechercheViaM.addValueChangeListener((e) -> {
            try {
                if (rechercheViaM.isEmpty()) {
                    refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()), editAble);

                } else {
                    refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchRealiseVM(this.main.getInfoSess().getCon(), rechercheViaM.getValue().getId()), editAble);

                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, réessayer");
            }
        });

        rechercheViaTO.addValueChangeListener((e) -> {
            try {
                if (rechercheViaTO.isEmpty()) {
                    refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()), editAble);

                } else {
                    refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchRealiseVTO(this.main.getInfoSess().getCon(), rechercheViaTO.getValue().getId()), editAble);

                }
            } catch (SQLException ex) {
                Notification.show("Erreur serveur, réessayer");
            }
        });

        addButton.addClickListener((e) -> dialog.open());

        save.addClickListener((e) -> {
            // Controle de saisie.
            Machine.setHelperText(null);
            TypeOperation.setHelperText(null);
            duree.setHelperText(null);

            if (Machine.isEmpty()) {
                Machine.setHelperText("Entrez une valeur");
            } else if (TypeOperation.isEmpty()) {
                TypeOperation.setHelperText("Entrez une valeur");
            } else if (duree.isEmpty()) {
                duree.setHelperText("Entrez une valeur");
            } else {

                try {
                    if (SqlQueryMainPart.TestRealise(this.main.getInfoSess().getCon(), Machine.getValue().getId(), TypeOperation.getValue().getId(), duree.getValue())) {
                        SqlQueryMainPart.addRealise(this.main.getInfoSess().getCon(), Machine.getValue().getId(), TypeOperation.getValue().getId(), duree.getValue());
                        dialog.close();
                        refreshTableRealise(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetRealise(this.main.getInfoSess().getCon()), editAble);
                    } else {
                        Machine.setHelperText("Combinaison déja existante");
                    }
                } catch (SQLException ex) {
                    Notification.show("Erreur serveur, réessayer");
                }
            }

        });

    }

    // Méthode:
    private void refreshTableRealise(Connection con, List<Realise> data, boolean editAble) throws SQLException {

        this.remove(this.TableOpM);
        this.TableOpM = new ListeRealise(con, data, editAble);
        this.add(this.TableOpM);
    }

}
