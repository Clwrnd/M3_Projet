package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;

public class InterfacePrinc extends VerticalLayout {

    private MainView main;
    Button AdminBt;
    MainContent controlleur;

    public InterfacePrinc(MainView mainn) {
        this.main = mainn;
        this.controlleur = new MainContent(this);

        this.AdminBt = new Button("Admin options");
        this.AdminBt.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        this.setAlignSelf(Alignment.END, this.AdminBt);
        this.AdminBt.setVisible(false);

        Span permState = new Span(this.getMain().getInfoSess().getUtilActuel().get().getAutorisation().toString());
        permState.getElement().getThemeList().add("badge primary");

        this.setAlignSelf(Alignment.START, permState);

        this.add(this.AdminBt, permState);

        // Ajout/Mise en visibilité des éléments spécifique aux autorisation      
        switch (this.main.getInfoSess().getUtilActuel().get().getAutorisation()) {
            case ADMINISTRATION:
                this.AdminBt.setVisible(true);
                break;
            case MODIFICATION:
                break;
            case CONSULTATION:
                break;
        }
        
        this.AdminBt.addClickListener((e)->{        
           this.controlleur.GoToAdminOp(); 
        });

    }

    /**
     * @return the main
     */
    public MainView getMain() {
        return main;
    }

}
