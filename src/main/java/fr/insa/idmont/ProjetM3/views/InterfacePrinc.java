package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;

public class InterfacePrinc extends VerticalLayout {

    private MainView main;
    private MainContent controlleur;

    // Constructeur de l'interface principale
    public InterfacePrinc(MainView mainn) {
        this.main = mainn;
        this.controlleur = new MainContent(this);

        Button AdminBt = new Button("Admin options");
        AdminBt.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        this.setAlignSelf(Alignment.END, AdminBt);
        AdminBt.setVisible(false);

        Span permState = new Span(this.getMain().getInfoSess().getUtilActuel().get().getAutorisation().toString());
        permState.getElement().getThemeList().add("badge primary");

        this.setAlignSelf(Alignment.START, permState);

        this.add(AdminBt, permState);

        // Ajout/Mise en visibilité des éléments spécifique aux autorisation      
        switch (this.main.getInfoSess().getUtilActuel().get().getAutorisation()) {
            case ADMINISTRATION:
                AdminBt.setVisible(true);
                break;
            case MODIFICATION:
                break;
            case CONSULTATION:
                break;
        }

        AdminBt.addClickListener((e) -> {
            this.controlleur.GoToAdminInterface();
        });

    }

    

    //Get() and Set():   
    public MainView getMain() {
        return main;
    }

}
