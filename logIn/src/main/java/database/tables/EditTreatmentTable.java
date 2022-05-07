/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import database.tables.EditBloodTestTable;
import database.DB_Connection;
import database.tables.EditMessageTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.Message;
import mainClasses.Treatment;

/**
 *
 * @author mountant
 */
public class EditTreatmentTable {

    /*returns all the treatments for the user*/
    public ArrayList<Treatment> getUserTreatment(int userId) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Treatment> array = new ArrayList<>();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM treatment WHERE user_id='" + userId + "'");
        try {
            while(rs.next()) {
                String json=DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                array.add(gson.fromJson(json, Treatment.class));
            }
            return array;
        } catch (Exception e) {
            System.err.println("exception in getting active treatments " + e);
        }
        return null;
    }
    
    public void addTreatmentFromJSON(String json) throws ClassNotFoundException{
         Treatment msg=jsonToTreatment(json);
         createNewTreatment(msg);
    }
    public String treatmentToJSON(Treatment tr) {
        Gson gson = new Gson();

        String json = gson.toJson(tr, Treatment.class);
        return json;
    }

    public Treatment jsonToTreatment(String json) {
        Gson gson = new Gson();
        Treatment tr = gson.fromJson(json, Treatment.class);
        return tr;
    }
    
    public Treatment databaseToTreatment(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM treatment WHERE treatment_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Treatment tr  = gson.fromJson(json, Treatment.class);
            return tr;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }

    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewTreatment(Treatment tr) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " treatment (doctor_id,user_id,start_date,end_date,treatment_text,bloodtest_id) "
                    + " VALUES ("
                    + "'" + tr.getDoctor_id() + "',"
                    + "'" + tr.getUser_id() + "',"
                    + "'" + tr.getStart_date() + "',"
                    + "'" + tr.getEnd_date()+ "',"
                    + "'" + tr.getTreatment_text() + "',"
                    + "'" + tr.getBloodtest_id()+ "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);
            System.out.println("# The bloodtest was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();
                 con.close();
        } catch (SQLException ex) {
            Logger.getLogger(EditBloodTestTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
