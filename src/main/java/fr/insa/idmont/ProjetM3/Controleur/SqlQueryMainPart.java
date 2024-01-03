/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Controleur;

import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import fr.insa.idmont.ProjetM3.DataBase_Model.Produits;
import fr.insa.idmont.ProjetM3.DataBase_Model.TypeOperations;
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

    // ------------------------------------ Produit :
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
                + " where ref = ?")) {
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

    public static void deleteProd(Connection con, Iterator<Produits> iterator) throws SQLException {
        Iterator<Produits> it = iterator;
        while (it.hasNext()) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tproduits"
                    + " where id = ?")) {
                pst.setInt(1, iterator.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
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

    // ------------------------------------ Machine :
    public static List<Machines> GetMachine(Connection con) throws SQLException {
        ArrayList<Machines> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Tmachine  ");
            while (res.next()) {
                liste.add(new Machines(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public static int TestMachine(Connection con, String mach) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Tmachine"
                + " where ref = ?")) {
            pst.setString(1, mach);
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

    public static void EditMachine(Connection con, String newRef, String newDes, int id, int puissance) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Tmachine  "
                + " set ref=?,des=?,puissance=?"
                + " where id = ?")) {
            pst.setString(1, newRef);
            pst.setString(2, newDes);
            pst.setInt(3, puissance);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static void deleteMachine(Connection con, Iterator<Machines> iterator) throws SQLException {
        Iterator<Machines> it = iterator;
        while (it.hasNext()) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tmachine"
                    + " where id = ?")) {
                pst.setInt(1, iterator.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static List<Machines> SearchMachine(Connection con, String ref) {
        ArrayList<Machines> liste = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(
                "select *"
                + " from Tmachine"
                + " where ref like ?")) {
            st.setString(1, "%" + ref + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new Machines(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }

    public static void addMachine(Connection con, String ref, String des, int puissance) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tmachine (ref,des,puissance)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setString(1, ref);
            pt.setString(2, des);
            pt.setInt(3, puissance);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    // ------------------------------------ Type Opérations :
    public static List<TypeOperations> GetTO(Connection con) throws SQLException {
        ArrayList<TypeOperations> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Ttype_operation  ");
            while (res.next()) {
                liste.add(new TypeOperations(res.getInt(1), res.getString(2)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public static int TestTO(Connection con, String des) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Ttype_operation"
                + " where des = ?")) {
            pst.setString(1, des);
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

    public static void EditTO(Connection con, String newDes, int id) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Ttype_operation  "
                + " set des=?"
                + " where id = ?")) {
            pst.setString(1, newDes);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static void deleteTO(Connection con, Iterator<TypeOperations> iterator) throws SQLException {
        Iterator<TypeOperations> it = iterator;
        while (it.hasNext()) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Ttype_operation"
                    + " where id = ?")) {
                pst.setInt(1, iterator.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static List<TypeOperations> SearchTO(Connection con, String des) {
        ArrayList<TypeOperations> liste = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(
                "select *"
                + " from Ttype_operation"
                + " where des like ?")) {
            st.setString(1, "%" + des + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                liste.add(new TypeOperations(res.getInt(1), res.getString(2)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }

    public static void addTO(Connection con, String des) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Ttype_operation (des)
                                                       VALUES (?)
                                                       """)) {
            pt.setString(1, des);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    // ------------------------------------ Realise :
    public static int TestRealise(Connection con, String idMachine) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Trealise"
                + " where id_machine = ?")) {
            pst.setString(1, idMachine);
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

    public static void EditRealise(Connection con, int idTypeOperation, float duree, int idMachine) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Tproduits  "
                + " set id_type=?,duree=?"
                + " where id_machine = ?")) {
            pst.setInt(1, idTypeOperation);
            pst.setFloat(2, duree);
            pst.setInt(3, idMachine);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    // -------------------------------------------- Operations:
    public static void AssociationOpDes(Connection con, ArrayList<Operations> liste) throws SQLException {

        for (Operations op : liste) {
            try (PreparedStatement st = con.prepareStatement(
                    "select *"
                    + " from Ttype_operation"
                    + " where id=? ")) {
                st.setInt(1, op.getIdType());
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    op.setDesTO(res.getString(2));
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static List<Operations> GetOp(Connection con, int idproduit) {
        ArrayList<Operations> liste = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(
                "select *"
                + " from Toperations"
                + " where id_produit=? ")) {
            st.setInt(1, idproduit);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                Operations op = new Operations(res.getInt(1), new TypeOperations(), new Produits());
                op.getTypeOP().setId(res.getInt(2));
                op.getProduit().setId(idproduit);
                liste.add(op);
            }
            AssociationOpDes(con, liste);
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }

    public static void deleteOp(Connection con, Iterator<Operations> iterator) throws SQLException {
        Iterator<Operations> it = iterator;
        while (it.hasNext()) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Toperations"
                    + " where id = ?")) {
                pst.setInt(1, iterator.next().getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static void addOp(Connection con, int idtype, int idproduit) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Toperations (idtype,id_produit)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setInt(1, idtype);
            pt.setInt(2, idproduit);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

}
