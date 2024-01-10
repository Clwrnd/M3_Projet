/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author cidmo
 */
// Objet Utilisateur, permmetant la gestion de cette entité SQL en java et la passation de données sur la session/utilisateur actuel
public class Utilisateur {

    private int id;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setIdString(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getIdString() {
        return String.valueOf(id);
    }

}
