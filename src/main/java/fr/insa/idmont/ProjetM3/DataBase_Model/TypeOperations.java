/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
// Objet Type Operations, permmetant la gestion de cette entité SQL en java.
public class TypeOperations {

    private int id;
    private String des;

    public TypeOperations(int aInt) {
        this.id = aInt;
    }

    // Méthode héritées modifié:
    @Override
    public String toString() {
        return des;
    }

    // Getteurs et Setteurs:
    public TypeOperations(int id, String des) {
        this.id = id;
        this.des = des;
    }

    public TypeOperations() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

}
