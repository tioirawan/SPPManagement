/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.db;

import javax.swing.JOptionPane;
import sppmanagement.model.Petugas;

/**
 *
 * @author ASTANEW2
 */
public class Auth {

    static Petugas user;

    public static Petugas getUser() {
        // for debugging
//        Petugas admin = new Petugas();
//
//        admin.setId(999);
//        admin.setLevel("admin");
//        admin.setNama("Admin Testing");
//        admin.setUsername("admin");
//        admin.setPassword("admin123");
//
//        return admin;
        // end debugging

        return user;
    }

    public static void setUser(Petugas user) {
        Auth.user = user;
    }

    public static Petugas check() {
        // for debugging
//        Petugas admin = new Petugas();
//
//        admin.setId(999);
//        admin.setLevel("admin");
//        admin.setNama("Admin Testing");
//        admin.setUsername("admin");
//        admin.setPassword("admin123");
//
//        return admin;
        // end debugging

        if (user == null) {
            JOptionPane.showMessageDialog(null, "Session ended, please reopen the program and login!");

            System.exit(0);
        }

        System.out.println("Login check for user :" + user.getUsername() + " as " + user.getLevel());

        return user;
    }
}
