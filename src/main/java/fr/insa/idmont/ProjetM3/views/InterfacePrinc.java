package fr.insa.idmont.ProjetM3.views;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.theme.lumo.Lumo;
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
        Tab TypeOp = new Tab(VaadinIcon.AUTOMATION.create(), new Span("Type d'opérations")); //2
        Tab Administration = new Tab(VaadinIcon.USER.create(), new Span("Administration")); // :3
        Tabs tabs = new Tabs(Machine, Produit, TypeOp, Administration);
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

        Span permState = new Span(this.getMain().getInfoSess().getUtilActuel().get().getAutorisation().toString());
        permState.getElement().getThemeList().add("badge primary");

        Checkbox themeToggle = new Checkbox("");
        themeToggle.addValueChangeListener(e -> {
            setTheme(e.getValue());
        });

        HorizontalLayout hl3 = new HorizontalLayout(permState, new H4("|"), VaadinIcon.MOON.create(), themeToggle);
        hl3.getStyle().set("margin-left", "auto");
        hl3.getStyle().set("margin-right", "5px");

        addToDrawer(logOutButton);
        logOutButton.setWidth(50, Unit.PIXELS);
        logOutButton.getStyle().set("margin-top", "auto");
        logOutButton.getStyle().set("margin-bottom", "5px");
        logOutButton.getStyle().set("margin-left", "5px");
        logOutButton.getStyle().set("margin-right", "auto");

        this.addToNavbar(hl3);

        // Ajout/Mise en visibilité des éléments spécifique aux autorisation      
        switch (this.main.getInfoSess().getUtilActuel().get().getAutorisation()) {
            case ADMINISTRATION:
                
                break;
            case MODIFICATION:
                break;
            case CONSULTATION:
                break;
        }

        tabs.addSelectedChangeListener((e) -> {
            this.controlleur.setContent(tabs.getSelectedIndex());
        });


    }

    private void setTheme(boolean dark) {
        var js = "document.documentElement.setAttribute('theme', $0)";

        getElement().executeJs(js, dark ? Lumo.DARK : Lumo.LIGHT);
    }

    //Get() and Set():   
    public MainView getMain() {
        return main;
    }

}
