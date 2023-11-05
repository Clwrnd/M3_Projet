/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

/**
 *
 * @author cidmo
 */
public class Utilisateur {

    private final int id;
    private String nom;
    private String pass;
    private Autorisation autorisation;

    public Utilisateur(int id, String nom, String pass,Autorisation autorisation) {
        this.id = id;
        this.nom = nom;
        this.pass = pass;
        this.autorisation = autorisation;
    }

 
    public int getId() {
        return id;
    }

 
    public String getNom() {
        return nom;
    }

    public String getPass() {
        return pass;
    }

    /**
     * @return the autorisation
     */
    public Autorisation getAutorisation() {
        return autorisation;
    }

    /**
     * @param autorisation the autorisation to set
     */
    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }


    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    
}
