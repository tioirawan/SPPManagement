/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import sppmanagement.dao.KelasDAO;
import sppmanagement.dao.PembayaranDAO;
import sppmanagement.dao.SPPDao;
import sppmanagement.dao.SiswaDAO;

/**
 *
 * @author ASTANEW2
 */
public class Siswa implements Model {

    private String nisn;
    private String nis;
    private String nama;
    private int idKelas;
    private String alamat;
    private String noTelp;
    private int idSpp;

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(int idKelas) {
        this.idKelas = idKelas;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public int getIdSpp() {
        return idSpp;
    }

    public void setIdSpp(int idSpp) {
        this.idSpp = idSpp;
    }
    
    public Kelas getKelas() {
        Kelas kelas = new KelasDAO().find(idKelas);
        
        return kelas;
    }
    
    public SPP getSPP() {
        SPP spp = new SPPDao().find(idSpp);
        
        
        return spp;
    }

    @Override
    public void save() {
        SiswaDAO dao = new SiswaDAO();
        
        if (dao.find(nisn) == null) {
            dao.add(this);
        } else {
            dao.update(this);
        }
    }

    @Override
    public void delete() {
        if (this.nisn.equals("")) {
            new SiswaDAO().delete(this);
        }
    }

    @Override
    public void fillFromResultSet(ResultSet result) throws SQLException {
        setNisn(result.getString("nisn"));
        setNis(result.getString("nis"));
        setNama(result.getString("nama"));
        setIdKelas(result.getInt("id_kelas"));
        setAlamat(result.getString("alamat"));
        setNoTelp(result.getString("no_telp"));
        setIdSpp(result.getInt("id_spp"));
    }

    public ArrayList<Pembayaran> getPembayaran() {
        return new PembayaranDAO().where("nisn = " + nisn, "tgl_bayar DESC, id_pembayaran DESC", Pembayaran.class);
    }
}
