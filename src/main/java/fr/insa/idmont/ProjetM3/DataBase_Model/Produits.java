/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
// Objet Produit, permmetant la gestion de cette entité SQL en java.
public class Produits {

    private int id;
    private String ref;
    private String des;

    public Produits(int id, String ref, String des) {
        this.id = id;
        this.ref = ref;
        this.des = des;
    }

    public Produits() {
    }

    // Get() et Set():
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setIdString(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getIdString() {
        return String.valueOf(id);
    }

}
