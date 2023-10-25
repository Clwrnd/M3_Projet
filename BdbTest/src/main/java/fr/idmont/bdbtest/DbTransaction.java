/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.idmont.bdbtest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author cidmo
 */
public class DbTransaction {     

    public static void collecttableMachine(Connection con)
            throws SQLException {
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Tmachine ");
            while (res.next()) {
                System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getInt(4));
            }
            System.out.println("OK");
        } catch (SQLException ex) {
            System.out.println("Table non existante");
        }
                
    }
        public static void collecttableProduit(Connection con)
            throws SQLException {
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Tproduits ");
            while (res.next()) {
                System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3));
            }
            System.out.println("OK");
        } catch (SQLException ex) {
            System.out.println("Table non existante");
        }
                
    }
        
        public static void collecttableOp(Connection con)
            throws SQLException {
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Toperations ");
            while (res.next()) {
                System.out.println(res.getInt(1) + " " + res.getInt(2) + " " + res.getInt(3));
            }
            System.out.println("OK");
        } catch (SQLException ex) {
            System.out.println("Table non existante");
        }
                
    }
        
    
             
    public static void addMachine(Connection con, String ref, String des, int puissance) throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tmachine (ref,des,puissance)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setString(1, ref);
            pt.setString(2, des);
            pt.setInt(3, puissance);
            pt.executeUpdate();
            con.commit();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {
            con.rollback();
            System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }
    }
    
    public static int ChooseTypeOp(Connection con)
            throws SQLException {
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Ttype_operation ");
            while (res.next()) {
                System.out.println(res.getInt(1) + " " + res.getString(2));
            }
            System.out.println("OK");
        } catch (SQLException ex) {
            System.out.println("Table non existante");
        }
        System.out.println("Choisissez l'id votre typeOP:");
        int i = Lire.i();
        return i;
    }
    
    public static void AssociateMachTypeOp(Connection con)throws SQLException {
        collecttableMachine(con);
        System.out.println("Choisisser l'id de la machine: ");
        int Idmachine=Lire.i();
        int idTypeOP = ChooseTypeOp(con);
        System.out.println("durée de l'opération: ");
        float duree = Lire.f();
        con.setAutoCommit(false);
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Trealise (id_machine,id_type,duree)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setInt(1, Idmachine);
            pt.setInt(2,idTypeOP );
            pt.setFloat(3, duree);
            pt.executeUpdate();
            con.commit();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {
            con.rollback();
            System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }
    }
                
    public static void addType_Operation(Connection con,String des)throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Ttype_operation (des)
                                                       VALUES (?)
                                                       """)) {
            pt.setString(1, des);
            pt.executeUpdate();
            con.commit();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {
            con.rollback();
            System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }
    }
  
    public static void CreateProduct(Connection con,String ref, String des)throws SQLException {
        con.setAutoCommit(false);
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tproduits(ref,des)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setString(1, ref);
            pt.setString(2, des);
            pt.executeUpdate();
            con.commit();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {
            con.rollback();
            System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }
    }
    
    public static void Addoperations(Connection con)throws SQLException {
        collecttableProduit(con);
        System.out.println("Choisissez l'id produit");
        int id = Lire.i();
        int idTypeOP = ChooseTypeOp(con);
         try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Toperations (idtype,id_produit)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setInt(1, id);
            pt.setInt(2,idTypeOP );
            pt.executeUpdate();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {          
          System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }              
    }
    
    public static void AddPrecedence(Connection con)throws SQLException {
        collecttableOp(con);
        System.out.println("Choisissez l'id de l'OPavant");
        int idAv = Lire.i();
        System.out.println("Choisissez l'id de l'OPprès");
        int idAp = Lire.i();
         try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tprecedence (opavant,opapres)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setInt(1, idAv);
            pt.setInt(2,idAp );
            pt.executeUpdate();
            System.out.println("Transaction réussi");
        } catch (SQLException ex) {          
          System.out.println("Transaction échec");
        } finally {
            con.setAutoCommit(true);
        }              
    }
    
    
}
