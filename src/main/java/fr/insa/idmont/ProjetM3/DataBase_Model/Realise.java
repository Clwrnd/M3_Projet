/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Simon
 */
// Objet Realise, permmetant la gestion de cette entité SQL en java.
public class Realise {

    private Machines Machine;
    private TypeOperations TypeOperation;
    private double duree;

    public Realise(Machines Machine, TypeOperations TypeOperation, double duree) {
        this.Machine = Machine;
        this.TypeOperation = TypeOperation;
        this.duree = duree;
    }

    // Getteurs et Setteurs  
    public Machines getMachine() {
        return Machine;
    }

    public TypeOperations getTO() {
        return TypeOperation;
    }

    public void setTypeOperation(TypeOperations TypeOperation) {
        this.TypeOperation = TypeOperation;
    }

    public void setMachine(Machines Machine) {
        this.Machine = Machine;
    }

    public double getDuree() {
        return duree;
    }

    public void setDuree(double duree) {
        this.duree = duree;
    }

}
