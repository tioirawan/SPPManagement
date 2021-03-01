/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.helper;

/**
 *
 * @author ASTANEW2
 */
public class Helper {
    public static String getTextBulan(int bulanIndex) {
        String[] textBulan = new String[]{
            "Januari",
            "Februari",
            "Maret",
            "April",
            "Mei",
            "Juni",
            "Juli",
            "Agustus",
            "September",
            "Oktober",
            "November",
            "Desember"
        };

        return textBulan[bulanIndex];
    }
}
