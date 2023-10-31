package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.idmont.ProjetM3.DataBase.Initialisation;
import fr.insa.idmont.ProjetM3.controlleur.InfoSession;
import java.sql.Connection;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private InfoSession infoSess;    
  

    public MainView() {
        this.infoSess = new InfoSession();
        
        if (Initialisation.pilotCheck()) {
           this.infoSess.setCon(Initialisation.connectionMySQLdirect());
            if (this.infoSess.getCon() != null) {
                add(new Identification(this));
            } else {
                add(new H1("Pilot found but connection failed"));
            }
        } else {
            add(new H1("Pilot not found"));
        }

    }
    
    public InfoSession getInfoSess() {
        return infoSess;
    }
    
    
}
