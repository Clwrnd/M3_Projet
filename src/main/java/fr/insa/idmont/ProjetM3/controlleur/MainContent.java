/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.AffichProduit;
import fr.insa.idmont.ProjetM3.views.GestionUser;
import fr.insa.idmont.ProjetM3.views.InterfacePrinc;
import fr.insa.idmont.ProjetM3.views.LocateInPlan;

/**
 *
 * @author cidmo
 */
// Controlleur partie principale:

public class MainContent {

    InterfacePrinc viewPrinc;

    public MainContent(InterfacePrinc view) {
        this.viewPrinc = view;
    }

    public void GoToAdminInterface() {
        this.viewPrinc.setContent(new GestionUser(this.viewPrinc.getMain()));
    }

    public void GoToLocateInPlan() {
        this.viewPrinc.setContent(new LocateInPlan(this.viewPrinc.getMain()));
    }

    public void GoToProduit() {
        this.viewPrinc.setContent(new AffichProduit(this.viewPrinc.getMain()));
    }

    ;

    public void setContent(int i) {

        switch (i) {

            case (0):
                GoToLocateInPlan();
                break;
            case (1):
                GoToProduit();
                break;
            case (2):
                GoToAdminInterface();
                break;

        }
    }

}
