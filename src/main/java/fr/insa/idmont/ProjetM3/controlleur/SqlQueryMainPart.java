/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.controlleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Produits;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author cidmo
 */
// Requête partie principale:
public class SqlQueryMainPart {

    public static List<Produits> GetProduit(Connection con) {
        ArrayList<Produits> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Tproduits  ");
            while (res.next()) {
                liste.add(new Produits(res.getInt(1), res.getString(2), res.getString(3)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public static int TestProduit(Connection con, String prod) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Tproduits"
                + " where username = ?")) {
            pst.setString(1, prod);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException ex) {
            return -1;
        }
    }

    public static void EditProduct(Connection con, String newRef, String newDes, int id) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Tproduits  "
                + " set ref=?,des=?"
                + " where id = ?")) {
            pst.setString(1, newRef);
            pst.setString(2, newDes);
            pst.setInt(3, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static void deleteProd(Connection con, Iterator<Produits> iterator) {
        Iterator<Produits> it = iterator;
        while (it.hasNext()) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tproduits"
                    + " where id = ?")) {
                pst.setInt(1, iterator.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {

            }
        }
    }

    public static List<Produits> SearchProd(Connection con, String ref) {
        ArrayList<Produits> liste = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(
                "select *"
                + " from Tproduits"
                + " where ref like ?")) {
            st.setString(1, "%" + ref + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new Produits(res.getInt(1), res.getString(2), res.getString(3)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }

    public static void addProduct(Connection con, String ref, String des) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tproduits (ref,des)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setString(1, ref);
            pt.setString(2, des);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

}
