/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package fr.idmont.bdbtest;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author cidmo
 */
public class Main {
       
    public static void main(String[] args) throws SQLException {
        Initialisation.pilotCheck();
        String host = "92.222.25.165";
        String db = "m3_cidmont01";
        String user = "m3_cidmont01";
        int port = 3306;
        String pw = "52686b49";

        Connection con =  Initialisation.connectionMySQL(host, port, db, user, pw);

        boolean fin = true;
        while (fin) {
            System.out.println("Que voulez-vous faire?");
            System.out.println("1) Créer schema :");
            System.out.println("2) Supprimer schema :");
            System.out.println("3) Afficher table :");
            System.out.println("4) Ajouter machine :");
            System.out.println("5) Stop :");
            System.out.println("6) Ajouter type_operation");
            System.out.println("7) Associer machine/typeOp");
            System.out.println("8) Ajouter un produit");
            System.out.println("9) Ajouter une opération");
            System.out.println("10) Ajouter une précédence");
            int rep = Lire.i();
            switch (rep) {
                case 1:
                    SchemaManage.creates(con);
                    break;
                case 2:
                    SchemaManage.drop(con);
                    break;
                case 3:
                    DbTransaction.collecttableMachine(con);
                    break;
                case 4:
                    System.out.println("Reference:");
                    String ref = Lire.S();
                    System.out.println("Description");
                    String def = Lire.S();
                    System.out.println("Puissance");
                    int puissance = Lire.i();
                    DbTransaction.addMachine(con, ref, def, puissance);
                    break;
                case 5:
                    fin = false;
                    break;
                case 6: 
                    System.out.println("Description: ");
                    String des = Lire.S();
                    DbTransaction.addType_Operation(con, des);
                case 7:
                    DbTransaction.AssociateMachTypeOp(con);
                case 8:
                    System.out.println("Ref produit:");
                    String refP = Lire.S();
                    System.out.println("Des produit:");
                    String desP = Lire.S();
                    DbTransaction.CreateProduct(con, refP, desP);
                    break;
                case 9:
                    DbTransaction.Addoperations(con);
                    break;
               case 10:
                    DbTransaction.AddPrecedence(con);
                    break;
                default:
                    System.out.println("Commande invalide");
                    break;
            }
        }

    }
}
