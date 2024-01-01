/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

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
import fr.insa.idmont.ProjetM3.controlleur.SqlQueryMainPart;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AffichMachine extends VerticalLayout {

    private MainView main;
    private ListeMachines TableMachine;
    private Button save2;
    private TextField des;
    private NumberField clickX;
    private NumberField clickY;
    private Dialog dialog2;
    private HorizontalLayout info;
    private Button locateBut;
    private double Xc;
    private double Yc;
    private String desC;

    public AffichMachine(MainView main) {
        this.main = main;
        this.Xc = -1;
        this.Yc = -1;
        this.desC = null;

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
        TextField RechercheMachine = new TextField("Search a machine");
        RechercheMachine.setValue("Press enter");

        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("New Machine");
        TextField refAdd = new TextField("Référence");
        refAdd.addClassName("error");
        TextField desAdd = new TextField("Description");
        desAdd.addClassName("error");
        IntegerField puisAdd = new IntegerField("Puissance (W)");
        puisAdd.setMax(999999999);
        puisAdd.addClassName("error");
        this.locateBut = new Button("Localisation", VaadinIcon.BULLSEYE.create());
        Button save = new Button("Confirm");
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        dialog.getFooter().add(cancelButton);
        dialog.getFooter().add(save);

        this.des = new TextField("Specification");
        this.des.setReadOnly(true);
        this.clickX = new NumberField("X:");
        this.clickX.setWidth(100, Unit.PIXELS);
        this.clickY = new NumberField("Y:");
        this.clickY.setWidth(100, Unit.PIXELS);
        this.clickX.setReadOnly(true);
        this.clickY.setReadOnly(true);
        this.info = new HorizontalLayout(getDes(), getClickX(), getClickY());

        VerticalLayout Hl2 = new VerticalLayout(refAdd, desAdd, puisAdd, locateBut, getInfo());
        info.setVisible(false);
        Hl2.setAlignSelf(Alignment.CENTER, refAdd, desAdd, puisAdd, locateBut);
        dialog.add(Hl2);

        this.dialog2 = new Dialog();
        dialog2.setHeaderTitle("Locate");
        this.save2 = new Button("Confirm");
        Button cancelButton2 = new Button("Cancel", e -> getDialog2().close());
        dialog2.getFooter().add(cancelButton2);
        dialog2.getFooter().add(save2);

        try {
            this.TableMachine = new ListeMachines(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
            this.add(Hl1, RechercheMachine, this.TableMachine);;
        } catch (SQLException ex) {
            Notification.show("Server error, try again");
        }
        this.setAlignSelf(FlexComponent.Alignment.CENTER, Hl1);

        //Actions des composants:
        deleteButton1.addClickListener((e) -> {
            try {
                SqlQueryMainPart.deleteMachine(this.main.getInfoSess().getCon(), this.TableMachine.getSelectedItems().iterator());
                refreshTableMachine(this.main.getInfoSess().getCon(), SqlQueryMainPart.GetMachine(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("Try again and verify there is no constraint on this item");
            }
        });

        RechercheMachine.addKeyPressListener(Key.ENTER, (e) -> {
            try {
                refreshTableMachine(this.main.getInfoSess().getCon(), SqlQueryMainPart.SearchMachine(this.main.getInfoSess().getCon(), RechercheMachine.getValue()));
            } catch (SQLException ex) {
                Notification.show("server error, try again");

            }
            ;
        });

        addButton.addClickListener((e) -> dialog.open());

        save.addClickListener((e) -> {
            refAdd.setHelperText(null);
            desAdd.setHelperText(null);
            puisAdd.setHelperText(null);

            if (refAdd.getValue().length() > 30 || refAdd.getValue().length() == 0) {
                refAdd.setHelperText("1-30 characters exiged");
            } else if (desAdd.getValue().length() > 30 || desAdd.getValue().length() == 0) {
                desAdd.setHelperText("1-30 characters exiged ");
            } else if (puisAdd.isEmpty()) {
                puisAdd.setHelperText("Enter a valid value");
            } else if (this.Xc == -1 || this.Yc == -1 || this.desC == null) {
                Notification.show("Add a location !");
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
                        this.info.setVisible(false);
                    } else {
                        refAdd.setHelperText("Machine already exists");
                    }
                } catch (SQLException ex) {
                    Notification.show("Server error, try again");
                }
            }

        });

        locateBut.addClickListener((e) -> {
            dialog2.removeAll();
            dialog2.add(new LocateInPlan(this.main, this));
            dialog2.open();
        });

    }

    private void refreshTableMachine(Connection con, List<Machines> data) throws SQLException {
        this.remove(this.TableMachine);
        this.TableMachine = new ListeMachines(con, data);
        this.add(this.TableMachine);
    }

    /**
     * @return the save2
     */
    public Button getSave2() {
        return save2;
    }

    /**
     * @return the des
     */
    public TextField getDes() {
        return des;
    }

    /**
     * @return the clickX
     */
    public NumberField getClickX() {
        return clickX;
    }

    /**
     * @return the clickY
     */
    public NumberField getClickY() {
        return clickY;
    }

    /**
     * @return the dialog2
     */
    public Dialog getDialog2() {
        return dialog2;
    }

    /**
     * @return the info
     */
    public HorizontalLayout getInfo() {
        return info;
    }

    /**
     * @param Xc the Xc to set
     */
    public void setXc(double Xc) {
        this.Xc = Xc;
    }

    /**
     * @param Yc the Yc to set
     */
    public void setYc(double Yc) {
        this.Yc = Yc;
    }

    /**
     * @param desC the desC to set
     */
    public void setDesC(String desC) {
        this.desC = desC;
    }

}
