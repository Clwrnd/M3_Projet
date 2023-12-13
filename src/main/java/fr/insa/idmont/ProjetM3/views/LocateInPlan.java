/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import java.io.IOException;
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
        dropEnabledUpload.setDropAllowed(true);
        dropEnabledUpload.setAcceptedFileTypes("image/jpg", "image/png");
       /*      dropEnabledUpload.addSucceededListener(event -> {
        try {
                InputStream inputStream =memoryBuffer.getInputStream();
                
                
            } catch (IOException e) {
                
            }
}); */
        
         //Image image2 = new Image(image, "My Alt Image");
         this.add(dropEnabledUpload);
        

        
    }
    
    
}
