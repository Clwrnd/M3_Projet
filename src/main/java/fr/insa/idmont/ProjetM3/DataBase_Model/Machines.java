/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Henry Adèle
 */
// Objet Machine, permmetant la gestion de cette entité SQL en java.
public class Machines {

    private int id;
    private String ref;
    private String des;
    private int puissance;
    private Plan Plan;
    private int X;
    private int Y;

    public Machines(int id, String ref, String des, int puissance, int idplan, int X, int Y) {
        this.id = id;
        this.ref = ref;
        this.des = des;
        this.puissance = puissance;
        this.Plan = new Plan(idplan);
        this.X = X;
        this.Y = Y;
    }

    public Machines(int id) {
        this.id = id;
    }

    // Getteurs et Setteurs:
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

    public int getPuissance() {
        return puissance;
    }

    public void setPuissance(int puissance) {
        this.puissance = puissance;
    }

    public void setIdString(String id) {
        this.id = Integer.valueOf(id);
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    public Plan getPlan() {
        return Plan;
    }

    public void setPlan(Plan Plan) {
        this.Plan = Plan;
    }

    public String getPlanDes() {
        return this.Plan.getDes();
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }

    // Méthodes héritées modifé.
    @Override
    public String toString() {
        return ref;
    }

}
