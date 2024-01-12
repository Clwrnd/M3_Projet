/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

import com.vaadin.flow.component.html.Image;

/**
 *
 * @author cidmo
 */
public class Plan {

    private Image plan;
    private String des;
    private int idP;

    public Plan(Image plan, String des, int id) {
        this.plan = plan;
        this.des = des;
        this.idP = id;
    }

    public Plan(int idP) {
        this.idP = idP;
    }

    // Méthode héritée modifé:
    @Override
    public String toString() {
        return getDes();
    }

    //Get et Set:
    public Image getPlan() {
        return plan;
    }

    /**
     * @return the idP
     */
    public int getIdP() {
        return idP;
    }

    /**
     * @return the des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des the des to set
     */
    public void setDes(String des) {
        this.des = des;
    }

}
