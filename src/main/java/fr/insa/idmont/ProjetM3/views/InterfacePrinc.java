package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;




public class InterfacePrinc extends HorizontalLayout{
    MainView main;
    
    
    public InterfacePrinc(MainView main) {
        this.add(new H1("Ca marche !"));
    }

}
 