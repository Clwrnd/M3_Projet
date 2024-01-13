/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.View;

import fr.insa.idmont.ProjetM3.Affichage.AffichMachine;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.shared.Registration;
import fr.insa.idmont.ProjetM3.Controleur.SqlQueryMainPart;
import fr.insa.idmont.ProjetM3.DataBase_Model.Plan;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author cidmo
 */
// Interface permettant la localisation d'une machine sur un plan:
public class LocateInPlan extends HorizontalLayout {

    private MainView main;
    private Image plan;
    private AffichMachine parent;
    private File file;

    public LocateInPlan(MainView main, AffichMachine parent) {
        this.main = main;
        this.parent = parent;

        // Création des composants:        
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        MyUpload uploadBut = new MyUpload();
        uploadBut.setReceiver(memoryBuffer);
        uploadBut.setUploadButton(new Button("Uploader un plan"));
        uploadBut.setAcceptedFileTypes("image/JPEG", "image/png");
        uploadBut.setMaxFiles(0);

        NumberField X = new NumberField("Position X:");
        X.setWidth(100, Unit.PIXELS);
        NumberField Y = new NumberField("Positon Y:");
        Y.setWidth(100, Unit.PIXELS);
        X.setReadOnly(true);
        Y.setReadOnly(true);

        NumberField Xclick = new NumberField("Clic X:");
        Xclick.setWidth(100, Unit.PIXELS);
        NumberField Yclick = new NumberField("Clic Y:");
        Yclick.setWidth(100, Unit.PIXELS);
        Xclick.setReadOnly(true);
        Yclick.setReadOnly(true);

        HorizontalLayout coordinateMoving = new HorizontalLayout(X, Y);
        HorizontalLayout coordinateClick = new HorizontalLayout(Xclick, Yclick);
        coordinateClick.setVisible(false);
        coordinateMoving.setVisible(false);
        TextField des = new TextField("Localisation ");
        des.addClassName("error");
        Hr hr1 = new Hr();
        hr1.setVisible(false);
        Hr hr2 = new Hr();
        hr2.setVisible(false);

        ComboBox<Plan> choixPlan = new ComboBox<>();
        Button deleteButton3 = new Button(VaadinIcon.TRASH.create());
        deleteButton3.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_ICON);
        HorizontalLayout hl3 = new HorizontalLayout(choixPlan, deleteButton3);
        try {
            choixPlan.setItems(SqlQueryMainPart.getPlan(this.main.getInfoSess().getCon()));
        } catch (SQLException ex) {
            Notification.show("Erreur serveur, réessayer");
        }

        VerticalLayout info = new VerticalLayout(des, uploadBut, new Hr(), hr1, coordinateMoving, hr2, coordinateClick, new H3("Plan récemment utilisé:"), hl3);

        this.add(info);

        // Activation de l'uploadeur
        des.addValueChangeListener((e) -> {
            if ((des.isEmpty() || des.getValue().length() > 30)) {
                uploadBut.setMaxFiles(0);
            } else {
                uploadBut.setMaxFiles(1);
            }
        });

        // Action receveur de fichier:
        uploadBut.addAllFinishedListener(event -> {
            InputStream inputStream = memoryBuffer.getInputStream();
            try {
                file = new File("test.png");
                FileUtils.copyInputStreamToFile(inputStream, file);
                SqlQueryMainPart.addPlan(this.main.getInfoSess().getCon(), des.getValue(), file);
                choixPlan.setItems(SqlQueryMainPart.getPlan(this.main.getInfoSess().getCon()));
            } catch (Exception ex) {
                Notification.show("Erreur serveur, réessayer");
            }
        });

        // Affichage du plan correspondant
        choixPlan.addValueChangeListener((e) -> {
            if (plan != null) {
                this.remove(plan);
            }
            if (!choixPlan.isEmpty()) {
                plan = choixPlan.getValue().getPlan();
                add(plan);
                this.plan.addClassName("onPlan");
                coordinateMoving.setVisible(true);
                hr1.setVisible(true);
                hr2.setVisible(true);

                this.plan.getElement().addEventListener("mousemove", (m) -> {
                    X.setValue(Double.valueOf(m.getEventData().getNumber("event.offsetX")));
                    Y.setValue(Double.valueOf(m.getEventData().getNumber("event.offsetY")));
                }).addEventData("event.offsetX")
                        .addEventData("event.offsetY");

                this.plan.getElement().addEventListener("click", (n) -> {
                    coordinateClick.setVisible(true);
                    Xclick.setValue(Double.valueOf(n.getEventData().getNumber("event.offsetX")));
                    Yclick.setValue(Double.valueOf(n.getEventData().getNumber("event.offsetY")));
                }).addEventData("event.offsetX")
                        .addEventData("event.offsetY");
            }

        });

        uploadBut.addFileRemoveListener((e) -> {
            hr1.setVisible(false);
            hr2.setVisible(false);
            coordinateClick.setVisible(false);
            coordinateMoving.setVisible(false);
            if (plan != null) {
                this.remove(plan);
            }
        });

        this.parent.getSave2().addClickListener((e) -> {
            des.setHelperText(null);
            if (Xclick.isEmpty() || Yclick.isEmpty()) {
                des.setHelperText("Localiser la machine sur le plan:");
            } else {
                this.parent.getClickX().setValue(Xclick.getValue());
                double a = Xclick.getValue();
                this.parent.setXc((int) a);
                this.parent.getClickY().setValue(Yclick.getValue());
                double b = Yclick.getValue();
                this.parent.setYc((int) b);
                this.parent.getDesF().setValue(choixPlan.getValue().getDes());
                this.parent.setDesC(choixPlan.getValue().getIdP());
                this.parent.getInfo().setVisible(true);

                this.parent.getDialog2().close();

                Xclick.setValue(null);
                Yclick.setValue(null);
                des.setValue("");
            }
        });

        deleteButton3.addClickListener((e) -> {
            try {
                coordinateClick.setVisible(false);
                coordinateMoving.setVisible(false);
                SqlQueryMainPart.DeletePlan(this.main.getInfoSess().getCon(), choixPlan.getValue().getIdP());
                choixPlan.setItems(SqlQueryMainPart.getPlan(this.main.getInfoSess().getCon()));
            } catch (SQLException ex) {
                Notification.show("Réessayer et vérifier qu'il n'y ai pas de contrainte sur l'élément");
            }
        });

    }

    class MyUpload extends Upload {

        Registration addFileRemoveListener(ComponentEventListener<FileRemoveEvent> listener) {
            return super.addListener(FileRemoveEvent.class, listener);
        }
    }

    @DomEvent("file-remove")
    public static class FileRemoveEvent extends ComponentEvent<Upload> {

        public FileRemoveEvent(Upload source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}
