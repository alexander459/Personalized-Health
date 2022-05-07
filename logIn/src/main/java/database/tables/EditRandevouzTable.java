/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import com.google.gson.Gson;
import mainClasses.Randevouz;
import database.DB_Connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditRandevouzTable {

    /**
     * returns an arraylist with all the randevouz of the user with id
     * @param userId the users id
     * @return arraylist of randevouz or null if not randevouz
     */
    public ArrayList<Randevouz> getRandevouzByUserId(int userId) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> array = new ArrayList<>();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM randevouz WHERE user_id= '" + userId + "'");

        while(rs.next()){
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz rd = gson.fromJson(json, Randevouz.class);
            array.add(rd);
        }
        if(array.size()==0)
            return null;
        return array;
    }


    /**
     * returns an arraylist with all the randevouz of the doctor with id
     * @param docId the docs id
     * @return arraylist of randevouz or null if not randevouz
     */
    public ArrayList<Randevouz> getRandevouzByDocId(int docId) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        ArrayList<Randevouz> array = new ArrayList<>();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM randevouz WHERE doctor_id= '" + docId + "'");

        while(rs.next()){
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz rd = gson.fromJson(json, Randevouz.class);
            array.add(rd);
        }
        if(array.size()==0)
            return null;
        return array;
    }

    /**
     *
     * @param array an array of randevouz
     * @return tan array with the "status" randevouz only or null if there are not "status"
     */
    public ArrayList<Randevouz> getRandevouzByStatus(ArrayList<Randevouz> array, String status){
        if(array==null)
            return null;
        ArrayList<Randevouz> newArray = new ArrayList<>();
        for(int i=0; i<array.size(); i++){
            if(array.get(i).getStatus().equalsIgnoreCase(status))
                newArray.add(array.get(i));
        }
        if(newArray.size()==0)
            return null;
        return newArray;
    }


    public void addRandevouzFromJSON(String json) throws ClassNotFoundException{
         Randevouz r=jsonToRandevouz(json);
         createNewRandevouz(r);
    }
    
    
     public Randevouz databaseToRandevouz(int id) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM randevouz WHERE randevouz_id= '" + id + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            Randevouz bt = gson.fromJson(json, Randevouz.class);
            return bt;
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
        return null;
    }
    

      
     public Randevouz jsonToRandevouz(String json) {
        Gson gson = new Gson();
        Randevouz r = gson.fromJson(json, Randevouz.class);
        return r;
    }
     
         
      public String randevouzToJSON(Randevouz r) {
        Gson gson = new Gson();

        String json = gson.toJson(r, Randevouz.class);
        return json;
    }


    public void updateRandevouz(int randevouzID, int userID, String info, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET user_id='" + userID + "',status='" + status +"',user_info='" + info + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void updateRandevouz(int randevouzID, String status) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String updateQuery = "UPDATE randevouz SET status='" + status + "' WHERE randevouz_id = '" + randevouzID + "'";
        stmt.executeUpdate(updateQuery);
        stmt.close();
        con.close();
    }

    public void deleteRandevouz(int randevouzID) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String deleteQuery = "DELETE FROM randevouz WHERE randevouz_id='" + randevouzID + "'";
        stmt.executeUpdate(deleteQuery);
        stmt.close();
        con.close();
    }



    /**
     * Establish a database connection and add in the database.
     *
     * @throws ClassNotFoundException
     */
    public void createNewRandevouz(Randevouz rand) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " randevouz (doctor_id,user_id,date_time,price,doctor_info,user_info,status)"
                    + " VALUES ("
                    + "'" + rand.getDoctor_id() + "',"
                    + "'" + rand.getUser_id() + "',"
                    + "'" + rand.getDate_time() + "',"
                    + "'" + rand.getPrice() + "',"
                    + "'" + rand.getDoctor_info() + "',"
                    + "'" + rand.getUser_info() + "',"
                    + "'" + rand.getStatus() + "'"
                    + ")";
            //stmt.execute(table);

            stmt.executeUpdate(insertQuery);
            System.out.println("# The randevouz was successfully added in the database.");

            /* Get the member id from the database and set it to the member */
            stmt.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditRandevouzTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
