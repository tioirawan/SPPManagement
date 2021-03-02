/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import sppmanagement.model.Pembayaran;
import sppmanagement.model.SPP;

/**
 *
 * @author ASTANEW2
 */
public class PembayaranDAO extends DAO {

    public PembayaranDAO() {
        this.table = "pembayaran";
        this.primaryKey = "id_pembayaran";
    }

    public void delete(Pembayaran pembayaran) {
        super.delete(String.valueOf(pembayaran.getIdPembayaran()));
    }

    public void update(Pembayaran pembayaran) {
        Map<String, Object> data = new HashMap<>();

        data.put("id_petugas", pembayaran.getIdPetugas());
        data.put("nisn", pembayaran.getNisn());
        data.put("tgl_bayar", pembayaran.getTglBayar());
        data.put("bulan_dibayar", pembayaran.getBulanBayar());
        data.put("tahun_dibayar", pembayaran.getTahunBayar());
        data.put("id_spp", pembayaran.getIdSpp());
        data.put("jumlah_bayar", pembayaran.getJumlahBayar());

        super.update(data, pembayaran.getIdPembayaran());
    }

    public int add(Pembayaran pembayaran) {
        Map<String, Object> data = new HashMap<>();

        data.put("id_petugas", pembayaran.getIdPetugas());
        data.put("nisn", pembayaran.getNisn());
        data.put("tgl_bayar", pembayaran.getTglBayar());
        data.put("bulan_dibayar", pembayaran.getBulanBayar());
        data.put("tahun_dibayar", pembayaran.getTahunBayar());
        data.put("id_spp", pembayaran.getIdSpp());
        data.put("jumlah_bayar", pembayaran.getJumlahBayar());

        String id = super.add(data);

        return Integer.parseInt(id);
    }

    public Pembayaran find(int id) {
        return super.find(String.valueOf(id), Pembayaran.class);
    }

    public ArrayList<Pembayaran> all() {
        return super.all(Pembayaran.class, "tgl_bayar DESC, id_pembayaran DESC");
    }

}
