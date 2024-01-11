/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.idmont.ProjetM3.Controleur;

import static fr.insa.idmont.ProjetM3.Controleur.AlgoGestionPrecedence.Tri;
import fr.insa.idmont.ProjetM3.DataBase_Model.Machines;
import fr.insa.idmont.ProjetM3.DataBase_Model.Operations;
import fr.insa.idmont.ProjetM3.DataBase_Model.Precedence;
import fr.insa.idmont.ProjetM3.DataBase_Model.Produits;
import fr.insa.idmont.ProjetM3.DataBase_Model.Realise;
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
    public static void addRealise(Connection con, int idM, int idOp, double duree) throws SQLException {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Trealise (id_machine,id_type,duree)
                                                       VALUES (?,?,?)
                                                       """)) {
            pt.setInt(1, idM);
            pt.setInt(2, idOp);
            pt.setDouble(3, duree);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }
    }

    public static boolean TestRealise(Connection con, int idMachine, int idTO, double duree) throws SQLException {
        int[] test = new int[2];
        test[0] = -1;
        test[1] = -1;
        try (PreparedStatement pst = con.prepareStatement(
                "select *"
                + " from Trealise"
                + " where id_machine = ? AND id_type=? AND duree=?")) {
            pst.setInt(1, idMachine);
            pst.setInt(2, idTO);
            pst.setDouble(3, duree);
            ResultSet res = pst.executeQuery();
            if (res.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    public static void EditRealise(Connection con, int idTypeOperation, double duree, int idMachine, int idTO, double dr, int idM) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Trealise  "
                + " set id_type=?,duree=?,id_machine= ?"
                + " where id_machine = ? AND id_type=? AND duree=?")) {
            pst.setInt(1, idTypeOperation);
            pst.setDouble(2, duree);
            pst.setInt(3, idMachine);
            pst.setInt(4, idM);
            pst.setInt(5, idTO);
            pst.setDouble(6, dr);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static List<Realise> GetRealise(Connection con) {
        ArrayList<Realise> liste = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT * FROM Trealise  ");
            while (res.next()) {
                liste.add(new Realise(new Machines(res.getInt(1)), new TypeOperations(res.getInt(2)), res.getDouble(3)));
            }
            AssociationMachDes(con, liste);
            AssociationOpDesRe(con, liste);
            return liste;
        } catch (SQLException ex) {
            return liste;
        }
    }

    public static void AssociationMachDes(Connection con, ArrayList<Realise> liste) throws SQLException {

        for (Realise rea : liste) {
            try (PreparedStatement st = con.prepareStatement(
                    "select *"
                    + " from Tmachine"
                    + " where id=? ")) {
                st.setInt(1, rea.getMachine().getId());
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    rea.getMachine().setRef(res.getString(2));
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static void AssociationOpDesRe(Connection con, ArrayList<Realise> liste) throws SQLException {

        for (Realise re : liste) {
            try (PreparedStatement st = con.prepareStatement(
                    "select *"
                    + " from Ttype_operation"
                    + " where id=? ")) {
                st.setInt(1, re.getTO().getId());
                ResultSet res = st.executeQuery();
                if (res.next()) {
                    re.getTO().setDes(res.getString(2));
                }
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

    public static void deleteRealise(Connection con, Iterator<Realise> iterator) throws SQLException {
        Iterator<Realise> it = iterator;
        while (it.hasNext()) {
            Realise r = it.next();
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Trealise"
                    + " where id_machine = ? AND id_type=? AND duree = ? ")) {
                pst.setInt(1, r.getMachine().getId());
                pst.setInt(2, r.getTO().getId());
                pst.setDouble(3, r.getDuree());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        }
    }

/*  public static List<Realise> SearchRealise(Connection con, String Ref) {
        ArrayList<Realise> liste = new ArrayList<>();
        try (PreparedStatement st = con.prepareStatement(
                "select *"
                + " from Tmachines"
                + " where ref like ?")) {
            st.setString(1, "%" + Ref + "%");
            ResultSet res = st.executeQuery();
            while (res.next()) {
                // liste.add(new Realise(res.getInt(1), res.getInt(2)));
            }
            return liste;
        } catch (SQLException ex) {
            return liste;
        }

    }
*/
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
            if (!liste.isEmpty()) {
                AssociationOpDes(con, liste);
                if (!GetPrec(con, liste).isEmpty()) {
                    List<Integer> test = Tri(GetPrec(con, liste));
                    liste = AlgoGestionPrecedence.Ordre(liste, test);
                }
            }
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

    public static int addOp(Connection con, int idtype, int idproduit) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Toperations (idtype,id_produit)
                                                       VALUES (?,?)
                                                       """, Statement.RETURN_GENERATED_KEYS)) {
            pt.setInt(1, idtype);
            pt.setInt(2, idproduit);
            pt.executeUpdate();

            ResultSet generatedKeys = pt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException ex) {
        }
        return -1;
    }

    public static void deleteOpAll(Connection con, int id) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "delete "
                + " from Toperations"
                + " where id_produit = ?")) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            throw ex;
        }

    }

    // -------------------------------------------- Précédence:
    public static List<Precedence> GetPrec(Connection con, List<Operations> list) throws SQLException {
        ArrayList<Precedence> pliste = new ArrayList<>();
        for (Operations op : list) {
            try (PreparedStatement st = con.prepareStatement(
                    "select *"
                    + " from Tprecedence"
                    + " where opavant=? OR opapres=? ")) {
                st.setInt(1, op.getId());
                st.setInt(2, op.getId());
                ResultSet res = st.executeQuery();
                while (res.next()) {
                    pliste.add(new Precedence(res.getInt(1), res.getInt(2)));
                }

            } catch (SQLException ex) {
            }
        }
        return pliste;
    }

    public static void addPrecedence(Connection con, int avant, int apres) {
        try (PreparedStatement pt = con.prepareStatement("""
                                                       INSERT INTO Tprecedence (opavant,opapres)
                                                       VALUES (?,?)
                                                       """)) {
            pt.setInt(1, avant);
            pt.setInt(2, apres);
            pt.executeUpdate();
        } catch (SQLException ex) {

        }

    }

    public static void EditPrecedence2(Connection con, int aid, int nid, int av, int ap) throws SQLException {
        try (PreparedStatement pst = con.prepareStatement(
                "update Tprecedence  "
                + " set opavant=?,opapres=?"
                + " where opavant = ? AND opapres =? ")) {
            pst.setInt(1, nid);
            pst.setInt(2, aid);
            pst.setInt(3, av);
            pst.setInt(4, ap);
            pst.executeUpdate();
        } catch (SQLException ex) {
        }
    }

    public static void deletePrece(Connection con, Iterator<Operations> iterator, List<Operations> opL) throws SQLException {
        Operations it = iterator.next();

        if (opL.indexOf(it) == 0) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tprecedence"
                    + " where opavant = ?")) {
                pst.setInt(1, it.getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        } else if (opL.indexOf(it) == opL.size() - 1) {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tprecedence"
                    + " where opapres = ?")) {
                pst.setInt(1, it.getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
        } else {
            try (PreparedStatement pst = con.prepareStatement(
                    "delete "
                    + " from Tprecedence"
                    + " where opavant = ?")) {
                pst.setInt(1, it.getId());
                pst.executeUpdate();
            } catch (SQLException ex) {
                throw ex;
            }
            EditPrecedence2(con, opL.get(opL.indexOf(it) + 1).getId(), opL.get(opL.indexOf(it) - 1).getId(), opL.get(opL.indexOf(it) - 1).getId(), it.getId());
        }

    }

    public static void DeletePreceAll(Connection con, List<Operations> list) throws SQLException {
        for (Operations op : list) {
            System.out.println("ok");
            try (PreparedStatement st = con.prepareStatement(
                    "DELETE "
                    + " from Tprecedence"
                    + " where opavant=? OR opapres=? ")) {
                st.setInt(1, op.getId());
                st.setInt(2, op.getId());
                st.executeUpdate();

            } catch (SQLException ex) {
            }
        }
    }

}
