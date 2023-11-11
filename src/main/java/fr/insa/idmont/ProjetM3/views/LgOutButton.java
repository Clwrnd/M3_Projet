package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import java.util.Optional;


public class LgOutButton extends Button{
    
    private MainView main;
    
    // Constructeur du boutton de déconnexion
    public LgOutButton(MainView main){
        this.main = main;
        this.setText("Log out");
        this.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
        ButtonVariant.LUMO_ERROR);
        
        this.addClickListener(e->{
            this.main.getInfoSess().setUtilActuel(Optional.empty());
            this.main.removeAll();
            this.main.add(new Identification(this.main));
        });
        
    }
    
            
    
    
}
