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

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    private final int id;
    private String nom;
    private String pass;
    private Autorisation autorisation;

    public Utilisateur(int id, String nom, String pass, Autorisation autorisation) {
        this.id = id;
        this.nom = nom;
        this.pass = pass;
        this.autorisation = autorisation;
    }
    
    //Get() and Set()
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPass() {
        return pass;
    }


    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
