package fr.insa.idmont.ProjetM3.View;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.shared.Tooltip;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.theme.lumo.Lumo;
import fr.insa.idmont.ProjetM3.Controleur.MainContent;

// Page statique principale où seront affichés les différents éléments.
public class InterfacePrinc extends AppLayout {

    private MainView main;
    private MainContent controlleur;
    private final Checkbox themeToggle;
    private boolean editAble;

    // Constructeur de l'interface principale
    public InterfacePrinc(MainView mainn) {

        this.addClassName("x");
        this.editAble = true;

        // Initialisation de l'AppLayout            
        DrawerToggle toggle = new DrawerToggle();
        H4 title = new H4("Ligne de production");
        Tab Machine = new Tab(VaadinIcon.HAMMER.create(), new Span("Machines")); // :0  
        Tab Produit = new Tab(VaadinIcon.CART.create(), new Span("Produits"));   // :1
        Tab TypeOp = new Tab(VaadinIcon.AUTOMATION.create(), new Span("Type d'opérations")); //2
        Tab Realise = new Tab(VaadinIcon.TWIN_COL_SELECT.create(), new Span("Réalisation machine")); //3
        Tab Administration = new Tab(VaadinIcon.USER.create(), new Span("Administration")); // :4
        Tabs tabs = new Tabs(Machine, Produit, TypeOp, Realise, Administration);
        tabs.setOrientation(Tabs.Orientation.VERTICAL);

        addToDrawer(tabs);
        addToNavbar(toggle, title);
        setPrimarySection(Section.DRAWER);
        getElement().getStyle().set("width", "100%");
        this.setPrimarySection(Section.NAVBAR);
        tabs.setSelectedTab(null);

        //Création de l'interface:
        this.main = mainn;
        this.controlleur = new MainContent(this);
        LgOutButton logOutButton = new LgOutButton(this.main);

        Span permState = new Span(this.getMain().getInfoSess().getUtilActuel().get().getAutorisation().toString());
        permState.getElement().getThemeList().add("badge primary");

        this.themeToggle = new Checkbox("");
        themeToggle.addValueChangeListener(e -> {
            setTheme(e.getValue());
        });

        Span infoB = new Span(VaadinIcon.QUESTION.create());
        infoB.getElement().getThemeList().add("badge contrast");
        Tooltip tooltip = Tooltip.forComponent(infoB).withText("Aide");
        H3 descr = new H3("Voici quelques vidéos pour vous aider à utiliser l'application:");

        H4 link1 = new H4("Connexion et 1er connexion");
        link1.addClassName("link");
        link1.addClickListener((e) -> {
            getUI().get().getPage().open("https://www.youtube.com/watch?v=IOmMUjBj6Is&ab_channel=Clewrnd");
        });
        H4 link2 = new H4("Interface principale: Vue d'ensemble");
        link2.addClassName("link");
        link2.addClickListener((e) -> {
            getUI().get().getPage().open("https://www.youtube.com/watch?v=74bZaTMRrR8&ab_channel=Clewrnd");
        });

        H4 link3 = new H4("Fonctionnalités");
        link3.addClassName("link");
        link3.addClickListener((e) -> {
            getUI().get().getPage().open("https://www.youtube.com/watch?v=N0A_4hEDhUA&ab_channel=Clewrnd");
        });

        H4 link4 = new H4("Ajout-Modification-Administration");
        link4.addClassName("link");
        link4.addClickListener((e) -> {
            getUI().get().getPage().open("https://www.youtube.com/watch?v=aAnv4sC2gFI&ab_channel=Clewrnd");
        });

        H4 link5 = new H4("Compléments");
        link5.addClassName("link");
        link5.addClickListener((e) -> {
            getUI().get().getPage().open("https://www.youtube.com/watch?v=FnSzdXlZgok&ab_channel=Clewrnd");
        });

        VerticalLayout vaide = new VerticalLayout(descr, link1, link2, link3, link4, link5);
        vaide.setAlignItems(FlexComponent.Alignment.CENTER);

        Dialog infoD = new Dialog("Aide");
        infoD.add(vaide);

        HorizontalLayout hl3 = new HorizontalLayout(permState, new H4("|"), infoB, new H4("|"), VaadinIcon.MOON.create(), themeToggle);
        hl3.getStyle().set("margin-left", "auto");
        hl3.getStyle().set("margin-right", "5px");

        addToDrawer(logOutButton);
        logOutButton.setWidth(50, Unit.PIXELS);
        logOutButton.getStyle().set("margin-top", "auto");
        logOutButton.getStyle().set("margin-bottom", "5px");
        logOutButton.getStyle().set("margin-left", "5px");
        logOutButton.getStyle().set("margin-right", "auto");

        this.addToNavbar(hl3);

        // Ajout/Mise en visibilité des éléments spécifiques aux autorisations      
        switch (this.main.getInfoSess().getUtilActuel().get().getAutorisation()) {
            case ADMINISTRATION:
                break;
            case MODIFICATION:
                Administration.setVisible(false);
                Administration.setEnabled(false);
                break;
            case CONSULTATION:
                Administration.setVisible(false);
                Administration.setEnabled(false);
                editAble = false;
                break;
        }

        tabs.addSelectedChangeListener((e) -> {
            this.controlleur.setContent(tabs.getSelectedIndex(), editAble);
        });

        infoB.addClickListener((e) -> {
            infoD.open();
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
