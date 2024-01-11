/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
// Objet Operation, permmetant la gestion de cette entité SQL en java.
public class Operations {

    private int id;
    private TypeOperations typeOP;
    private Produits produit;

    public Operations(int id, TypeOperations to, Produits prod) {
        this.id = id;
        this.typeOP = to;
        this.produit = prod;
    }

    // Getteurs et Setteurs:
    public String getDesTO() {
        return getTypeOP().getDes();
    }

    public void setDesTO(String des) {
        this.getTypeOP().setDes(des);
    }

    public TypeOperations getTypeOP() {
        return typeOP;
    }

    public Produits getProduit() {
        return produit;
    }

    public int getId() {
        return id;
    }

    public int getIdType() {
        return this.typeOP.getId();
    }

}
