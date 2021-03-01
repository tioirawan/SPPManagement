/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import sppmanagement.db.DB;
import sppmanagement.model.Petugas;

/**
 *
 * @author ASTANEW2
 */
public class PetugasDAO extends DAO {

    public PetugasDAO() {
        this.table = "petugas";
        this.primaryKey = "id_petugas";
    }

    private final String selectByUsernameAndPassword = "SELECT * FROM petugas WHERE username = ? AND password = ?";

    public ArrayList<Petugas> all() {
        return super.all(Petugas.class);
    }

    public void add(Petugas petugas) {
        Map<String, Object> data = new HashMap();

        data.put("username", petugas.getUsername());
        data.put("password", petugas.getPassword());
        data.put("nama_petugas", petugas.getNama());
        data.put("level", petugas.getLevel());

        super.add(data);
    }

    public Petugas find(int id) {
        return super.find(String.valueOf(id), Petugas.class);
    }

    public void update(Petugas petugas) {
        Map<String, Object> data = new HashMap<>();

        data.put("username", petugas.getUsername());
        data.put("password", petugas.getPassword());
        data.put("nama_petugas", petugas.getNama());
        data.put("level", petugas.getLevel());

        super.update(data, petugas.getId());
    }

    public void delete(Petugas petugas) {
        super.delete("" + petugas.getId());
    }

    public Petugas getByUsernameAndPassword(String username, String password) {
        Petugas petugas = null;

        try {
            PreparedStatement statement = connection.prepareStatement(selectByUsernameAndPassword);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                petugas = new Petugas();

                petugas.fillFromResultSet(result);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return petugas;

    }
    
    

//    private String selectAllQuery = "SELECT * FROM petugas";
//    private String insertQuery = "INSERT INTO petugas(username, password, nama_petugas, level) VALUES(?, ?, ?, ?)";
//    private final String updateQuery = "UPDATE petugas SET username = ?, password = ?, nama_petugas = ?, level = ? WHERE id_petugas = ?";
//    private final String findByIdQuery = "SELECT * FROM petugas WHERE id_petugas = ?";
//    private final String deleteQuery = "DELETE FROM petugas WHERE id_petugas = ?";

//    public void delete(Petugas petugas) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(deleteQuery);
//
//            statement.setInt(1, petugas.getId());
//
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    public void update(Petugas petugas) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(updateQuery);
//
//            statement.setString(1, petugas.getUsername());
//            statement.setString(2, petugas.getPassword());
//            statement.setString(3, petugas.getNama());
//            statement.setString(4, petugas.getLevel());
//            statement.setInt(5, petugas.getId());
//
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    //    public void add(Petugas petugas) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(insertQuery);
//
//            statement.setString(1, petugas.getUsername());
//            statement.setString(2, petugas.getPassword());
//            statement.setString(3, petugas.getNama());
//            statement.setString(4, petugas.getLevel());
//
//            statement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
