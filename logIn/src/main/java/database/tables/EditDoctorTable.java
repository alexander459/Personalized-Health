/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.Doctor;
import com.google.gson.Gson;
import mainClasses.User;
import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mainClasses.SimpleUser;

import javax.print.Doc;

/**
 *
 * @author Mike
 */
public class EditDoctorTable {
    /**
     * deletes the doc with username
     * @param username
     * @return the response status code: 200 if success, 500 in failure
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int deleteDoc(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String query = "DELETE FROM doctors WHERE username='" + username + "'";
        try {
            stmt.executeUpdate(query);
            stmt.close();
            con.close();
            return 200;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return 500;
    }

    /*returns an array list of type doctor with all the doctors*/
    public ArrayList<Doctor> getAllDocs() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String json;
        ResultSet rs;
        ArrayList<Doctor> docs = new ArrayList<>();

        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");      //get all docs
            while (rs.next()){
                json=DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                docs.add(gson.fromJson(json, Doctor.class));
            }
            stmt.close();
            con.close();
            return docs;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public void addDoctorFromJSON(String json) throws ClassNotFoundException {
        Doctor doc = jsonToDoctor(json);
        addNewDoctor(doc);
    }

    public Doctor jsonToDoctor(String json) {
        Gson gson = new Gson();

        Doctor doc = gson.fromJson(json, Doctor.class);
        return doc;
    }

    public String doctorToJSON(Doctor doc) {
        Gson gson = new Gson();

        String json = gson.toJson(doc, Doctor.class);
        return json;
    }

    public void updateDoctor(String username, String entry, String newVal) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE doctors SET " + entry + "='" + newVal + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        stmt.close();
        con.close();
    }

    public Doctor databaseToDoctor(String username) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            stmt.close();
            con.close();
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public Doctor databaseToDoctor(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Doctor doc = gson.fromJson(json, Doctor.class);
            stmt.close();
            con.close();
            return doc;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public ArrayList<Doctor> databaseToDoctors() throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Doctor> doctors=new ArrayList<Doctor>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors");
            while (rs.next()) {
                String json = DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                Doctor doc = gson.fromJson(json, Doctor.class);
                doctors.add(doc);
            }
            stmt.close();
            con.close();
            return doctors;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public String databaseToJSON(String username, String password) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "' AND password='" + password + "'");
            rs.next();
            String json = DB_Connection.getResultsToJSON(rs);
            return json;
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
    public void addNewDoctor(Doctor doc) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " doctors (username,email,password,firstname,lastname,birthdate,gender,amka,country,city,address,"
                    + "lat,lon,telephone,height,weight,blooddonor,bloodtype,specialty,doctor_info,certified)"
                    + " VALUES ("
                    + "'" + doc.getUsername() + "',"
                    + "'" + doc.getEmail() + "',"
                    + "'" + doc.getPassword() + "',"
                    + "'" + doc.getFirstname() + "',"
                    + "'" + doc.getLastname() + "',"
                    + "'" + doc.getBirthdate() + "',"
                    + "'" + doc.getGender() + "',"
                    + "'" + doc.getAmka() + "',"
                    + "'" + doc.getCountry() + "',"
                    + "'" + doc.getCity() + "',"
                    + "'" + doc.getAddress() + "',"
                    + "'" + doc.getLat() + "',"
                    + "'" + doc.getLon() + "',"
                    + "'" + doc.getTelephone() + "',"
                    + "'" + doc.getHeight() + "',"
                    + "'" + doc.getWeight() + "',"
                    + "'" + doc.isBloodDonor() + "',"
                    + "'" + doc.getBloodtype() + "',"
                    + "'" + doc.getSpecialty() + "',"
                    + "'" + doc.getDoctor_info() + "',"
                    + "'" + doc.getCertified() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            /* Get the member id from the database and set it to the member */
            ResultSet rs = stmt.executeQuery("SELECT doctor_id FROM doctors WHERE username = '" + doc.getUsername() + "'");
            rs.next();
            doc.setDoctor_id(Integer.parseInt(rs.getString("doctor_id")));
            stmt.close();
            con.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditDoctorTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
