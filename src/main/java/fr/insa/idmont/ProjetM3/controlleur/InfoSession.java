/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import java.sql.Connection;
import java.util.Optional;

/**
 *
 * @author cidmo
 */
public class InfoSession {

    private Optional<Utilisateur> utilActuel;
    private Connection con;

    public InfoSession() {
        this.utilActuel = Optional.empty();
        this.con = null;
    }

    //Get() and Set()
    public void setUtilActuel(Optional<Utilisateur> utilActuel) {
        this.utilActuel = utilActuel;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Optional<Utilisateur> getUtilActuel() {
        return utilActuel;
    }

    public Connection getCon() {
        return con;
    }

}
