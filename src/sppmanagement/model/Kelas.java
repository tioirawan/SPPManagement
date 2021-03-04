/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import sppmanagement.dao.KelasDAO;

public class Kelas implements Model {
    private int idKelas;
    private String namaKelas;
    private String kompetensiKeahlian;

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public String getKompetensiKeahlian() {
        return kompetensiKeahlian;
    }

    public void setKompetensiKeahlian(String kompetensiKeahlian) {
        this.kompetensiKeahlian = kompetensiKeahlian;
    }

    @Override
    public void save() {
        if (this.idKelas == 0) {
            new KelasDAO().add(this);
        } else {
            new KelasDAO().update(this);
        }
    }

    @Override
    public void delete() {
         if (this.idKelas!= 0) {
            new KelasDAO().delete(this.idKelas + "");
        }
    }

    @Override
    public void fillFromResultSet(ResultSet result) throws SQLException {
        setIdKelas(result.getInt("id_kelas"));
        setNamaKelas(result.getString("nama_kelas"));
        setKompetensiKeahlian(result.getString("kompetensi_keahlian"));
    }
}
