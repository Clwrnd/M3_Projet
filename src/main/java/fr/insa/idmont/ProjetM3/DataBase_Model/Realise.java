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
    private int idMachine;
    private int idTypeOperation;
    private float duree;

    public Realise(int idMachine, int idTypeOperation) {
        this.idMachine = idMachine;
        this.idTypeOperation = idTypeOperation;
    }

    public Machines getMachine(int id) {
        return Machine;
    }

    public TypeOperations getTO() {
        return TypeOperation;
    }
    
    public void setRefM(String Ref) {
        this.Machine.setRef(Ref); 
    }
    
    public String getRefM() {
        return this.Machine.getRef();
    }
    
    public void setDesTO(String Des) {
        this.TypeOperation.setDes(Des); 
    }
    
    public String getDesTO() {
        return this.TypeOperation.getDes();
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }
    
    public void setStringDuree(String duree) {
        this.duree = Float.valueOf(duree);
    }
    
    public String getStringDuree() {
        return String.valueOf(duree);
    }

    public int getIdMachine() {
        return idMachine;
    }

    /**
     * @param TypeOperation the TypeOperation to set
     */
    public void setTypeOperation(TypeOperations TypeOperation) {
        this.TypeOperation = TypeOperation;
    }
    
    
}
