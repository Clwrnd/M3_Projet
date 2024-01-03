/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
public class Machines {

    private int id;
    private String ref;
    private String des;
    private int puissance;
    private String DesignationPlan;
    private int X;
    private int Y;

    public Machines(int id, String ref, String des, int puissance) {
        this.id = id;
        this.ref = ref;
        this.des = des;
        this.puissance = puissance;
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
     * @return the ref
     */
    public String getRef() {
        return ref;
    }

    /**
     * @param ref the ref to set
     */
    public void setRef(String ref) {
        this.ref = ref;
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

    /**
     * @return the puissance
     */
    public int getPuissance() {
        return puissance;
    }

    /**
     * @param puissance the puissance to set
     */
    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public void setIdString(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    /**
     * @return the DesignationPlan
     */
    public String getDesignationPlan() {
        return DesignationPlan;
    }

    /**
     * @param DesignationPlan the DesignationPlan to set
     */
    public void setDesignationPlan(String DesignationPlan) {
        this.DesignationPlan = DesignationPlan;
    }

    /**
     * @return the X
     */
    public int getX() {
        return X;
    }

    /**
     * @param X the X to set
     */
    public void setX(int X) {
        this.X = X;
    }

    /**
     * @return the Y
     */
    public int getY() {
        return Y;
    }

    /**
     * @param Y the Y to set
     */
    public void setY(int Y) {
        this.Y = Y;
    }

    public String getLoc() {
        return (this.DesignationPlan + ": " + String.valueOf(this.X) + " - " + String.valueOf(this.Y));
    }

    public void setLoc(String desPlan, int X, int Y) {
        this.DesignationPlan = desPlan;
        this.X = X;
        this.Y = Y;
    }

}
