/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import sppmanagement.dao.PetugasDAO;

/**
 *
 * @author ASTANEW2
 */
public class Petugas implements Model {

    private int id;
    private String username;
    private String password;
    private String nama;
    private String level;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public void save() {
        if (this.id == 0) {
            new PetugasDAO().add(this);
        } else {
            new PetugasDAO().update(this);
        }
    }

    @Override
    public void delete() {
        if (this.id != 0) {
            new PetugasDAO().delete(this);
        }
    }

    @Override
    public void fillFromResultSet(ResultSet result) throws SQLException {
        setId(result.getInt("id_petugas"));
        setUsername(result.getString("username"));
        setPassword(result.getString("password"));
        setNama(result.getString("nama_petugas"));
        setLevel(result.getString("level"));
    }
}
