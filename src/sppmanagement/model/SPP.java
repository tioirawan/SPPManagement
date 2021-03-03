/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sppmanagement.dao.SPPDao;
import sppmanagement.dao.SiswaDAO;

/**
 *
 * @author ASTANEW2
 */
public class SPP implements Model {

    private int idSpp;
    private String tahun;
    private int nominal;

    public int getIdSpp() {
        return idSpp;
    }

    public void setIdSpp(int idSpp) {
        this.idSpp = idSpp;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public ArrayList<Siswa> getSiswa() {
        return new SiswaDAO().where("id_spp=" + this.idSpp, Siswa.class);
    }

    @Override
    public void save() {
        if (this.idSpp == 0) {
            int id = new SPPDao().add(this);

            this.setIdSpp(id);
        } else {
            new SPPDao().update(this);
        }
    }

    @Override
    public void delete() {
        if (this.idSpp != 0) {
            new SPPDao().delete(this);
        }
    }

    @Override
    public void fillFromResultSet(ResultSet result) throws SQLException {
        setIdSpp(result.getInt("id_spp"));
        setTahun(result.getString("tahun"));
        setNominal(result.getInt("nominal"));
    }

    @Override
    public String toString() {
        return this.tahun + " - " + this.nominal;
    }
}
