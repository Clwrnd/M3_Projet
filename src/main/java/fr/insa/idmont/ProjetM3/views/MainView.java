package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.idmont.ProjetM3.DataBase.Initialisation;
import java.sql.Connection;

@PageTitle("Main")
@Route(value = "")
public class MainView extends HorizontalLayout {

    private Connection con;
    private Identification StartPage;

    public MainView() {
        if (Initialisation.pilotCheck()) {
            con = Initialisation.connectionMySQLdirect();
            if (con != null) {
                this.StartPage = new Identification(this);
                add(this.StartPage);
            } else {
                add(new H1("Pilot found but connection failed"));
            }
        } else {
            add(new H1("Pilot not found"));
        }

    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }

}
