/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement;

import java.sql.Connection;
import java.util.Locale;
import sppmanagement.db.DB;
import sppmanagement.view.MainView;
import sppmanagement.view.admin.AdminView;

/**
 *
 * @author ASTANEW2
 */
public class SPPManagement {

    Connection con;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("id", "ID"));
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainView().setVisible(true);
            }
        });
    }

}
