/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.view.Login;

import javax.swing.JOptionPane;
import sppmanagement.dao.PetugasDAO;
import sppmanagement.db.Auth;
import sppmanagement.model.Petugas;

/**
 *
 * @author ASTANEW2
 */
public class LoginController {

    PetugasDAO petugasDao = new PetugasDAO();

    void login(LoginView view) {
        String username = view.getTextUsername().getText();
        String password = view.getTextPassword().getText();

        Petugas user = petugasDao.getByUsernameAndPassword(username, password);

        if (user != null) {
            Auth.setUser(user);

            view.loginListener.onSuccess();
        } else {
            JOptionPane.showMessageDialog(null, "username atau password salah!");

            Auth.setUser(null);

            view.loginListener.onFailure();
        }
    }

}
