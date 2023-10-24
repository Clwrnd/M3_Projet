/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa_henry.productionah;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ahenry03
 */
public class Gestion {
    
    private Connection conn;

    public Gestion(Connection conn) {
        this.conn = conn;
    }
    
    
    
     public static Connection connectGeneralMySQL(String host,
            int port, String database,
            String user, String pass)
            throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }
     
     
     
      public static Connection connectSurServeurM3() throws SQLException {
        return connectGeneralMySQL("92.222.25.165", 3306,
                "m3_ahenry01", "m3_ahenry01",
                "3eaede70");
    }
      
      
      //TP 1: créer le schéma contenant des tables
      
       public void creeSchema() throws SQLException {
        this.conn.setAutoCommit(false);
        try (Statement st = this.conn.createStatement()) {
            st.executeUpdate(
                    "create table T2machine (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) not null unique,\n"
                    + "    des varchar(30) not null\n"
                    + "    puissance integer not null"
                    + ")\n");
            
            st.executeUpdate(
                    "create table Toperations (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    idtype integer not null ,\n"
                    + "    id_produit integer not null\n"
                    + ")\n"
            );
            
             st.executeUpdate(
                    "create table Tproduits (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) not null unique,\n"
                    + "    des varchar(30) not null\n"
                    + ")\n"
            );
            
             this.conn.commit();
            
              st.executeUpdate(
                    "alter table Toperations  \n"
                    + "    add constraint fk_Toperations_produits \n"
                    + "    foreign key (id_produit) references Tproduits(id) \n"
            );
            
            System.out.println("Schéma créé!");
              
        } catch (SQLException ex) {
            System.out.println("Erreur: schéma déjà existant");
            this.conn.rollback();
        } finally {
            this.conn.setAutoCommit(true);
        }
    }
       
       
    public void supprime() throws SQLException {
        try (Statement st = this.conn.createStatement()){
           //Statement= une requète sur la base de donnée?
          
           try {
                st.executeUpdate("alter table Toperations drop constraint fk_Toperations_produits");
            } catch (SQLException ex) {
                //rien à faire, si pas de contraintes par ex
            }
           
           try {
                st.executeUpdate("drop table T2machine");
            } catch (SQLException ex) {
            }
            try {
                st.executeUpdate("drop table Toperations");
            } catch (SQLException ex) {
            }
           try {
                st.executeUpdate("drop table Tproduits");
            } catch (SQLException ex) {
            }
            System.out.println("schéma supprimé!");   
        } 
    }
      
      
    
    public static void addMachine(Connection conn, String ref, String des, int puissance ) throws SQLException {
    }
    
    
    
    
    
    
    public static void collectableMachine(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()){
            ResultSet res = st.executeQuery("SELECT * FROM T2machine");
            while (res.next()){
                System.out.println(res.getInt(1)+" "+res.getString(2)+" "+res.getString(3)+" "+res.getInt(4));
            }
            System.out.println("OK");
        } catch(SQLException ex){
            System.out.println("Table introuvable");
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
      
     public void menuPrincipal() throws SQLException {
        int rep = -1;
        while (rep != 0) {
            int i = 1;
            System.out.println("Menu principal");
            System.out.println("==============");
            System.out.println((i++) + ") supprimer schéma");
            System.out.println((i++) + ") créer schéma");
            System.out.println((i++) + ") Ajouter machine");
            System.out.println("0) Fin");
            rep=Lire.i();
            //rep = ConsoleFdB.entreeEntier("Votre choix : ");
            try {
                int j = 1;
                if (rep == j++) {
                    this.supprime();
                } else if (rep == j++) {
                    this.creeSchema();}
                else if (rep == j++){
                    
                    System.out.println("Reference:");
                    String ref = Lire.S();
                    System.out.println("Description");
                    String des = Lire.S();
                    System.out.println("Puissance");
                    int puissance = Lire.i(); 
                    addMachine(conn, ref, des, puissance);
                }   

            } catch (SQLException ex) {
                System.out.println("il y a une erreur");
            }
        }
    }
    
    
      
      
      
      
      
      
    public static void debut() {
        try (Connection con = connectSurServeurM3()) {
            System.out.println("connecté");
           Gestion gestionnaire = new Gestion(con);
           gestionnaire.menuPrincipal();
        } catch (SQLException ex) {
            throw new Error("Connection impossible", ex);
        }
    }

    public static void main(String[] args) {
        debut();
        
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }
    
    
}
