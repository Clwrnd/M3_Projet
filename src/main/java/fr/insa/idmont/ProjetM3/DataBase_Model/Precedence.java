/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author cidmo
 */
// Objet Precedence, permmetant la gestion de cette entité SQL en java.
public class Precedence {

    private int opAvant;
    private int opApres;

    public Precedence(int opAvant, int opApres) {
        this.opAvant = opAvant;
        this.opApres = opApres;
    }

    // Getteurs et Setteurs:
    public int getOpAvant() {
        return opAvant;
    }

    public int getOpApres() {
        return opApres;
    }

}
