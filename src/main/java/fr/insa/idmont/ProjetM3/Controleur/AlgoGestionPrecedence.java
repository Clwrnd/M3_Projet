/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Controleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import fr.insa.idmont.ProjetM3.DataBase_Model.Precedence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author cidmo
 */
public class AlgoGestionPrecedence {

    public static List<Integer> Tri(List<Precedence> atrier) {
        int i;
        ArrayList<Integer> list = new ArrayList<>();
        list.add(atrier.get(0).getOpAvant());
        list.add(atrier.get(0).getOpApres());
        while (list.size() != SizePrecedence(atrier)) {
            for (i = 1; i < atrier.size(); i++) {
                if (list.contains(atrier.get(i).getOpAvant()) && list.contains(atrier.get(i).getOpApres())) {
                    // C'est bon ne rien faire
                } else if (list.contains(atrier.get(i).getOpAvant()) && (!list.contains(atrier.get(i).getOpApres()))) {
                    list.add(list.indexOf(atrier.get(i).getOpAvant()) + 1, atrier.get(i).getOpApres());
                } else if (list.contains(atrier.get(i).getOpApres()) && (!list.contains(atrier.get(i).getOpAvant()))) {
                    if (list.indexOf(atrier.get(i).getOpApres()) == 0) {
                        list.add(0, atrier.get(i).getOpAvant());
                    } else {
                        list.add(list.indexOf(atrier.get(i).getOpApres()) - 1, atrier.get(i).getOpAvant());
                    }
                } else {
                    // Ne rien faire (reboucler)
                }
            }
            for(Integer a :list){
                System.out.println("test "+ a);
            }
            
        }
        return list;
    }
    
    public static int SizePrecedence(List<Precedence> atrier) {
        int i = 0;
        ArrayList<Integer> dejaVu = new ArrayList<>();
        for (Precedence pred : atrier) {
            if (dejaVu.contains(pred.getOpAvant())) {
                // ne rien faire
            } else {
                i++;
                dejaVu.add(pred.getOpAvant());
            }
            if (dejaVu.contains(pred.getOpApres())) {
                // ne rien faire
            } else {
                i++;
                dejaVu.add(pred.getOpApres());
            }
        }
        return i;
    }
    
    public static ArrayList<Operations> Ordre(List<Operations> partielle, List<Integer> complet) {   
        int a;
        boolean test;
        ArrayList<Operations> finL = new ArrayList<>();
        for (Integer i : complet) {
            test=true;
            a=0;
             while(test && a<partielle.size()) {
                if (partielle.get(a).getId() == i){   
                    finL.add(partielle.get(a));
                    test= false;
                }
                a++;
            }
        }        
      return finL; 
    }
    
     



    
    
    
    
}
