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
import sppmanagement.model.SPP;

/**
 *
 * @author ASTANEW2
 */
public class SPPDao extends DAO {

    public SPPDao() {
        this.table = "spp";
        this.primaryKey = "id_spp";
    }

    public void delete(SPP spp) {
        super.delete(String.valueOf(spp.getIdSpp()));
    }

    public void update(SPP spp) {
        Map<String, Object> data = new HashMap<>();
        
        data.put("tahun", spp.getTahun());
        data.put("nominal", spp.getNominal());
        
        super.update(data, spp.getIdSpp());
    }

    public int add(SPP spp) {
         Map<String, Object> data = new HashMap<>();
        
        data.put("tahun", spp.getTahun());
        data.put("nominal", spp.getNominal());
        
        String id = super.add(data);
        
        return Integer.parseInt(id);
    }

    public SPP find(int id) {
        return super.find(String.valueOf(id), SPP.class);
    }

    public ArrayList<SPP> all() {
        return super.all(SPP.class);
    }

}
