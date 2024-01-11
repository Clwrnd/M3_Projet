/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Controleur;

import fr.insa.idmont.ProjetM3.Affichage.AffichMachine;
import fr.insa.idmont.ProjetM3.Affichage.AffichProduit;
import fr.insa.idmont.ProjetM3.Affichage.AffichRealise;
import fr.insa.idmont.ProjetM3.Affichage.AffichTypeOp;
import fr.insa.idmont.ProjetM3.Affichage.GestionUser;
import fr.insa.idmont.ProjetM3.View.InterfacePrinc;

/**
 *
 * @author cidmo
 */
// Controleur partie principale:
public class MainContent {

    InterfacePrinc viewPrinc;

    public MainContent(InterfacePrinc view) {
        this.viewPrinc = view;
    }

    public void GoToAdminInterface() {
        this.viewPrinc.setContent(new GestionUser(this.viewPrinc.getMain()));
    }

    public void GoToProduit(boolean editAble) {
        this.viewPrinc.setContent(new AffichProduit(this.viewPrinc.getMain(),editAble));
    }

    public void GoToMachine(boolean editAble) {
        this.viewPrinc.setContent(new AffichMachine(this.viewPrinc.getMain(),editAble));
    }

    public void GoToTypeOp(boolean editAble) {
        this.viewPrinc.setContent(new AffichTypeOp(this.viewPrinc.getMain(),editAble));
    }

      public void GoToRealise(boolean editAble) {
        this.viewPrinc.setContent(new AffichRealise(this.viewPrinc.getMain(),editAble));
    }

    ;

    public void setContent(int i,boolean editAble)  {

        switch (i) {

            case (0):
                GoToMachine(editAble);
                break;
            case (1):
                GoToProduit(editAble);
                break;
            case (2):
                GoToTypeOp(editAble);
                break;
            case (3):
                GoToRealise(editAble);
                break;
            case (4):
                GoToAdminInterface();
                break;

        }
    }

}
