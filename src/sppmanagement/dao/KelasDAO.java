/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import sppmanagement.model.Kelas;

/**
 *
 * @author ASTANEW2
 */
public class KelasDAO extends DAO {

    public KelasDAO() {
        this.table = "kelas";
        this.primaryKey = "id_kelas";
    }

    public ArrayList<Kelas> all() {
        return super.all(Kelas.class);
    }
    
    public Kelas find(int id) {
        return super.find(String.valueOf(id), Kelas.class);
    }

    public void add(Kelas kelas) {
        Map<String, Object> data = new HashMap();

        data.put("nama_kelas", kelas.getNamaKelas());
        data.put("kompetensi_keahlian", kelas.getKompetensiKeahlian());

        super.add(data);
    }

    public void update(Kelas kelas) {
        Map<String, Object> data = new HashMap();

        data.put("nama_kelas", kelas.getNamaKelas());
        data.put("kompetensi_keahlian", kelas.getKompetensiKeahlian());

        super.update(data, kelas.getIdKelas());
    }
    
       public void delete(Kelas kelas) {
        super.delete(String.valueOf(kelas.getIdKelas()));;
    }
}
