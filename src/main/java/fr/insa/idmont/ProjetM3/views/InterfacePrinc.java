package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import fr.insa.idmont.ProjetM3.controlleur.MainContent;

public class InterfacePrinc extends AppLayout {

    private MainView main;
    private MainContent controlleur;

    // Constructeur de l'interface principale
    public InterfacePrinc(MainView mainn) {

        // Initialisation de l'AppLayout
        DrawerToggle toggle = new DrawerToggle();
        H4 title = new H4("Ligne de production");
        Tab Machine = new Tab(VaadinIcon.HAMMER.create(), new Span("Machines")); // :0  
        Tab Produit = new Tab(VaadinIcon.CART.create(), new Span("Produits"));   // :1
        Tab Administration = new Tab(VaadinIcon.USER.create(), new Span("Administration")); // :2
        Tabs tabs = new Tabs(Machine, Produit, Administration);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(tabs);
        addToNavbar(toggle, title);
        setPrimarySection(Section.DRAWER);
        getElement().getStyle().set("width", "100%");
        this.setPrimarySection(Section.NAVBAR);

        
        //Création de l'interface:
        this.main = mainn;
        this.controlleur = new MainContent(this);
        LgOutButton logOutButton = new LgOutButton(this.main);

        Button AdminBt = new Button("Admin options");
        AdminBt.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        Button locaMach = new Button("Locate machine");
        AdminBt.setVisible(false);

        Span permState = new Span(this.getMain().getInfoSess().getUtilActuel().get().getAutorisation().toString());
        permState.getElement().getThemeList().add("badge primary");
        permState.getStyle().set("margin-left", "auto");
        permState.getStyle().set("margin-right", "5px");

        this.addToNavbar(permState);

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

        tabs.addSelectedChangeListener((e) -> {
            this.controlleur.setContent(tabs.getSelectedIndex());
        });

        locaMach.addClickListener((e) -> {
            this.controlleur.GoToLocateInPlan();
        });
    }

    //Get() and Set():   
    public MainView getMain() {
        return main;
    }

}
