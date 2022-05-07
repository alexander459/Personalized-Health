package servlet;

import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.SimpleUser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "GetDoctors", value = "/GetDoctors")
public class GetDoctors extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        EditDoctorTable edt = new EditDoctorTable();
        ArrayList<Doctor> docs;                      //store docs as array of docs objects
        ArrayList<String> JSONdocs = new ArrayList<>();  //store docs as json
        try {
            docs=edt.getAllDocs();

            //doc objcs to json
            for (int i=0; i<docs.size(); i++)
                JSONdocs.add(edt.doctorToJSON(docs.get(i)));

            out.print(JSONdocs);     //send them back to the javaScript
            response.setStatus(200);
        }catch (SQLException e){
            System.out.println("ex getDoc " + e);
            response.setStatus(500);
        }catch (ClassNotFoundException e){
            System.out.println("ex2 getDoc " + e);
            response.setStatus(500);
        }
    }

}
