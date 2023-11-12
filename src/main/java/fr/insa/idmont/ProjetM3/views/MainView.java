package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.idmont.ProjetM3.DataBase_Model.Initialisation;
import fr.insa.idmont.ProjetM3.DataBase_Model.InfoSession;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private InfoSession infoSess;

    public MainView() {
        this.infoSess = new InfoSession();

        // Vérification de la présence du pilote et de la réussite de la connexion, cas contraire -> message d'erreur
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

    // Get() and Set() :
    public InfoSession getInfoSess() {
        return infoSess;
    }

}
