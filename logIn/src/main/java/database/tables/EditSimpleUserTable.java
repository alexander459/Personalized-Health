/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.SimpleUser;
import com.google.gson.Gson;

import database.DB_Connection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Mike
 */
public class EditSimpleUserTable {


    /**
     * updates all the values of the record
     * @param username the name of the user
     * @param entry the entry of the table to update
     * @param newVal the new value
     */
    public void updateSimpleUser(String username, String entry, String newVal) throws SQLException, ClassNotFoundException {
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String update = "UPDATE users SET " + entry + "='" + newVal + "' WHERE username = '" + username + "'";
        stmt.executeUpdate(update);
        stmt.close();
        con.close();
    }

    /**
     * deletes the user with username
     * @param username
     * @return the response status code: 200 if success, 500 in failure
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public int deleteUser(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String query = "DELETE FROM users WHERE username='" + username + "'";
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

    /*returns an array list of type simple user with all the users*/
    public ArrayList<SimpleUser> getAllUsers() throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String json;
        ResultSet rs;
        ArrayList<SimpleUser> users= new ArrayList<>();

        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username!='admin'");      //get all users
            while (rs.next()){
                json=DB_Connection.getResultsToJSON(rs);
                Gson gson = new Gson();
                users.add(gson.fromJson(json, SimpleUser.class));
            }
            stmt.close();
            con.close();
            return users;
        } catch (Exception e) {
            System.err.println("Got an exception in database to su! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public void addSimpleUserFromJSON(String json) throws ClassNotFoundException{
         SimpleUser user=jsonToSimpleUser(json);
         addNewSimpleUser(user);
    }
    
     public SimpleUser jsonToSimpleUser(String json){
         Gson gson = new Gson();

        SimpleUser user = gson.fromJson(json, SimpleUser.class);
        return user;
    }
    
    public String simpleUserToJSON(SimpleUser user){
         Gson gson = new Gson();

        String json = gson.toJson(user, SimpleUser.class);
        return json;
    }

    
    public SimpleUser databaseToSimpleUser(String username, String password) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class);

            stmt.close();
            con.close();
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception in database to su! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }

    public SimpleUser databaseToSimpleUser(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
            Gson gson = new Gson();
            SimpleUser user = gson.fromJson(json, SimpleUser.class);

            stmt.close();
            con.close();
            return user;
        } catch (Exception e) {
            System.err.println("Got an exception in database to su! ");
            System.err.println(e.getMessage());
        }
        stmt.close();
        con.close();
        return null;
    }
    
    public String databaseUserToJSON(String username, String password) throws SQLException, ClassNotFoundException{
         Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        try {
            rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "' AND password='"+password+"'");
            rs.next();
            String json=DB_Connection.getResultsToJSON(rs);
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
    public void addNewSimpleUser(SimpleUser user) throws ClassNotFoundException {
        try {
            Connection con = DB_Connection.getConnection();

            Statement stmt = con.createStatement();

            String insertQuery = "INSERT INTO "
                    + " users (username,email,password,firstname,lastname,birthdate,gender,amka,country,city,address,"
                    + "lat,lon,telephone,height,weight,blooddonor,bloodtype)"
                    + " VALUES ("
                    + "'" + user.getUsername() + "',"
                    + "'" + user.getEmail() + "',"
                    + "'" + user.getPassword() + "',"
                    + "'" + user.getFirstname() + "',"
                    + "'" + user.getLastname() + "',"
                    + "'" + user.getBirthdate() + "',"
                    + "'" + user.getGender() + "',"
                    + "'" + user.getAmka() + "',"
                    + "'" + user.getCountry() + "',"
                    + "'" + user.getCity() + "',"
                    + "'" + user.getAddress() + "',"
                    + "'" + user.getLat() + "',"
                    + "'" + user.getLon() + "',"
                    + "'" + user.getTelephone() + "',"
                    + "'" + user.getHeight() + "',"
                    + "'" + user.getWeight() + "',"
                    + "'" + user.isBloodDonor() + "',"
                    + "'" + user.getBloodtype() + "'"
                    + ")";

            stmt.executeUpdate(insertQuery);
            /* Get the member id from the database and set it to the member */
            ResultSet rs = stmt.executeQuery("SELECT user_id FROM users WHERE username = '" + user.getUsername() + "'");
            rs.next();
            user.setUser_id(Integer.parseInt(rs.getString("user_id")));
            stmt.close();
            con.close();
            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(EditSimpleUserTable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
