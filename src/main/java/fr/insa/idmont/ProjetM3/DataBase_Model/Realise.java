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
    
    private int idMachine;
    private int idTypeOperation;
    private float duree;

    public int getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    public int getIdTypeOperation() {
        return idTypeOperation;
    }

    public void setIdTypeOperation(int idTypeOperation) {
        this.idTypeOperation = idTypeOperation;
    }
    
    public void setIdStringM(String id) {
        this.idMachine = Integer.valueOf(idMachine);
    }
    
    public String getIdStringM() {
        return String.valueOf(idMachine);
    }
    
    public void setIdStringTO(String id) {
        this.idTypeOperation = Integer.valueOf(idTypeOperation);
    }
    
    public String getIdStringTO() {
        return String.valueOf(idTypeOperation);
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
}
