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
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.shared.Registration;
import java.io.InputStream;

/**
 *
 * @author cidmo
 */
// Interface permettant la localisation d'une machine sur un plan:
public class LocateInPlan extends HorizontalLayout {

    private MainView main;
    private Image plan;
    private AffichMachine parent;

    public LocateInPlan(MainView main, AffichMachine parent) {
        this.main = main;
        this.parent = parent;

        // Création des composants:        
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        MyUpload uploadBut = new MyUpload();
        uploadBut.setReceiver(memoryBuffer);
        uploadBut.setUploadButton(new Button("Uploader un plan"));
        uploadBut.setAcceptedFileTypes("image/JPEG", "image/png");

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
        VerticalLayout info = new VerticalLayout(des, new Hr(), uploadBut, hr1, coordinateMoving, hr2, coordinateClick);

        this.add(info);

        // Action receveur de fichier:
        uploadBut.addAllFinishedListener(event -> {
            InputStream inputStream = memoryBuffer.getInputStream();
            plan = new Image(new StreamResource(memoryBuffer.getFileName(), () -> {;
                try {
                    return inputStream;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }), "");
            add(plan);
            this.plan.addClassName("onPlan");
            coordinateMoving.setVisible(true);
            hr1.setVisible(true);
            hr2.setVisible(true);

            this.plan.getElement().addEventListener("mousemove", (e) -> {
                X.setValue(Double.valueOf(e.getEventData().getNumber("event.offsetX")));
                Y.setValue(Double.valueOf(e.getEventData().getNumber("event.offsetY")));
            }).addEventData("event.offsetX")
                    .addEventData("event.offsetY");

            this.plan.getElement().addEventListener("click", (e) -> {
                coordinateClick.setVisible(true);
                Xclick.setValue(Double.valueOf(e.getEventData().getNumber("event.offsetX")));
                Yclick.setValue(Double.valueOf(e.getEventData().getNumber("event.offsetY")));
            }).addEventData("event.offsetX")
                    .addEventData("event.offsetY");

        });

        uploadBut.addFileRemoveListener((e) -> {
            hr1.setVisible(false);
            hr2.setVisible(false);
            coordinateClick.setVisible(false);
            coordinateMoving.setVisible(false);
            this.remove(plan);
        });

        this.parent.getSave2().addClickListener((e) -> {
            des.setHelperText(null);
            if (des.isEmpty() || des.getValue().length() > 30) {
                des.setHelperText("1-30 caractères demandés");
            } else if (Xclick.isEmpty() || Yclick.isEmpty()) {
                Notification.show("Localiser la machine");
            } else {
                this.parent.getClickX().setValue(Xclick.getValue());
                this.parent.setXc(Xclick.getValue());
                this.parent.getClickY().setValue(Yclick.getValue());
                this.parent.setYc(Yclick.getValue());
                this.parent.getDesF().setValue(des.getValue());
                this.parent.setDesC(des.getValue());
                this.parent.getInfo().setVisible(true);

                this.parent.getDialog2().close();

                Xclick.setValue(null);
                Yclick.setValue(null);
                des.setValue("");
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
