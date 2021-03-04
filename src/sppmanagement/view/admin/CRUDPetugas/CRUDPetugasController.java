/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.admin.CRUDPetugas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sppmanagement.dao.PetugasDAO;
import sppmanagement.model.Petugas;

/**
 *
 * @author ASTANEW2
 */
public class CRUDPetugasController {
    
    PetugasDAO dao;
    
    public CRUDPetugasController() {
        dao = new PetugasDAO();
    }
    
    public void loadTable(CRUDPetugasView view) {
        ArrayList<Petugas> listPetugas = dao.all();
        
//        System.out.println(listPetugas);
        
        DefaultTableModel model = (DefaultTableModel) view.getTablePetugas().getModel();
        
        model.setNumRows(0);
        
        listPetugas.forEach((petugas) -> {
            Object[] row = new Object[5];
            
            row[0] = petugas.getId();
            row[1] = petugas.getUsername();
            row[2] = petugas.getPassword();
            row[3] = petugas.getNama();
            row[4] = petugas.getLevel();
            
            model.addRow(row);
        });
    }
    
    public void save(CRUDPetugasView view) {
        Petugas petugas;
        
        String id = view.getTextID().getText();
        
        if (id.equals("")) {
            System.out.println("Create new petugas");
            petugas = new Petugas();
        } else {
            System.out.println("Update existing petugas");
            petugas = dao.find(Integer.parseInt(id));
        }
        
        petugas.setUsername(view.getTextUsername().getText());
        petugas.setPassword(view.getTextPassword().getText());
        petugas.setNama(view.getTextNama().getText());
        petugas.setLevel(view.getSelectLevel().getSelectedItem().toString());
        
        petugas.save();
        
        // only reset if creating new user
        if (id.equals("")) this.reset(view);
        
        this.loadTable(view);
    }
    
    public void setFormFieldFromClickedTable(CRUDPetugasView view) {
        int selectedRow = view.getTablePetugas().getSelectedRow();
        
        String id = view.getTablePetugas().getValueAt(selectedRow, 0).toString();
        String username = view.getTablePetugas().getValueAt(selectedRow, 1).toString();
        String password = view.getTablePetugas().getValueAt(selectedRow, 2).toString();
        String name = view.getTablePetugas().getValueAt(selectedRow, 3).toString();
        String level = view.getTablePetugas().getValueAt(selectedRow, 4).toString();
        
        view.getTextID().setText(id);
        view.getTextUsername().setText(username);
        view.getTextPassword().setText(password);
        view.getTextNama().setText(name);
        view.getSelectLevel().setSelectedItem(level);
    }
    
    public void reset(CRUDPetugasView view) {
        view.getTextID().setText("");
        view.getTextUsername().setText("");
        view.getTextPassword().setText("");
        view.getTextNama().setText("");
        view.getSelectLevel().setSelectedItem("");
    }
    
    public void delete(CRUDPetugasView view) {
        if (view.getTextID().getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Pilih petugas yang akan dihapus");
            
            return;
        }
        
        int response = JOptionPane.showConfirmDialog(null, "Menghapus petugas akan mempengaruhi data pembayaran! apakah anda yakin?", "Konfirmasi Hapus", JOptionPane.WARNING_MESSAGE);

        if (response != JOptionPane.OK_OPTION) {
            return;
        }
        
        int petugasId = Integer.parseInt(view.getTextID().getText());
        
        dao.find(petugasId).delete();
        
        this.reset(view);
        this.loadTable(view);
    }
}
