/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author cidmo
 */
public class Precedence {
    private int opAvant;
    private int opApres;

    public Precedence(int opAvant, int opApres) {
        this.opAvant = opAvant;
        this.opApres = opApres;
    }

    /**
     * @return the opAvant
     */
    public int getOpAvant() {
        return opAvant;
    }

    /**
     * @return the opApres
     */
    public int getOpApres() {
        return opApres;
    }
    
    
}
