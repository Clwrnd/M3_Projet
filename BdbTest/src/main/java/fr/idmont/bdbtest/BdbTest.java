/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package fr.idmont.bdbtest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author cidmo
 */
public class BdbTest {

    public static void pilotCheck() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Pilote trouvé");
        } catch (ClassNotFoundException ex) {
            throw new Error("Pilote introuvable");
        }
    }

    public static Connection connectionMySQL(String host, int port,
            String db, String user, String pw) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pw);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            System.out.println("Réussite de la connexcion");
            return con;
        } catch (SQLException ex) {
            System.out.println("Echec de la connexcion");
            return null;
        }
    }

    public static void query(Connection con, String ref, String des, int puissance) throws SQLException {
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

    public static void collecttable(Connection con)
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

    public static void creates(Connection con) throws SQLException {
        con.setAutoCommit(false);
        try (Statement st = con.createStatement()) {

            st.executeUpdate(
                    "create table Tmachine (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) not null unique,\n"
                    + "    des varchar(30) not null,\n"
                    + "    puissance integer not null"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table Toperations (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    idtype integer not null ,\n"
                    + "    id_produit integer not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table Tprecedence (\n"
                    + "    opavant integer not null,\n"
                    + "    opapres integer not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table Tproduits (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    ref varchar(30) not null unique,\n"
                    + "    des varchar(30) not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table Trealise (\n"
                    + "    id_machine integer not null,\n"
                    + "    id_type integer not null,\n"
                    + "    duree float not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "create table Ttype_operation (\n"
                    + "    id integer not null primary key AUTO_INCREMENT,\n"
                    + "    des varchar(30) not null\n"
                    + ")\n"
            );
            st.executeUpdate(
                    "alter table Toperations  \n"
                    + "    add constraint fk_Toperations_produits \n"
                    + "    foreign key (id_produit) references Tproduits(id) \n"
            );
            st.executeUpdate(
                    "alter table Toperations  \n"
                    + "    add constraint fk_Toperations_typeoperations \n"
                    + "    foreign key (idtype) references Ttype_operation(id) \n"
            );
            st.executeUpdate(
                    "alter table Tprecedence  \n"
                    + "    add constraint fk_Tprecedence_opavant \n"
                    + "    foreign key (opavant) references Toperations(id) \n"
            );
            st.executeUpdate(
                    "alter table Tprecedence  \n"
                    + "    add constraint fk_Tprecedence_opapres \n"
                    + "    foreign key (opapres) references Toperations(id) \n"
            );
            st.executeUpdate(
                    "alter table Trealise  \n"
                    + "    add constraint Tfk_realise_idmachine \n"
                    + "    foreign key (id_machine) references Tmachine(id) \n"
            );
            st.executeUpdate(
                    "alter table Trealise  \n"
                    + "    add constraint Tfk_realise_idtype \n"
                    + "    foreign key (id_type) references Ttype_operation(id) \n"
            );

            con.commit();
            System.out.println("Schema crée !");

        } catch (SQLException ex) {
            System.out.println("Scema deja existant.");
            con.rollback();
        } finally {
            con.setAutoCommit(true);
        }

    }

    public static void drop(Connection con) throws SQLException {

        try (Statement st = con.createStatement()) {
            
            try {st.executeUpdate("alter table Toperations drop constraint fk_Toperations_produits");} catch(SQLException ex){}
            try {st.executeUpdate("alter table Toperations drop constraint fk_Toperations_typeoperations");} catch(SQLException ex){}
            try {st.executeUpdate("alter table Tprecedence drop constraint fk_Tprecedence_opavant");} catch(SQLException ex){}
            try {st.executeUpdate("alter table Tprecedence drop constraint fk_Tprecedence_opapres");} catch(SQLException ex){}
            try {st.executeUpdate("alter table Trealise drop constraint Tfk_realise_idmachine");} catch(SQLException ex){}
            try {st.executeUpdate("alter table Trealise drop constraint Tfk_realise_idtype");} catch(SQLException ex){}
           
            try {st.executeUpdate("drop table Tmachine");} catch(SQLException ex){}
            try {st.executeUpdate("drop table Toperations");} catch(SQLException ex){}
            try {st.executeUpdate("drop table Tprecedence");} catch(SQLException ex){}
            try {st.executeUpdate("drop table Tproduits");} catch(SQLException ex){}
            try {st.executeUpdate("drop table Trealise");} catch(SQLException ex){}
            try {st.executeUpdate("drop table Ttype_operation");} catch(SQLException ex){}
        } 

    }

    public static void main(String[] args) throws SQLException {
        pilotCheck();
        String host = "92.222.25.165";
        String db = "m3_cidmont01";
        String user = "m3_cidmont01";
        int port = 3306;
        String pw = "52686b49";

        Connection con = connectionMySQL(host, port, db, user, pw);

        boolean fin = true;
        while (fin) {
            System.out.println("Que voulez-vous faire?");
            System.out.println("1) Créer schema :");
            System.out.println("2) Supprimer schema :");
            System.out.println("3) Afficher table :");
            System.out.println("4) Ajouter machine :");
            System.out.println("5) Stop :");
            int rep = Lire.i();
            switch (rep) {
                case 1:
                    creates(con);
                    break;
                case 2:
                    drop(con);
                    break;
                case 3:
                    collecttable(con);
                    break;
                case 4:
                    System.out.println("Reference:");
                    String ref = Lire.S();
                    System.out.println("Description");
                    String def = Lire.S();
                    System.out.println("Puissance");
                    int puissance = Lire.i();
                    query(con, ref, def, puissance);
                    break;
                case 5:
                    fin = false;
                    break;
                default:
                    System.out.println("Commande invalide");
                    break;
            }
        }

    }
}
