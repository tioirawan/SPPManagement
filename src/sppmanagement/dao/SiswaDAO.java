/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import sppmanagement.model.Siswa;

/**
 *
 * @author ASTANEW2
 */
public class SiswaDAO extends DAO {

    public SiswaDAO() {
        this.table = "siswa";
        this.primaryKey = "nisn";
    }

    public void delete(Siswa siswa) {
        super.delete(siswa.getNisn());
    }

    public void update(Siswa siswa) {
        Map<String, Object> data = new HashMap();

        data.put("nis", siswa.getNis());
        data.put("nama", siswa.getNama());
        data.put("id_kelas", siswa.getIdKelas());
        data.put("alamat", siswa.getAlamat());
        data.put("no_telp", siswa.getNoTelp());
        data.put("id_spp", siswa.getIdSpp());

        super.update(data, siswa.getNisn());
    }

    public void add(Siswa siswa) {
        Map<String, Object> data = new HashMap();

        data.put("nisn", siswa.getNisn());
        data.put("nis", siswa.getNis());
        data.put("nama", siswa.getNama());
        data.put("id_kelas", siswa.getIdKelas());
        data.put("alamat", siswa.getAlamat());
        data.put("no_telp", siswa.getNoTelp());
        data.put("id_spp", siswa.getIdSpp());

        super.add(data);
    }

    public Siswa find(String nisn) {
        return super.find(nisn, Siswa.class);
    }

    public ArrayList<Siswa> all() {
        return super.all(Siswa.class);
    }

}
