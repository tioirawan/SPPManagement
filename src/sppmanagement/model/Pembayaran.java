/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import sppmanagement.dao.PembayaranDAO;
import sppmanagement.dao.PetugasDAO;
import sppmanagement.dao.SPPDao;
import sppmanagement.dao.SiswaDAO;
import sppmanagement.helper.Helper;

/**
 *
 * @author ASTANEW2
 */
public class Pembayaran implements Model {

    private int idPembayaran;
    private int idPetugas;
    private String nisn;
    private Date tglBayar;
    private String bulanBayar;
    private String tahunBayar;
    private int idSpp;
    private int jumlahBayar;

    public int getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(int idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public int getIdPetugas() {
        return idPetugas;
    }

    public void setIdPetugas(int idPetugas) {
        this.idPetugas = idPetugas;
    }

    public String getNisn() {
        return nisn;
    }

    public void setNisn(String nisn) {
        this.nisn = nisn;
    }

    public Date getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(Date tglBayar) {
        this.tglBayar = tglBayar;
    }

    public String getBulanBayar() {
        return bulanBayar;
    }

    public void setBulanBayar(String bulanBayar) {
        this.bulanBayar = bulanBayar;
    }

    public String getTahunBayar() {
        return tahunBayar;
    }

    public void setTahunBayar(String tahunBayar) {
        this.tahunBayar = tahunBayar;
    }

    public int getIdSpp() {
        return idSpp;
    }

    public void setIdSpp(int idSpp) {
        this.idSpp = idSpp;
    }

    public int getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(int jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    @Override
    public void save() {
        if (this.idPembayaran == 0) {
            new PembayaranDAO().add(this);
        } else {
            new PembayaranDAO().update(this);
        }
    }

    @Override
    public void delete() {
        if (this.idPembayaran != 0) {
            new PembayaranDAO().delete(this);
        }
    }

    @Override
    public void fillFromResultSet(ResultSet result) throws SQLException {
        setIdPembayaran(result.getInt("id_pembayaran"));
        setIdPetugas(result.getInt("id_petugas"));
        setNisn(result.getString("nisn"));
        setTglBayar(result.getDate("tgl_bayar"));
        setBulanBayar(result.getString("bulan_dibayar"));
        setTahunBayar(result.getString("tahun_dibayar"));
        setIdSpp(result.getInt("id_spp"));
        setJumlahBayar(result.getInt("jumlah_bayar"));

    }

    public Petugas getPetugas() {
        return new PetugasDAO().whereOne("id_petugas = " + idPetugas, Petugas.class);
    }

    public Siswa getSiswa() {
        return new SiswaDAO().whereOne("nisn = " + nisn, Siswa.class);
    }

    public SPP getSPP() {
        return new SPPDao().whereOne("id_spp = " + idSpp, SPP.class);
    }
}
