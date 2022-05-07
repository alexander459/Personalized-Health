package servlet;

import database.tables.EditSimpleUserTable;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * this servlet returns all the users from the database to the admin
 */

@WebServlet(name = "GetUsers", value = "/GetUsers")
public class GetUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EditSimpleUserTable eut = new EditSimpleUserTable();
        ArrayList<SimpleUser> users;                      //storeusers as array of user objects
        ArrayList<String> JSONusers = new ArrayList<>();  //store users as json
        try {
            users=eut.getAllUsers();

            //user objcs to json
            for (int i=0; i<users.size(); i++)
                JSONusers.add(eut.simpleUserToJSON(users.get(i)));

            out.print(JSONusers);     //send them back to the javaScript
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("ex getU " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("ex2 getU " + e);
            response.setStatus(500);
        }
    }
}
