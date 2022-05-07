package database;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Check {

    /**
     * checks if the user exists
     * @param username
     * @return true if exists, false if not
     */
    public boolean userExists(String username) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM users");
        while(rs.next())
            if(username.equals(rs.getString("username")))
                return true;    //username found in simple user


        rs = stmt.executeQuery("SELECT * FROM doctors");
        while(rs.next())
            if(username.equals(rs.getString("username")))
                return true;   //username found in doctors

        return false;
    }

    /**
     * takes username and password and checks if the password is correct
     * @param username
     * @param password
     * @return true or false
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean isPasswordValid(String username, String password) throws SQLException, ClassNotFoundException{
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();
        String db_password;
        ResultSet rs;

        rs = stmt.executeQuery("SELECT * FROM users WHERE username = '" + username + "'");
        if(rs.next()) {
            db_password = rs.getString("password");
            System.out.println("in users " + db_password);
            if (db_password.equals(password)) {
                System.out.println("connected!");
                stmt.close();
                rs.close();
                return true;
            }
        }

        //maybe its a doctor
        rs = stmt.executeQuery("SELECT * FROM doctors WHERE username = '" + username + "'");
        if(rs.next()) {
            db_password = rs.getString("password");
            System.out.println("in docs " + db_password);
            if (db_password.equals(password)) {
                System.out.println("connected!");
                stmt.close();
                rs.close();
                return true;
            }
        }

        //invalid password
        System.out.println("invald pas");
        return false;   //wrong password
    }
}
