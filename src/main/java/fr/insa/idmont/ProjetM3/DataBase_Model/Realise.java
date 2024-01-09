/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.DataBase_Model;

/**
 *
 * @author Simon
 */
public class Realise {
    
    private Machines Machine;
    private TypeOperations TypeOperation;
    private double duree;

    public Realise(Machines Machine, TypeOperations TypeOperation, double duree) {
        this.Machine = Machine;
        this.TypeOperation = TypeOperation;
        this.duree = duree;
    }

    

    public Machines getMachine() {
        return Machine;
    }

    public TypeOperations getTO() {
        return TypeOperation;
    }
    


    /**
     * @param TypeOperation the TypeOperation to set
     */
    public void setTypeOperation(TypeOperations TypeOperation) {
        this.TypeOperation = TypeOperation;
    }




    /**
     * @param Machine the Machine to set
     */
    public void setMachine(Machines Machine) {
        this.Machine = Machine;
    }

    /**
     * @return the duree
     */
    public double getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(double duree) {
        this.duree = duree;
    }
    
    
}
