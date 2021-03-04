/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.admin.CRUDSiswa;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sppmanagement.dao.KelasDAO;
import sppmanagement.dao.SPPDao;
import sppmanagement.dao.SiswaDAO;
import sppmanagement.model.Kelas;
import sppmanagement.model.SPP;
import sppmanagement.model.Siswa;

/**
 *
 * @author ASTANEW2
 */
public class CRUDSiswaController {

    SiswaDAO dao;
    KelasDAO kelasDao;
    SPPDao sppDao;

    ArrayList<Kelas> listKelas;
    ArrayList<SPP> listSPP;

    String[] listTextSPP;
    String[] listNamaKelas;

    boolean isEdit = false;

    public CRUDSiswaController() {
        dao = new SiswaDAO();
        kelasDao = new KelasDAO();
        sppDao = new SPPDao();

        listKelas = kelasDao.all();
        listSPP = sppDao.all();

        listTextSPP = listSPP.stream().map((SPP spp) -> {
            return spp.getTahun();
        }).toArray(String[]::new);

        listNamaKelas = listKelas.stream().map((Kelas kelas) -> {
            return kelas.getNamaKelas();
        }).toArray(String[]::new);
    }

    public void loadTable(CRUDSiswaView view) {
        ArrayList<Siswa> listSiswa = dao.all();

        DefaultTableModel tableModel = (DefaultTableModel) view.getTableSiswa().getModel();

        tableModel.setNumRows(0);

        listSiswa.forEach((siswa) -> {
            Object[] row = new Object[7];

            row[0] = siswa.getNisn();
            row[1] = siswa.getNis();
            row[2] = siswa.getNama();
            row[3] = siswa.getKelas() != null ? siswa.getKelas().getNamaKelas() : "-";
            row[4] = siswa.getAlamat();
            row[5] = siswa.getNoTelp();

            SPP spp = siswa.getSPP();

            row[6] = spp != null ? spp.getTahun() : "-";

            tableModel.addRow(row);
        });
    }

    void setupSelectKelas(CRUDSiswaView view) {

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(listNamaKelas);

        view.getSelectKelas().setModel(model);
    }

    void setupSelectSPP(CRUDSiswaView view) {

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(listTextSPP);

        view.getSelectSPP().setModel(model);
    }

    void save(CRUDSiswaView view) {
        Siswa siswa;

        String nisn = view.getTextNISN().getText();
        Siswa existingSiswa = dao.find(nisn);

        if (existingSiswa == null) {
            System.out.println("Create new siswa");
            siswa = new Siswa();
        } else {
            if (isEdit == false) {
                JOptionPane.showMessageDialog(null, "Siswa dengan NISN tersebut sudah terdaftar!");

                return;
            }

            System.out.println("Update existing siswa");
            siswa = dao.find(nisn);
        }

        siswa.setNisn(view.getTextNISN().getText());
        siswa.setNis(view.getTextNIS().getText());
        siswa.setNama(view.getTextNama().getText());
        siswa.setAlamat(view.getTextAlamat().getText());
        siswa.setNoTelp(view.getTextNoTelp().getText());

        siswa.setIdKelas(getSelectedKelas(view).getIdKelas());
        siswa.setIdSpp(getSelectedSPP(view).getIdSpp());

        siswa.save();

        // adding new siswa
        if (existingSiswa == null) {
            this.reset(view);
        }

        this.loadTable(view);
    }

    private Kelas getSelectedKelas(CRUDSiswaView view) {
        // get id kelas from kelas jComboBox selectKelas
        int selectedIndex = view.getSelectKelas().getSelectedIndex();

        Kelas selectedKelas = listKelas.get(selectedIndex);

        return selectedKelas;
    }

    private SPP getSelectedSPP(CRUDSiswaView view) {
        // get id kelas from kelas jComboBox selectKelas
        int selectedIndex = view.getSelectSPP().getSelectedIndex();

        SPP selectedSPP = listSPP.get(selectedIndex);

        return selectedSPP;
    }

    void reset(CRUDSiswaView view) {
        view.getTextNISN().setText("");
        view.getTextNIS().setText("");
        view.getTextNama().setText("");
        view.getSelectKelas().setSelectedIndex(0);
        view.getTextAlamat().setText("");
        view.getTextNoTelp().setText("");
        view.getSelectSPP().setSelectedIndex(0);

        view.getTextNISN().setEnabled(true);

        isEdit = false;

    }

    void delete(CRUDSiswaView view) {
        if (view.getTextNISN().getText().equals("")) {
            JOptionPane.showMessageDialog(view, "Pilih siswa yang akan dihapus");

            return;
        }

        int response = JOptionPane.showConfirmDialog(null, "Menghapus siswa akan mempengaruhi data pembayaran! apakah anda yakin?", "Konfirmasi Hapus", JOptionPane.WARNING_MESSAGE);

        if (response != JOptionPane.OK_OPTION) {
            return;
        }

        dao.find(view.getTextNISN().getText()).delete();

        this.reset(view);
        this.loadTable(view);
    }

    void onTableClick(CRUDSiswaView view) {
        JTable tabelSiswa = view.getTableSiswa();

        int selectedRow = tabelSiswa.getSelectedRow();

        Siswa siswa = dao.find(tabelSiswa.getValueAt(selectedRow, 0).toString());

        view.getTextNISN().setText(siswa.getNisn());
        view.getTextNIS().setText(siswa.getNis());
        view.getTextNama().setText(siswa.getNama());
        view.getTextAlamat().setText(siswa.getAlamat());
        view.getTextNoTelp().setText(siswa.getNoTelp());

        view.getSelectKelas().setSelectedItem(siswa.getKelas() != null ? siswa.getKelas().getNamaKelas() : "");

        SPP sppSiswa = siswa.getSPP();

        view.getSelectSPP().setSelectedItem(sppSiswa != null ? sppSiswa.getTahun() : "");

        view.getTextNISN().setEnabled(false);
        isEdit = true;
    }

}
