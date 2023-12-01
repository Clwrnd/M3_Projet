/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.views.GestionUser;
import fr.insa.idmont.ProjetM3.views.InterfacePrinc;

/**
 *
 * @author cidmo
 */
public class MainContent {

    InterfacePrinc viewPrinc;

    public MainContent(InterfacePrinc view) {
        this.viewPrinc = view;
    }

    public void GoToAdminInterface() {
        this.viewPrinc.getMain().removeAll();
        this.viewPrinc.getMain().add(new GestionUser(this.viewPrinc.getMain()));
    }
    
   

}
