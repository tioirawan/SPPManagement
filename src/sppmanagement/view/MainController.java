/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sppmanagement.dao.SiswaDAO;
import sppmanagement.model.Pembayaran;
import sppmanagement.model.SPP;
import sppmanagement.model.Siswa;

/**
 *
 * @author ASTANEW2
 */
public class MainController {

    SiswaDAO siswaDao = new SiswaDAO();

    Siswa siswa;

    public void search(MainView view) {
        String nisn = view.textSearchNISN.getText();

        if (nisn.equals("")) {
            JOptionPane.showMessageDialog(null, "Harap masukan NISN");

            return;
        }

        siswa = siswaDao.find(nisn);

        if (siswa == null) {
            JOptionPane.showMessageDialog(null, "Siswa tidak dapat ditemukan, harap periksa NISN yang anda masukan...");

            return;
        }

        this.loadTabelSiswa(view);
        this.loadTabelHistory(view);
    }

    private void loadTabelSiswa(MainView view) {
        DefaultTableModel tableModel = (DefaultTableModel) view.tableSiswa.getModel();

        tableModel.setNumRows(0);

        Object[] row = new Object[7];

        row[0] = siswa.getNisn();
        row[1] = siswa.getNis();
        row[2] = siswa.getNama();
        row[3] = siswa.getKelas() != null ? siswa.getKelas().getNamaKelas() : "-";
        row[4] = siswa.getAlamat();
        row[5] = siswa.getNoTelp();

        SPP spp = siswa.getSPP();

        row[6] = spp.getTahun() + " - " + spp.getNominal();

        tableModel.addRow(row);
    }

    private void loadTabelHistory(MainView view) {
        DefaultTableModel tableModel = (DefaultTableModel) view.tableHistoryPembayaranSiswa.getModel();

        tableModel.setNumRows(0);

        if (siswa == null) {
            return;
        }

        ArrayList<Pembayaran> listPembayaranSiswa = siswa.getPembayaran();

        listPembayaranSiswa.forEach((pembayaran) -> {
            Object[] row = new Object[6];

            DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);

            row[0] = pembayaran.getTglBayar();
            row[1] = pembayaran.getBulanBayar();
            row[2] = pembayaran.getTahunBayar();
            row[3] = pembayaran.getPetugas() != null ? pembayaran.getPetugas().getNama() : "-";
            row[4] = pembayaran.getJumlahBayar();

            tableModel.addRow(row);
        });
    }

}
