/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.admin.CRUDSPP;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sppmanagement.dao.SPPDao;
import sppmanagement.model.SPP;

/**
 *
 * @author ASTANEW2
 */
public class CRUDSPPController {

    SPPDao dao;

    public CRUDSPPController() {
        this.dao = new SPPDao();
    }

    void loadTable(CRUDSPPView view) {
        ArrayList<SPP> listSpp = dao.all();

        DefaultTableModel tableModel = (DefaultTableModel) view.getTabelSPP().getModel();

        tableModel.setNumRows(0);

        listSpp.forEach((spp) -> {
            Object[] row = new Object[3];

            row[0] = spp.getIdSpp();
            row[1] = spp.getTahun();
            row[2] = spp.getNominal();

            tableModel.addRow(row);
        });

    }

    void save(CRUDSPPView view) {
        SPP spp;

        int id = Integer.parseInt("0" + view.getTextID().getText());

        SPP existingSPP = dao.find(id);

        if (id == 0 || existingSPP == null) {
            spp = new SPP();
        } else {
            spp = existingSPP;
        }

        spp.setNominal(Integer.parseInt(view.getTextNominal().getText()));
        spp.setTahun(view.getTextTahun().getText());

        spp.save();

        if (id == 0 || existingSPP == null) {
            this.reset(view);
        }

        this.loadTable(view);
    }

    void reset(CRUDSPPView view) {
        view.getTextID().setText("");
        view.getTextTahun().setText("");
        view.getTextNominal().setText("");
    }

    void delete(CRUDSPPView view) {
        if (view.getTextID().getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Pilih SPP yang akan dihapus");

            return;
        }

        int id = Integer.parseInt(view.getTextID().getText());

        SPP spp = dao.find(id);

        if (spp.getSiswa().size() > 0) {
            JOptionPane.showMessageDialog(view, "SPP ini digunakan oleh " + spp.getSiswa().size() + " siswa, harap ubah atau hapus siswa yang menggunakan jenis SPP ini terlebih dahulu!");

            return;
        }
        
        int response = JOptionPane.showConfirmDialog(null, "Menghapus SPP akan mempengaruhi data siswa! apakah anda yakin?", "Konfirmasi Hapus", JOptionPane.WARNING_MESSAGE);

        if (response != JOptionPane.OK_OPTION) {
            return;
        }

        spp.delete();

        this.reset(view);
        this.loadTable(view);
    }

    void tableClicked(CRUDSPPView view) {
        int selectedRow = view.getTabelSPP().getSelectedRow();

        int selectedId = Integer.parseInt(view.getTabelSPP().getValueAt(selectedRow, 0).toString());

        SPP spp = dao.find(selectedId);

        view.getTextID().setText("" + spp.getIdSpp());
        view.getTextTahun().setText("" + spp.getTahun());
        view.getTextNominal().setText("" + spp.getNominal());

    }

}
