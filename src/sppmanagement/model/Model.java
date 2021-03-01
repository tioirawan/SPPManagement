/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sppmanagement.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import sppmanagement.dao.PetugasDAO;

/**
 *
 * @author ASTANEW2
 */
public interface Model {

    public void save();

    public void delete();

    public void fillFromResultSet(ResultSet result) throws SQLException;
}
