/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.idmont.bdbtest;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author cidmo
 */
public class SchemaManage {
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
    
}
