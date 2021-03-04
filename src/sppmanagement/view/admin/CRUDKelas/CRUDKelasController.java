/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.admin.CRUDKelas;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sppmanagement.dao.KelasDAO;
import sppmanagement.model.Kelas;

/**
 *
 * @author ASTANEW2
 */
public class CRUDKelasController {

    KelasDAO dao;

    public CRUDKelasController() {
        dao = new KelasDAO();
    }

    void loadTable(CRUDKelasView view) {
        ArrayList<Kelas> listKelas = dao.all();

        DefaultTableModel tableModel = (DefaultTableModel) view.getTableKelas().getModel();

        tableModel.setRowCount(0);

        listKelas.forEach((kelas) -> {
            Object[] row = new Object[3];

            row[0] = kelas.getIdKelas();
            row[1] = kelas.getNamaKelas();
            row[2] = kelas.getKompetensiKeahlian();

            tableModel.addRow(row);
        });
    }

    void save(CRUDKelasView view) {
        Kelas kelas;

        String id = view.getTextID().getText();

        if (id.equals("")) {
            System.out.println("Create new kelas");
            kelas = new Kelas();
        } else {
            System.out.println("Update existing kelas");
            kelas = dao.find(Integer.parseInt(id));
        }

        kelas.setNamaKelas(view.getTextNamaKelas().getText());
        kelas.setKompetensiKeahlian(view.getTextKeahlian().getText());

        kelas.save();
        
        if (id.equals("")) this.reset(view);

        this.loadTable(view);
    }

    void reset(CRUDKelasView view) {
        view.getTextID().setText("");
        view.getTextNamaKelas().setText("");
        view.getTextKeahlian().setText("");
    }

    void delete(CRUDKelasView view) {
        if (view.getTextID().getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Pilih kelas yang akan dihapus");

            return;
        }
        
        int response = JOptionPane.showConfirmDialog(null, "Menghapus kelas akan mempengaruhi data siswa! apakah anda yakin?", "Konfirmasi Hapus", JOptionPane.WARNING_MESSAGE);

        if (response != JOptionPane.OK_OPTION) {
            return;
        }

        int id = Integer.parseInt(view.getTextID().getText());

        dao.find(id).delete();

        this.reset(view);
        this.loadTable(view);
    }

    void setFormFieldFromClickedTable(CRUDKelasView view) {
        int selectedRow = view.getTableKelas().getSelectedRow();

        view.getTextID().setText(view.getTableKelas().getValueAt(selectedRow, 0).toString());
        view.getTextNamaKelas().setText(view.getTableKelas().getValueAt(selectedRow, 1).toString());
        view.getTextKeahlian().setText(view.getTableKelas().getValueAt(selectedRow, 2).toString());

    }
}
