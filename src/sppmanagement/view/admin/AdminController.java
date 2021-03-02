/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import sppmanagement.dao.PembayaranDAO;
import sppmanagement.dao.SiswaDAO;
import sppmanagement.db.Auth;
import sppmanagement.db.DB;
import sppmanagement.helper.Helper;
import sppmanagement.model.Pembayaran;
import sppmanagement.model.Petugas;
import sppmanagement.model.SPP;
import sppmanagement.model.Siswa;

/**
 *
 * @author ASTANEW2
 */
public class AdminController {

    Petugas user = Auth.getUser();

    SiswaDAO siswaDao = new SiswaDAO();
    PembayaranDAO pembayaranDao = new PembayaranDAO();

    Siswa selectedSiswa = null;

    void setupVisibility(AdminView view) {
        if (user.getLevel().equals("petugas")) {
            view.panelKelolaData.setVisible(false);
            view.btnGenerateLaporan.setVisible(false);
        }

        view.labelJudul.setText(user.getNama() + "@" + user.getLevel());
    }

    void searchUser(AdminView view) {
        String nisn = view.textSearchSiswaNISN.getText();

        selectedSiswa = siswaDao.find(nisn);

        if (selectedSiswa == null) {
            JOptionPane.showMessageDialog(null, "Siswa tidak dapat ditemukan");

            return;
        }

        view.labelUserNISN.setText(selectedSiswa.getNisn());
        view.labelUserNIS.setText(selectedSiswa.getNis());
        view.labelUserNama.setText(selectedSiswa.getNama());
        view.labelUserKelas.setText(selectedSiswa.getKelas().getNamaKelas());
        view.labelUserAlamat.setText(selectedSiswa.getAlamat());
        view.labelUserTelepone.setText(selectedSiswa.getNoTelp());
        view.labelUserSPP.setText(selectedSiswa.getSPP().getNominal() + "");

        this.populateTabelSiswa(view);

        view.labelNameNISNEntri.setText(selectedSiswa.getNama() + " - " + selectedSiswa.getNisn());
        view.dateTanggalBayar.setDate(new Date());
        view.textJumlahBayar.setText(selectedSiswa.getSPP().getNominal() + "");

        view.mainTabPane.setSelectedIndex(2);
    }

    private void populateTabelSiswa(AdminView view) {
        DefaultTableModel tableModel = (DefaultTableModel) view.tableHistoryPembayaranSiswa.getModel();

        tableModel.setNumRows(0);

        if (selectedSiswa == null) {
            return;
        }

        ArrayList<Pembayaran> listPembayaranSiswa = selectedSiswa.getPembayaran();

        listPembayaranSiswa.forEach((pembayaran) -> {
            Object[] row = new Object[6];

            row[0] = pembayaran.getIdPembayaran();
            row[1] = pembayaran.getPetugas().getNama();
            row[2] = pembayaran.getTglBayar();
            row[3] = pembayaran.getBulanBayar();
            row[4] = pembayaran.getTahunBayar();
            row[5] = pembayaran.getJumlahBayar();

            tableModel.addRow(row);
        });
    }

    void loadTabelPembayaran(AdminView view) {
        ArrayList<Pembayaran> listPembayaranSiswa = pembayaranDao.all();

        DefaultTableModel tableModel = (DefaultTableModel) view.tabelPembayaran.getModel();

        tableModel.setNumRows(0);

        listPembayaranSiswa.forEach((pembayaran) -> {
            Object[] row = new Object[8];

            row[0] = pembayaran.getIdPembayaran();
            row[1] = pembayaran.getPetugas().getNama();
            row[2] = pembayaran.getSiswa().getNama();
            row[3] = pembayaran.getTglBayar();
            row[4] = pembayaran.getBulanBayar();
            row[5] = pembayaran.getTahunBayar();
            row[6] = pembayaran.getSPP().toString();
            row[7] = pembayaran.getJumlahBayar();

            tableModel.addRow(row);
        });
    }

    void resetEntri(AdminView view) {
        view.dateTanggalBayar.setDate(null);
//        view.yearTahunDibayar.setYear(Calendar.getInstance().get(Calendar.YEAR));
//        view.monthBulanDIbayar.setMonth(Calendar.getInstance().get(Calendar.MONTH));
        view.textJumlahBayar.setText("");
    }

    void addEntry(AdminView view) {
        if (selectedSiswa == null) {
            JOptionPane.showMessageDialog(null, "Harap pilih siswa terlebih dahulu melalui panel di sebelah kiri");

            return;
        }

        Date tanggalBayar = view.dateTanggalBayar.getDate();
        int jumlahBayar = Integer.parseInt("0" + view.textJumlahBayar.getText());

        if (tanggalBayar == null || jumlahBayar == 0) {
            JOptionPane.showMessageDialog(null, "Harap lengkapi data pembayaran!");

            return;
        }

        java.sql.Date sqlDate = new java.sql.Date(tanggalBayar.getTime());

        Calendar cal = Calendar.getInstance();

        cal.setTime(tanggalBayar);

        String bulanBayar = Helper.getTextBulan(view.bulanBayar.getMonth());
        String tahunBayar = view.tahunBayar.getYear() + "";

        Pembayaran existingPembayaran = pembayaranDao.whereOne(String.format("nisn = '%s' AND bulan_dibayar = '%s' AND tahun_dibayar = '%s'", selectedSiswa.getNisn(), bulanBayar, tahunBayar), Pembayaran.class);

        if (existingPembayaran != null) {
            JOptionPane.showMessageDialog(null, String.format("Siswa telah membayar SPP untuk bulan %s tahun %s!", bulanBayar, tahunBayar));

            return;
        }

        SPP sppSiswa = selectedSiswa.getSPP();

        if (jumlahBayar != sppSiswa.getNominal()) {
            int response = JOptionPane.showConfirmDialog(null, "Jumlah bayar berbeda dari nominal SPP yang telah ditentukan (" + sppSiswa.getNominal() + "), yakin ingin melanjutkan entri?");

            if (response != JOptionPane.YES_OPTION) {
                return;
            }
        }

        // everyting is clear
        Pembayaran pembayaran = new Pembayaran();

        pembayaran.setIdPetugas(user.getId());
        pembayaran.setNisn(selectedSiswa.getNisn());
        pembayaran.setTglBayar(sqlDate);
        pembayaran.setBulanBayar(bulanBayar);
        pembayaran.setTahunBayar(tahunBayar);
        pembayaran.setIdSpp(selectedSiswa.getIdSpp());
        pembayaran.setJumlahBayar(jumlahBayar);

        pembayaran.save();

//        this.resetEntri(view);
        this.loadTabelPembayaran(view);
        this.populateTabelSiswa(view);
    }

    void loadTabelSiswa(AdminView view) {
        ArrayList<Siswa> listSiswa = siswaDao.all();

        DefaultTableModel tableModel = (DefaultTableModel) view.tableSiswa.getModel();

        tableModel.setNumRows(0);

        listSiswa.forEach((siswa) -> {
            Object[] row = new Object[7];

            row[0] = siswa.getNisn();
            row[1] = siswa.getNis();
            row[2] = siswa.getNama();
            row[3] = siswa.getKelas().getNamaKelas();
            row[4] = siswa.getAlamat();
            row[5] = siswa.getNoTelp();

            SPP spp = siswa.getSPP();

            row[6] = spp.getTahun() + " - " + spp.getNominal();

            tableModel.addRow(row);
        });
    }

    void onTableSiswaClick(AdminView view) {
        int selectedRow = view.tableSiswa.getSelectedRow();

        selectedSiswa = siswaDao.find(view.tableSiswa.getValueAt(selectedRow, 0).toString());

        if (selectedSiswa == null) {
            JOptionPane.showMessageDialog(null, "Siswa tidak dapat ditemukan");

            return;
        }

        view.textSearchSiswaNISN.setText(selectedSiswa.getNisn());

        view.labelUserNISN.setText(selectedSiswa.getNisn());
        view.labelUserNIS.setText(selectedSiswa.getNis());
        view.labelUserNama.setText(selectedSiswa.getNama());
        view.labelUserKelas.setText(selectedSiswa.getKelas().getNamaKelas());
        view.labelUserAlamat.setText(selectedSiswa.getAlamat());
        view.labelUserTelepone.setText(selectedSiswa.getNoTelp());
        view.labelUserSPP.setText(selectedSiswa.getSPP().getNominal() + "");

        this.populateTabelSiswa(view);

        view.labelNameNISNEntri.setText(selectedSiswa.getNama() + " - " + selectedSiswa.getNisn());
        view.dateTanggalBayar.setDate(new Date());
        view.textJumlahBayar.setText(selectedSiswa.getSPP().getNominal() + "");

        view.mainTabPane.setSelectedIndex(2);
    }

    void refresh(AdminView view) {
        this.loadTabelPembayaran(view);
        this.loadTabelSiswa(view);
    }

    void generateLaporan(AdminView aThis) {
        try {
            JasperPrint jp = JasperFillManager.fillReport(getClass().getResourceAsStream("laporanPembayaran.jasper"), null, DB.connection());
            JasperViewer.viewReport(jp, false);
        } catch (JRException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
