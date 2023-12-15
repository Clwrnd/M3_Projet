/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import java.io.InputStream;





/**
 *
 * @author cidmo
 */
public class LocateInPlan extends HorizontalLayout {
    private MainView main;

    public LocateInPlan(MainView main) {
        this.main = main;
      
        MemoryBuffer memoryBuffer = new MemoryBuffer();
        Upload dropEnabledUpload = new Upload(memoryBuffer);
       // dropEnabledUpload.setAcceptedFileTypes("image/jpg", "image/png");
        dropEnabledUpload.addAllFinishedListener(event -> {
        InputStream inputStream = memoryBuffer.getInputStream();
        Image image = new Image(new StreamResource(memoryBuffer.getFileName(), () -> {;
            try {
                return inputStream;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }), "alt text");
      add(image);     
}); 
        
         //Image image2 = new Image(image, "My Alt Image");
         this.add(dropEnabledUpload);
        

        
    }
    
    
}
