/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
public class Operations {

    private int id;
    private TypeOperations typeOP;
    private Produits produit;

    public Operations(int id, TypeOperations to, Produits prod) {
        this.id = id;
        this.typeOP = to;
        this.produit = prod;
    }

    public String getDesTO() {
        return getTypeOP().getDes();
    }

    public void setDesTO(String des) {
        this.getTypeOP().setDes(des);
    }

    /**
     * @return the typeOP
     */
    public TypeOperations getTypeOP() {
        return typeOP;
    }

    /**
     * @return the produit
     */
    public Produits getProduit() {
        return produit;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    public int getIdType() {
        return this.typeOP.getId();
    }

}
