/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.BloodTest;
import com.google.gson.Gson;
import database.DB_Connection;
import mainClasses.Doctor;
import mainClasses.SimpleUser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditBloodTestTable {

   
    
    public void addBloodTestFromJSON(String json) throws ClassNotFoundException, SQLException{
         BloodTest bt=jsonToBloodTest(json);
         bt.setValues();
         createNewBloodTest(bt);
    }
    
     public BloodTest jsonToBloodTest(String json){
        Gson gson = new Gson();
        BloodTest btest = gson.fromJson(json, BloodTest.class);
        return btest;
    }
    
     public String bloodTestToJSON(BloodTest bt){
         Gson gson = new Gson();

        String json = gson.toJson(bt, BloodTest.class);
        return json;
    }

    /**
     * returns an arraylist with all the bloodtests of this user id
     * @param id
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<BloodTest> getAllTestsByUserId(int  id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String json;
        ArrayList<BloodTest> btests = new ArrayList<>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE user_id= '" + id + "'");
        while (rs.next()){
            json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            btests.add(gson.fromJson(json, BloodTest.class));
        }
        stmt.close();
        con.close();
        return btests;
    }



    /**
     * returns an arraylist with all the bloodtests of this amka
     * @param amka
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ArrayList<BloodTest> getAllTestsByAmka(String amka) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String json;
        ArrayList<BloodTest> btests = new ArrayList<>();
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "'");
        while (rs.next()){
            json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            btests.add(gson.fromJson(json, BloodTest.class));
        }
        stmt.close();
        con.close();
        return btests;
    }

    public BloodTest databaseToBloodTest(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE bloodtest_id= '" + id + "'");
        rs.next();
        String json=DB_Connection.getResultsToJSON(rs);
        Gson gson = new Gson();
        BloodTest bt = gson.fromJson(json, BloodTest.class);
        return bt;

    }

     public BloodTest databaseToBloodTest(String amka,String date) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "' AND test_date='"+date+"'");
        rs.next();
        String json=DB_Connection.getResultsToJSON(rs);
        Gson gson = new Gson();
        BloodTest bt = gson.fromJson(json, BloodTest.class);
        return bt;

    }
  
       
    public void deleteBloodTest(int bloodtestid) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM bloodtest WHERE bloodtest_id='" + bloodtestid + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }


    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewBloodTest(BloodTest bt) throws ClassNotFoundException, SQLException {
        try {
            Connection con = DB_Connection.getConnection();
            System.out.println("in adding test is\n" + bt);
            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " bloodtest (amka,test_date,medical_center,blood_sugar,blood_sugar_level,cholesterol,"
                    + "cholesterol_level,iron,iron_level,vitamin_d3,vitamin_d3_level,vitamin_b12,vitamin_b12_level) "
                    + " VALUES ("
                    + "'" + bt.getAmka()+ "',"
                    + "'" + bt.getTest_date() + "',"
                    + "'" + bt.getMedical_center() + "',"
                    + "'" + bt.getBlood_sugar() + "',"
                    + "'" + bt.getBlood_sugar_level() + "',"
                    + "'" + bt.getCholesterol() + "',"
                    + "'" + bt.getCholesterol_level() + "',"
                    + "'" + bt.getIron() + "',"
                    + "'" + bt.getIron_level() + "',"
                    + "'" + bt.getVitamin_d3() + "',"
                    + "'" + bt.getVitamin_d3_level() + "',"
                    + "'" + bt.getVitamin_b12()+ "',"
                    + "'" + bt.getVitamin_b12_level()+ "'"
                    + ")";
            //stmt.execute(table);
            System.out.println(insertQuery);
            stmt.executeUpdate(insertQuery);

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditBloodTestTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
