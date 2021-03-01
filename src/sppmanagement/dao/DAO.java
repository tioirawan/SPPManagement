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
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import sppmanagement.db.DB;
import sppmanagement.model.Model;
import sppmanagement.model.Petugas;

/**
 *
 * @author ASTANEW2
 */
public class DAO {

    protected Connection connection;
    protected String table;
    protected String primaryKey;

    protected DAO() {
        connection = DB.connection();
    }

    public <T extends Model> ArrayList<T> all(Class<T> modelClass) {
        ArrayList<T> list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + table);

            while (result.next()) {
                T model = modelClass.newInstance();

                model.fillFromResultSet(result);

                list.add(model);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public <T extends Model> ArrayList<T> all(Class<T> modelClass, String orderBy) {
        ArrayList<T> list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM " + table + " ORDER BY " + orderBy);

            while (result.next()) {
                T model = modelClass.newInstance();

                model.fillFromResultSet(result);

                list.add(model);
            }

        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public <T extends Model> T find(String id, Class<T> modelClass) {
        T model = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + primaryKey + " = ?");

            statement.setString(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                model = modelClass.newInstance();

                model.fillFromResultSet(rs);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return model;
    }

    public <T extends Model> ArrayList<T> where(String where, Class<T> modelClass) {
        ArrayList<T> results = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + where);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                T model = modelClass.newInstance();

                model.fillFromResultSet(rs);

                results.add(model);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    public <T extends Model> ArrayList<T> where(String where, String orderBy, Class<T> modelClass) {
        ArrayList<T> results = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + where + " ORDER BY " + orderBy);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                T model = modelClass.newInstance();

                model.fillFromResultSet(rs);

                results.add(model);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    public <T extends Model> T whereOne(String where, Class<T> modelClass) {
        T results = null;

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + where);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                results = modelClass.newInstance();

                results.fillFromResultSet(rs);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return results;
    }

    public String add(Map<String, Object> data) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        columns.append("(");
        values.append("(");

        columns.append(String.join(",", data.keySet()));

        String formatedValues = data.entrySet().stream().map((t) -> {
            return "\"" + t.getValue() + "\"";
        }).collect(Collectors.joining(", "));

        values.append(formatedValues);

        columns.append(")");
        values.append(")");

        String query = "INSERT INTO " + table + columns + " VALUES " + values;

        try {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void update(Map<String, Object> data, Object where) {
        String query = "UPDATE " + table + " SET ";

        String formatedValues = data.entrySet().stream().map((item) -> {
            return item.getKey() + "=" + "'" + item.getValue() + "'";
        }).collect(Collectors.joining(", "));

        query += formatedValues + " WHERE " + primaryKey + "=" + where.toString();

        try {
            PreparedStatement statement = connection.prepareStatement(query);

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PetugasDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(String id) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + table + " WHERE " + primaryKey + "=" + id);
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
