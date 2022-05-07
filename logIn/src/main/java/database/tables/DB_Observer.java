package database.tables;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * checks if uname email or amka exist in the database
 */
public class DB_Observer {
    public boolean emailExists(String email) throws SQLException, ClassNotFoundException{
        if(email == null)
            return false;
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM users");
        while(rs.next()){
            if(email.equals(rs.getString("email")))
                return true;
        }
        rs = stmt.executeQuery("SELECT * FROM doctors");
        while(rs.next()){
            if(email.equals(rs.getString("email")))
                return true;
        }
        return false;
    }

    public boolean usernameExists(String username) throws SQLException, ClassNotFoundException{
        if(username == null)
            return false;
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM users");
        while(rs.next()){
            if(username.equals(rs.getString("username")))
                return true;
        }
        rs = stmt.executeQuery("SELECT * FROM doctors");
        while(rs.next()){
            if(username.equals(rs.getString("username")))
                return true;
        }
        return false;
    }

    public boolean amkaExists(String amka) throws SQLException, ClassNotFoundException{
        if(amka == null)
            return false;
        Connection con = DB_Connection.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs;
        rs = stmt.executeQuery("SELECT * FROM users");
        while(rs.next()){
            if(amka.equals(rs.getString("amka")))
                return true;
        }
        rs = stmt.executeQuery("SELECT * FROM doctors");
        while(rs.next()){
            if(amka.equals(rs.getString("amka")))
                return true;
        }
        return false;
    }
}
