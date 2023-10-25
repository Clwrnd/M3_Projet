package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import fr.insa.idmont.ProjetM3.DataBase.Initialisation;
import fr.insa.idmont.ProjetM3.controlleur.Connexion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@PageTitle("Main")
@Route(value = "")
public class Identification extends HorizontalLayout {

    private TextField username;
    private PasswordField pwEntry;
    private Connection con;
    private Button validate;
    private Connexion controlleur;

    public Identification() {
    
        this.controlleur = new Connexion(this);
        Paragraph state =new Paragraph();  
        state.getStyle().set("white-space", "pre-line");
        if(Initialisation.pilotCheck()){

            this.con= Initialisation.connectionMySQLdirect();
            if(con!=null){
                state.setText("Pilot found \n Successful connection");
            } else {
               state.setText("Pilot found \n Connection failed"); 
            }
        } else {
              state.setText("Pilot not found");
        }
        
       VerticalLayout vLay = new VerticalLayout();        
        
        this.validate= new Button("Connection");
        this.validate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.pwEntry = new PasswordField();
        this.pwEntry.setLabel("Password");

        username = new TextField("Username");

        vLay.add(new H1("Gestionnaire de ligne de production"),username, pwEntry,validate,state);
        vLay.setAlignItems(Alignment.CENTER);

        add(vLay);
        
        this.validate.addClickListener((e)->{
            try {
                if(this.controlleur.TestiD()){
                    System.out.println("Oui");
                } else{          
                    System.out.println("Non");
                }
            } catch (SQLException ex) {
            }
        });
    }

    /**
     * @return the username
     */
    public TextField getUsername() {
        return username;
    }

    /**
     * @return the pwEntry
     */
    public PasswordField getPwEntry() {
        return pwEntry;
    }

    /**
     * @return the con
     */
    public Connection getCon() {
        return con;
    }
    

}
