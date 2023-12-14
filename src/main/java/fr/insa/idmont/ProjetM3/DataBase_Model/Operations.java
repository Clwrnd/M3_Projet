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
    private int idtype;
    private int idproduit;

    public Operations(int id, int idtype, int idproduit) {
        this.id = id;
        this.idtype = idtype;
        this.idproduit = idproduit;
    }   

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idtype
     */
    public int getIdtype() {
        return idtype;
    }

    /**
     * @param idtype the idtype to set
     */
    public void setIdtype(int idtype) {
        this.idtype = idtype;
    }

    /**
     * @return the idproduit
     */
    public int getIdproduit() {
        return idproduit;
    }

    /**
     * @param idproduit the idproduit to set
     */
    public void setIdproduit(int idproduit) {
        this.idproduit = idproduit;
    }
    
    
    

    
}
